package com.wind.resource;

import org.junit.Test;

import java.net.URL;

public class ResourceTest {

    @Test
    public void test(){
        URL url = ResourceUtil.getUrlFile("image/code.png");
        URL url2 = ResourceUtil.getUrlFile("/image/code.png");
        System.out.println(url);
        System.out.println(url2);

        String relPath = ResourceUtil.getProjectPath();
        System.out.println(relPath);

        String path = ResourceUtil.getClassPath();
        System.out.println(path);
    }
}
