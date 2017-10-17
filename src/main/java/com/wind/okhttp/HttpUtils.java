package com.wind.okhttp;

import okhttp3.*;

import java.io.IOException;

/**
 * http请求工具类
 * @author wind
 */
public class HttpUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private  static OkHttpClient client = new OkHttpClient();


    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static Call get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request);
    }


    /**
     * post请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static Call post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request);
    }


    public static void main(String[] args) {
        String url = "http://192.168.1.44:8181/hb/Advertisement/getPageList.json";
        String json = "{}";
        try {
            Response response = get("https://www.baidu.com").execute();
            int code = response.code();
            System.out.println(code);
            System.out.println(response.body().string());
            /*post(url, json).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int code = response.code();
                    System.out.println(code);
                    System.out.println(response.body().string());

                    System.exit(0);
                }
            });*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
