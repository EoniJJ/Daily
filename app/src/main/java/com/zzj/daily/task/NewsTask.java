package com.zzj.daily.task;

import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzj.daily.bean.NewsEntity;
import com.zzj.daily.support.Http;
import com.zzj.daily.support.MyApplication;
import com.zzj.daily.support.Tools;
import com.zzj.daily.ui.MainActivity;
import com.zzj.daily.ui.NewsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoJ on 2016/3/15.
 * 获取新闻详情的异步任务
 */
public class NewsTask extends AsyncTask<String, Void, NewsEntity> {
    private ImageView imageView;
    private TextView textView_title;
    private TextView images_source;
    private String url;
    private WebView webView;
    private boolean isNightMode;

    public NewsTask(ImageView imageView, TextView textView_title, TextView images_source, WebView webView, boolean isNightMode) {
        this.imageView = imageView;
        this.textView_title = textView_title;
        this.images_source = images_source;
        this.webView = webView;
        this.isNightMode = isNightMode;
    }

    @Override
    protected void onPostExecute(NewsEntity newsEntity) {
        super.onPostExecute(newsEntity);
        if (newsEntity == null) {
            Toast.makeText(MyApplication.getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            return;
        }
        NewsActivity.setNewsEntity(newsEntity);
        this.textView_title.setText(newsEntity.getTitle());
        this.images_source.setText(newsEntity.getImage_source());
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/news.css\" type=\"text/css\">";
        String html = "";
        if (isNightMode) {
            html = "<html><head>" + css + "</head><body class=\"night\">" + newsEntity.getBody() + "</body></html>";
        } else {
            html = "<html><head>" + css + "</head><body>" + newsEntity.getBody() + "</body></html>";
        }
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        new DownloadImageTask(this.imageView).execute(new String[]{newsEntity.getImage()});
        new SaveToCacheTask(url, newsEntity).execute();
    }

    @Override
    protected NewsEntity doInBackground(String... params) {
        NewsEntity newsEntity = new NewsEntity();
        //如果没有网络连接，则返回null
        if (!Tools.isNetConnected()) {
            return null;
        }
        try {
            for (int i = 0; i < params.length; i++) {
                url = params[i];
                String jsonString = Http.getJsonString(params[i]);
                JSONObject jsonObject = JSON.parseObject(jsonString);
                newsEntity.setBody(jsonObject.getString("body"));
                newsEntity.setImage_source(jsonObject.getString("image_source"));
                newsEntity.setTitle(jsonObject.getString("title"));
                newsEntity.setImage(jsonObject.getString("image"));
                newsEntity.setShare_url(jsonObject.getString("share_url"));
                newsEntity.setGa_prefix(jsonObject.getString("ga_prefix"));
                newsEntity.setType(jsonObject.getInteger("type"));
                newsEntity.setId(jsonObject.getInteger("id"));
                JSONArray jsonArray_js = jsonObject.getJSONArray("js");
                List<String> js = new ArrayList<>();
                for (int j = 0; j < jsonArray_js.size(); j++) {
                    js.add(jsonArray_js.getString(j));
                }
                newsEntity.setJs(jsonArray_js);
                if (jsonObject.getJSONObject("section") != null) {
                    JSONObject jsonObject_section = jsonObject.getJSONObject("section");
                    NewsEntity.SectionEntity scSectionEntity = new NewsEntity.SectionEntity();
                    scSectionEntity.setId(jsonObject_section.getInteger("id"));
                    scSectionEntity.setThumbnail(jsonObject_section.getString("thumbnail"));
                    scSectionEntity.setName(jsonObject_section.getString("name"));
                    newsEntity.setSection(scSectionEntity);
                }
                List<String> list_css = new ArrayList<>();
                JSONArray jsonArray_css = jsonObject.getJSONArray("css");
                for (int j = 0; j < jsonArray_css.size(); j++) {
                    list_css.add(jsonArray_css.getString(j));
                }
                newsEntity.setCss(list_css);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsEntity;
    }
}
