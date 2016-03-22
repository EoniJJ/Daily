package com.zzj.daily.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.zzj.daily.bean.LatestNewsEntity;
import com.zzj.daily.bean.NewsEntity;
import com.zzj.daily.cache.ACache;
import com.zzj.daily.conn.ZhiHuDailyApi;
import com.zzj.daily.support.MyApplication;

/**
 * Created by xiaoJ on 2016/3/10.
 * 将数据写到缓存的异步任务
 */
public class SaveToCacheTask extends AsyncTask<Void,Void,Void> {
    private LatestNewsEntity latestNewsEntity;
    private String url;
    private String images_url;
    private Bitmap bitmap;
    private NewsEntity newsEntity;
    private ACache aCache;
   public SaveToCacheTask() {
       aCache = ACache.get(MyApplication.getContext());
   }

    public SaveToCacheTask(String url,NewsEntity newsEntity) {
        this();
        this.url = url;
        this.newsEntity = newsEntity;
    }
    public SaveToCacheTask(String url,LatestNewsEntity latestNewsEntity) {
        this();
        this.url = url;
        this.latestNewsEntity = latestNewsEntity;
    }
    public SaveToCacheTask(String images_url,Bitmap bitmap) {
        this();
        this.images_url = images_url;
        this.bitmap = bitmap;
    }
    public SaveToCacheTask(String url,LatestNewsEntity latestNewsEntity,String images_url,Bitmap bitmap) {
        this();
        this.url = url;
        this.latestNewsEntity = latestNewsEntity;
        this.images_url = images_url;
        this.bitmap = bitmap;
    }
    @Override
    protected Void doInBackground(Void... params) {
        if (latestNewsEntity!=null) {
            saveLatestNewsEntityToCache();
        }
        if (newsEntity != null) {
            saveNewsEntityToCache();
        }
        if (bitmap != null) {
            saveBitmapToCache();

            //如果内存缓存中不存在这张图片，把图片写入内存缓存
            if (MyApplication.getLruCache().get(images_url) == null) {
                saveBitmapToLruCache();
            }
        }
        return null;
    }

    /**
     * 将一个可序列化对象LatestNewsEntity写入缓存
     */
    private void saveLatestNewsEntityToCache() {
        aCache.put(url,latestNewsEntity);
    }

    /**
     * 将一个可序列化对象NewsEntity写入缓存
     */
    private void saveNewsEntityToCache() {
        aCache.put(url,newsEntity);
    }

    /**
     * 将一张bitmap图片写入缓存
     */
    private void saveBitmapToCache() {
        aCache.put(images_url,bitmap);
    }

    /**
     * 将一张图片写入内存缓存
     */
    private void saveBitmapToLruCache() {
        MyApplication.getLruCache().put(images_url, bitmap);
    }
}
