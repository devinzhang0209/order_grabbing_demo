package com.devin.jobsearch.controller;

import com.devin.jobsearch.Service.Job51SearchHandle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Devin Zhang
 * @className JobController
 * @description TODO
 * @date 2020/4/22 16:36
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Resource
    private Job51SearchHandle job51SearchHandle;

    @GetMapping("/51jobHandle")
    public String handle51JobController() throws Exception {
        job51SearchHandle.handle();
        return "success";
    }
}
