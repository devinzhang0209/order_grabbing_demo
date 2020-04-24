package com.devin.jobsearch.util;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Devin Zhang
 * @className FileUtil
 * @description TODO
 * @date 2020/4/22 17:56
 */
@Component
public class FileUtil {

    private String filePath = "D:\\data\\job\\fail.log";

    public void writeLog(String log) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath), true)));
            bw.write(log);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
