package com.wind.resource;

import com.wind.common.Constants;
import com.wind.common.DateUtil;
import com.wind.common.IoUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 读取网络资源文件加载工具类
 */
public class UrlUtil {

    /**
     * 解析url
     * @param url
     */
    public static void parseUrl(String url){
        BufferedReader reader = null;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            // 设置连接超时时间
            connection.setConnectTimeout(DateUtil.MINUTE);
            // 设置读超时时间
            connection.setReadTimeout(DateUtil.MINUTE);
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）
            connection.connect();

            // 获得响应状态信息
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("请求失败...");
                return;
            }

            // 获得头信息
            Map<String, List<String>> headers = connection.getHeaderFields();
            Iterator<Map.Entry<String, List<String>>> iterator = headers.entrySet()
                    .iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

            System.out.println("响应内容如下：");
            // 内容是文本，直接以缓冲字符流读取
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), Constants.UTF8));
            String data;
            while ((data = reader.readLine()) != null)
            {
                System.out.println(data);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(reader);
        }
    }


    public static void main(String[] args) {
        String url = "https://api.douban.com/v2/book/1220562";
//        String url = "http://m10.music.126.net/20170923151900/4a64b494c33e1478efaeb81b87124d38/ymusic/44cd/a68c/dd67/6332f5737efbac32d2878332a68bb2a8.mp3";
        parseUrl(url);
    }

}
