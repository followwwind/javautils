package com.wind.okhttp;

import okhttp3.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * http请求工具类
 * @author wind
 */
public class HttpUtil {

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
        String url = "https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login" +
                "&rand=sjrand&0.229965615247677";

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                .addHeader("Referer", "https://kyfw.12306.cn/otn/login/init")
                .url(url).build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            InputStream in = body.byteStream();
            FileOutputStream out = new FileOutputStream("E:/work/img/1.png");
            byte[] bytes = new byte[1024];
            int len = -1;
            while((len = in.read(bytes, 0, bytes.length)) != -1){
                out.write(bytes, 0, len);
            }

            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
