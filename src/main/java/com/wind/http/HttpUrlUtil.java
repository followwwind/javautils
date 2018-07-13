package com.wind.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wind.common.Const;
import com.wind.common.IOUtil;

/**
 * HttpURLConnection网络工具类
 * @author huanghy
 *
 */
public class HttpUrlUtil {
	
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
            connection.setConnectTimeout(Const.MINUTE);
            // 设置读超时时间
            connection.setReadTimeout(Const.MINUTE);
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
                    connection.getInputStream(), Const.UTF8));
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
            IOUtil.close(reader);
        }
    }

}
