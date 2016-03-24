package com.zzj.daily.task;

import android.os.AsyncTask;
import android.widget.Toast;

import com.zzj.daily.cache.ACache;
import com.zzj.daily.support.MyApplication;

/**
 * Created by xiaoJ on 2016/3/17.
 * 清除缓存的异步任务
 */
public class ClearCacheTask extends AsyncTask<Void,Void,Void> {
    //缓存类
    private ACache aCache;

    public ClearCacheTask(ACache aCache){
        this.aCache =aCache;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(MyApplication.getContext(),"正在清除缓存..",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(MyApplication.getContext(),"清除缓存成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        aCache.clear();
        return null;
    }
}
