package com.devin.jobsearch.Service;

/**
 * @author Devin Zhang
 * @className IJobHandle
 * @description TODO
 * @date 2020/4/22 16:39
 */

public interface IJobHandle {

    int getJobPage() throws Exception;

    void handle() throws Exception;
}
