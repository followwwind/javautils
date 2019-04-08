package com.wind.network.http;


import okhttp3.*;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 * @author wind
 *
 */
public class OkHttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType FILE = MediaType.parse("File/*");
    /**
     * 文件key
     */
    public static final String FILE_KEY = "file";

    private  static OkHttpClient client = new OkHttpClient();

    /**
     * 异步请求失败回调处理
     */
    @FunctionalInterface
    interface FailCallback{
        /**
         * 异步请求失败回调处理
         * @param call
         * @param e
         */
        void call(Call call, IOException e);
    }

    /**
     * 异步请求成功回调处理
     */
    @FunctionalInterface
    interface OkCallback{

        /**
         * 异步请求成功回调处理
         * @param call
         * @param response
         */
        void call(Call call, Response response);
    }

    /**
     * json get请求
     * application/json; charset=utf-8
     * @param url
     * @return
     */
    public static String getJson(String url){
        Request request = new Request.Builder().url(url).build();
        return execAsync(request);
    }


    /**
     * json post请求
     * application/json; charset=utf-8
     * @param url
     * @param json
     * @return
     */
    public static String postJson(String url, String json){
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        return execAsync(request);
    }

    /**
     * post表单请求
     * @param url
     * @param map
     * @return
     */
    public static String postForm(String url, Map<String, String> map){
        //创建表单请求体
        FormBody.Builder formBody = new FormBody.Builder();
        if(map != null && !map.isEmpty()){
            map.forEach(formBody::add);
        }
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        return execAsync(request);
    }

    /**
     * MultipartBody同时传递键值对参数和File对象
     * @param url
     * @param map
     * @param files
     * @return
     */
    public static String postMulti(String url, Map<String, String> map, List<File> files){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(map != null && !map.isEmpty()){
            map.forEach(builder::addFormDataPart);
        }

        if(files != null && !files.isEmpty()){
            files.forEach(file -> {
                RequestBody body = RequestBody.create(FILE, file);
                builder.addFormDataPart(FILE_KEY, file.getName(), body);
            });
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        return execAsync(request);
    }

    /**
     * MultipartBody同时传递键值对参数和File对象
     * @param url
     * @param multipartBody
     * @return
     */
    public static String postMulti(String url, MultipartBody multipartBody){
        Request request = new Request.Builder().url(url).post(multipartBody).build();
        return execAsync(request);
    }

    /**
     * 下载文件
     * @param request
     * @param targetFile
     */
    public static void down(Request request, String targetFile){
        ResponseBody responseBody = getBody(request);
        if(responseBody != null){

            try(
                InputStream in = responseBody.byteStream();
                FileOutputStream out = new FileOutputStream(targetFile, true)
            ) {
                byte[] bytes = new byte[1024];
                int len;
                while((len = in.read(bytes, 0, bytes.length)) != -1){
                    out.write(bytes, 0, len);
                }

                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 获取相应消息体
     * @param request
     * @return
     */
    public static ResponseBody getBody(Request request){
        ResponseBody responseBody = null;
        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }


    /**
     * 同步请求调用
     * @param request
     * @return
     */
    public static String execAsync(Request request){
        ResponseBody responseBody = getBody(request);
        String result = null;
        try {
            result =  responseBody != null ? responseBody.string() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 异步调用
     * @param request
     */
    public static void execSync(Request request, OkCallback ok, FailCallback fail){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fail.call(call, e);
            }

            @Override
            public void onResponse(Call call, Response response){
                ok.call(call, response);
            }
        });
    }
}
