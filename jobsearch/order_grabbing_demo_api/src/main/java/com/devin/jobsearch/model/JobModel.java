package com.devin.jobsearch.model;

import javax.persistence.Table;

/**
 * @author Devin Zhang
 * @className JobModel
 * @description TODO
 * @date 2020/4/22 9:42
 */
@Table(name = "job")
public class JobModel {
    private String jobId;
    private String jobName;
    private String jobDetail;
    private String jobCompanyName;
    private String jobCompanyImage;
    private String jobCompanyDesc;
    private String jobCompanyUrl;
    private String jobUrl;
    private String jobLocation;
    private String jobLocationDetail;
    private String jobSalary;
    private String jobDate;
    private String jobRestrictStr;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    public String getJobCompanyName() {
        return jobCompanyName;
    }

    public void setJobCompanyName(String jobCompanyName) {
        this.jobCompanyName = jobCompanyName;
    }

    public String getJobCompanyDesc() {
        return jobCompanyDesc;
    }

    public void setJobCompanyDesc(String jobCompanyDesc) {
        this.jobCompanyDesc = jobCompanyDesc;
    }

    public String getJobCompanyUrl() {
        return jobCompanyUrl;
    }

    public void setJobCompanyUrl(String jobCompanyUrl) {
        this.jobCompanyUrl = jobCompanyUrl;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobLocationDetail() {
        return jobLocationDetail;
    }

    public void setJobLocationDetail(String jobLocationDetail) {
        this.jobLocationDetail = jobLocationDetail;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobCompanyImage() {
        return jobCompanyImage;
    }

    public void setJobCompanyImage(String jobCompanyImage) {
        this.jobCompanyImage = jobCompanyImage;
    }

    public String getJobRestrictStr() {
        return jobRestrictStr;
    }

    public void setJobRestrictStr(String jobRestrictStr) {
        this.jobRestrictStr = jobRestrictStr;
    }

    @Override
    public String toString() {
        return "JobModel{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobDetail='" + jobDetail + '\'' +
                ", jobCompanyName='" + jobCompanyName + '\'' +
                ", jobCompanyImage='" + jobCompanyImage + '\'' +
                ", jobCompanyDesc='" + jobCompanyDesc + '\'' +
                ", jobCompanyUrl='" + jobCompanyUrl + '\'' +
                ", jobUrl='" + jobUrl + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", jobLocatonDetail='" + jobLocationDetail + '\'' +
                ", jobSalary='" + jobSalary + '\'' +
                ", jobDate='" + jobDate + '\'' +
                ", jobRestrictStr='" + jobRestrictStr + '\'' +
                '}';
    }
}
