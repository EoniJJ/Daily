package com.zzj.daily.support;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xiaoJ on 2016/3/8.
 * 请求网络获取数据
 */
public class Http {
    /**
     * 从网络上获取Json数据的字符串
     * @param urlAddr url地址
     * @return   json数据字符串
     * @throws IOException
     */
    public static String getJsonString(String urlAddr) throws IOException {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        if (Tools.isNetConnected()) {
            try {
                url = new URL(urlAddr);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] date = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(date)) != -1) {
                        byteArrayOutputStream.write(date, 0, len);
                    }
                    return new String(byteArrayOutputStream.toByteArray());
                } else {
                    throw new IOException("Network Error - response code: " + httpURLConnection.getResponseCode());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 从网络上获取图片
     * @param urlAddr 图片url
     * @return 一张bitmap图片
     * @throws IOException
     */
    public static Bitmap getImage(String urlAddr) throws IOException {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        if (Tools.isNetConnected()) {
            try {
                url = new URL(urlAddr);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] date = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(date)) != -1) {
                        byteArrayOutputStream.write(date, 0, len);
                    }
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                    return bitmap;
                } else {
                    throw new IOException("Network Error - response code: " + httpURLConnection.getResponseCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
