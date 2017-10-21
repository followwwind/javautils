package com.wind.sysinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程执行工具类
 * @author wind
 */
public class ProcessUtils {

    /**
     * 获取进程命令执行打印出来的信息
     * @return
     */
    public static List<String> getProcessInfo(String command){
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
            pro.destroy();
        }

        return lists;
    }
}
