package com.devin.jobsearch.mapper;

import com.devin.jobsearch.model.JobModel;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author Devin Zhang
 * @className JobMapper
 * @description TODO
 * @date 2020/4/22 16:24
 */

public interface JobMapper extends Mapper<JobModel>, MySqlMapper<JobModel> {
}
