package com.wind.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ProcessUtil
 * @Package com.wind.common.process
 * @Description: 进程执行工具类
 * @author wind
 * @date 2018/10/11 9:23
 * @version V1.0
 */
public class ProcessUtil {

    /**
     * 获取进程命令执行打印出来的信息
     * @return
     */
    public static List<String> exec(String command){
        Runtime r = Runtime.getRuntime();
        BufferedReader in = null;
        Process pro = null;
        List<String> lists = new ArrayList<>();
        try {
            pro = r.exec(command);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line;
            while((line = in.readLine()) != null){
                lists.add(line);
            }
        } catch (IOException e) {
            lists = null;
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(pro != null){
                pro.destroy();
            }
        }

        return lists;
    }
}
