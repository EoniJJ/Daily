package com.zzj.daily.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.zzj.daily.support.Http;
import java.io.IOException;


/**
 * Created by xiaoJ on 2016/3/9.
 * 下载图片的异步任务
 * String 图片的url  Bitmap 返回一张bitmap图片
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private String [] image_url;
    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        for (int i = 0; i < image_url.length; i++) {
            new SaveToCacheTask(image_url[i],bitmap).execute();
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        image_url = params;
        Bitmap bitmap = null;
        for (int i = 0; i < params.length; i++) {
            try {
                bitmap = Http.getImage(params[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
