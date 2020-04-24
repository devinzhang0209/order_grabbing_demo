package com.devin.jobsearch.Service;

import com.devin.jobsearch.mapper.JobMapper;
import com.devin.jobsearch.model.JobModel;
import com.devin.jobsearch.util.FileUtil;
import com.devin.jobsearch.util.SearchUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Devin Zhang
 * @className Job51SearchHandle
 * @description TODO
 * @date 2020/4/21 16:41
 */

@Service
public class Job51SearchHandle implements IJobHandle {

    @Resource
    private SearchUtil searchUtil;
    @Resource
    private JobMapper jobMapper;
    @Resource
    private FileUtil fileUtil;

    private static final String PAGEPATTERN = "pagePattern";
    private static final String JOB51URL = "https://search.51job.com/list/000000,000000,0000,00,9,99,%2B,2,pagePattern.html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=";


    /**
     * 获取51job总共有多少页
     *
     * @return
     * @throws Exception
     */
    @Override
    public int getJobPage() throws Exception {
        String url = JOB51URL;
        url = url.replace(PAGEPATTERN, "1");
        Document document = searchUtil.getDocument(url);
        return Integer.parseInt(document.getElementById("hidTotalPage").val());
    }


    /**
     * 分页爬取51job
     */
    @Override
    public void handle() throws Exception {
        //目标地址
        int pageTotal = this.getJobPage();
        List<JobModel> jobModelList = null;
        for (int page = 1; page <= pageTotal; page++) {
            try {
                jobModelList = new ArrayList();
                System.out.println("开始爬取第:" + page + "页的数据");
                String url = JOB51URL;
                url = url.replace(PAGEPATTERN, page + "");
                Document document = searchUtil.getDocument(url);
                //  右侧导航栏
                Elements nav_com = document.getElementsByClass("el");
                for (Element element : nav_com) {
                    if (element.children().first().tagName("p").hasClass("t1") &&
                            element.children().first().tagName("p").children().hasClass("check")) {

                        String jobName = element.children().first().tagName("p").children().tagName("span").text();
                        String jobUrl = element.children().first().tagName("p").child(2).child(0).attr("href");
                        String companyName = element.child(1).text();
                        String companyUrl = element.child(1).child(0).attr("href");
                        String jobLocation = element.child(2).text();
                        String jobSalary = element.child(3).text();
                        String jobDate = element.child(4).text();

                        JobModel jobModel = new JobModel();
                        jobModel.setJobName(jobName);
                        jobModel.setJobUrl(jobUrl);
                        jobModel.setJobCompanyName(companyName);
                        jobModel.setJobCompanyUrl(companyUrl);
                        jobModel.setJobLocation(jobLocation);
                        jobModel.setJobSalary(jobSalary);
                        jobModel.setJobDate(jobDate);

                        //爬取明细
                        Document detailDocument = searchUtil.getDocument(jobUrl);

                        String jobRestrict = detailDocument.getElementsByClass("msg ltype").text();
                        String jobDesc = detailDocument.getElementsByClass("bmsg job_msg inbox").text();
                        String jobLocationDetail = "";
                        if (detailDocument.getElementsByClass("bmsg inbox").size() > 0) {
                            jobLocationDetail = detailDocument.getElementsByClass("bmsg inbox").first().child(0).text();
                        }
                        String companyDesc = detailDocument.getElementsByClass("tmsg inbox").text();
                        String companyImage = "";
                        if (detailDocument.getElementsByClass("com_name himg").size() > 0) {
                            companyImage = detailDocument.getElementsByClass("com_name himg").first().child(0).attr("src");
                        }


                        jobModel.setJobRestrictStr(jobRestrict);
                        jobModel.setJobDetail(jobDesc);
                        jobModel.setJobLocationDetail(jobLocationDetail);
                        jobModel.setJobCompanyDesc(companyDesc);
                        jobModel.setJobCompanyImage(companyImage);

                        String jobId = "";
                        String patternStr = "/[0-9]*.html";
                        Pattern pattern = Pattern.compile(patternStr);
                        Matcher matcher = pattern.matcher(jobUrl);
                        if (matcher.find()) {
                            jobId = matcher.group();
                            jobId = jobId.replaceAll(".html", "").replaceAll("/","");
                        }
                        if (StringUtils.isEmpty(jobId)) {
                            patternStr = "jobid=[0-9]*";
                            pattern = Pattern.compile(patternStr);
                            matcher = pattern.matcher(jobUrl);
                            if (matcher.find()) {
                                jobId = matcher.group();
                                jobId = jobId.replaceAll("jobid=", "");
                            }
                        }
                        if (StringUtils.isEmpty(jobId)) {
                            patternStr = "#[0-9]*";
                            pattern = Pattern.compile(patternStr);
                            matcher = pattern.matcher(jobUrl);
                            if (matcher.find()) {
                                jobId = matcher.group();
                                jobId = jobId.replaceAll("#", "");
                            }
                        }
                        jobModel.setJobId(jobId);
                        System.out.println(jobModel);
                        jobModelList.add(jobModel);
                    }
                }
                if (!CollectionUtils.isEmpty(jobModelList)) {
                    System.out.println("第" + page + "页数据，开始插入数据");
                    jobMapper.insertList(jobModelList);
                }
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                if (null != jobModelList) {
                    fileUtil.writeLog(jobModelList.toString());
                }
            }
        }
    }

}
