package com.zzj.daily.task;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzj.daily.adapter.RecyclerViewAdapter;
import com.zzj.daily.bean.LatestNewsEntity;
import com.zzj.daily.support.Http;
import com.zzj.daily.support.MyApplication;
import com.zzj.daily.support.Tools;
import com.zzj.daily.ui.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiaoJ on 2016/3/8.
 * 将Json数据解析为实体类的异步任务
 * String 需要解析的Json字符串   LatestNewsEntity 实体类
 */
public class LatestNewsTask extends AsyncTask<String, Void, LatestNewsEntity> {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<LatestNewsEntity> latestNewsEntities;
    private String url;

    public LatestNewsTask(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, RecyclerViewAdapter recyclerViewAdapter, List<LatestNewsEntity> latestNewsEntities) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.latestNewsEntities = latestNewsEntities;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //在进行非触底加载时展示下拉刷新动画
        if (!MainActivity.onLoading) {
            this.swipeRefreshLayout.setRefreshing(true);
        }

    }

    @Override
    protected void onPostExecute(LatestNewsEntity latestNewsEntity) {
        super.onPostExecute(latestNewsEntity);
        if (latestNewsEntity == null) {
            Toast.makeText(MyApplication.getContext(), "获取数据失败", Toast.LENGTH_LONG).show();
            this.swipeRefreshLayout.setRefreshing(false);
            return;
        }
        //移除重复项
        for (int i = 0; i < latestNewsEntities.size(); i++) {
            if (latestNewsEntities.get(i).getDate().equals(latestNewsEntity.getDate())) {
                latestNewsEntities.remove(i);
                i--;
            }
        }
        latestNewsEntities.add(latestNewsEntity);
        //启动异步任务将数据更新到ui
        new SetNewsTask(swipeRefreshLayout, recyclerView, recyclerViewAdapter, latestNewsEntities).execute();
        //启动异步任务将数据写入缓存
        new SaveToCacheTask(url, latestNewsEntity).execute();

    }

    @Override
    protected LatestNewsEntity doInBackground(String... params) {
        LatestNewsEntity latestNewsEntity = new LatestNewsEntity();
        //如果没有网络连接，则返回null
        if (!Tools.isNetConnected()) {
            return null;
        }
        for (int i = 0; i < params.length; i++) {
            url = params[i];
            try {
                String json_LatestNewsEntity = Http.getJsonString(params[i]);
                if (json_LatestNewsEntity == null) {
                    return null;
                }
                JSONObject jsonObject_LatestNewsEntity = JSONObject.parseObject(json_LatestNewsEntity);
                latestNewsEntity.setDate(jsonObject_LatestNewsEntity.getString("date"));
                JSONArray jsonArray_stories = jsonObject_LatestNewsEntity.getJSONArray("stories");
                List<LatestNewsEntity.StoriesEntity> stories = new ArrayList<>();
                for (int j = 0; j < jsonArray_stories.size(); j++) {
                    LatestNewsEntity.StoriesEntity storiesEntity = new LatestNewsEntity.StoriesEntity();
                    JSONObject jsonObject_storiesEntity = jsonArray_stories.getJSONObject(j);
                    storiesEntity.setDate(latestNewsEntity.getDate());
                    storiesEntity.setId(jsonObject_storiesEntity.getInteger("id"));
                    storiesEntity.setGa_prefix(jsonObject_storiesEntity.getString("ga_prefix"));
                    storiesEntity.setTitle(jsonObject_storiesEntity.getString("title"));
                    storiesEntity.setType(jsonObject_storiesEntity.getInteger("type"));
                    JSONArray jsonArray_storiesEntityImages = jsonObject_storiesEntity.getJSONArray("images");
                    List<String> list = new ArrayList<>();
                    for (int z = 0; z < jsonArray_storiesEntityImages.size(); z++) {
                        list.add(jsonArray_storiesEntityImages.getString(z));
                    }
                    storiesEntity.setImages(list);
                    stories.add(storiesEntity);
                }
                latestNewsEntity.setStories(stories);
                JSONArray jsonArray_top_stories = jsonObject_LatestNewsEntity.getJSONArray("top_stories");
                List<LatestNewsEntity.TopStoriesEntity> topStoriesEntities = new ArrayList<>();

                if (jsonArray_top_stories != null) {
                    for (int j = 0; j < jsonArray_top_stories.size(); j++) {
                        JSONObject jsonObject_top_storyEntity = jsonArray_top_stories.getJSONObject(j);
                        LatestNewsEntity.TopStoriesEntity topStoriesEntity = new LatestNewsEntity.TopStoriesEntity();
                        topStoriesEntity.setType(jsonObject_top_storyEntity.getInteger("type"));
                        topStoriesEntity.setTitle(jsonObject_top_storyEntity.getString("title"));
                        topStoriesEntity.setGa_prefix(jsonObject_top_storyEntity.getString("ga_prefix"));
                        topStoriesEntity.setId(jsonObject_top_storyEntity.getInteger("id"));
                        topStoriesEntity.setImage(jsonObject_top_storyEntity.getString("image"));
                        topStoriesEntities.add(topStoriesEntity);
                    }
                    latestNewsEntity.setTop_stories(topStoriesEntities);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return latestNewsEntity;
    }
}
