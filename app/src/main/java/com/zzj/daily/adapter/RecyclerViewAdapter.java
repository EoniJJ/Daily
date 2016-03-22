package com.zzj.daily.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzj.daily.R;
import com.zzj.daily.bean.LatestNewsEntity;
import com.zzj.daily.cache.ACache;
import com.zzj.daily.support.MyApplication;
import com.zzj.daily.task.DownloadImageTask;
import com.zzj.daily.task.ViewPagerTask;
import com.zzj.daily.ui.GroupItemHolder;
import com.zzj.daily.ui.NewsActivity;
import com.zzj.daily.ui.SimpleItemHolder;
import com.zzj.daily.ui.TopItemHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoJ on 2016/3/12.
 * RecyclerView适配器
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    //头部viewPager
    private static final int TOP_ITEM = 3;
    //不带时间的cardView
    private static final int SIMPLE_ITEM = 0;
    //带时间的cardView
    private static final int GROUP_ITEM = 1;
    //新闻实体类
    private List<LatestNewsEntity> latestNewsEntities;
    private LayoutInflater mLayoutInflater;
    private List<LatestNewsEntity.StoriesEntity> storiesEntityLis;
    //图片内存缓存
    private LruCache<String, Bitmap> lruCache;
    private ACache aCache;

    private ScheduledExecutorService scheduledExecutorService;

    public RecyclerViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        aCache = ACache.get(MyApplication.getContext());
        this.lruCache = MyApplication.getLruCache();
    }

    public void setLatestNewsEntities(List<LatestNewsEntity> latestNewsEntities) {
        storiesEntityLis = new ArrayList<>();
        for (int i = 0; i < latestNewsEntities.size(); i++) {
            for (int j = 0; j < latestNewsEntities.get(i).getStories().size(); j++) {
                storiesEntityLis.add(latestNewsEntities.get(i).getStories().get(j));
            }
        }

        this.latestNewsEntities = latestNewsEntities;
    }

    public List<LatestNewsEntity> getLatestNewsEntities() {
        return latestNewsEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TOP_ITEM) {
            return new TopItemHolder(mLayoutInflater.inflate(R.layout.cardview_top_item, parent, false));
        }
        if (viewType == GROUP_ITEM) {
            GroupItemHolder groupItemHolder = new GroupItemHolder(mLayoutInflater.inflate(R.layout.carview_group_item, parent, false));
            groupItemHolder.itemView.setOnClickListener(this);
            return groupItemHolder;
        } else {
            SimpleItemHolder simpleItemHolder = new SimpleItemHolder(mLayoutInflater.inflate(R.layout.cardview_simple_item, parent, false));
            simpleItemHolder.itemView.setOnClickListener(this);
            return simpleItemHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (latestNewsEntities == null) {
            return;
        }
        if (position == 0) {
            bindTopItem(latestNewsEntities.get(0), holder);
            return;
        }
        LatestNewsEntity.StoriesEntity storiesEntity = storiesEntityLis.get(position - 1);
        if (holder instanceof GroupItemHolder) {
            bingGroupItem(storiesEntity, holder);
        } else {
            bindSimpleItem(storiesEntity, holder);
        }
    }

    @Override
    public int getItemCount() {

        //TOP_ITEM占一个item
        int count = 1;
        count += storiesEntityLis.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP_ITEM;
        } else if (position == 1) {
            return GROUP_ITEM;
        } else {
            String dateCurrent = storiesEntityLis.get(position - 1).getDate();
            return dateCurrent.equals(storiesEntityLis.get(position - 2).getDate()) ? SIMPLE_ITEM : GROUP_ITEM;
        }
    }

    @Override
    public long getItemId(int position) {
        return storiesEntityLis.get(position - 1).getId();
    }

    /**
     * 构建带时间的新闻
     *
     * @param storiesEntity 消息实体类
     * @param holder        View控件
     */
    public void bingGroupItem(LatestNewsEntity.StoriesEntity storiesEntity, RecyclerView.ViewHolder holder) {
        ((GroupItemHolder) holder).getTextView_time().setText(storiesEntity.getDate());
        bindSimpleItem(storiesEntity, holder);
        ((GroupItemHolder)holder).itemView.setTag(storiesEntity.getId());
    }

    /**
     * 构造不带时间的新闻
     *
     * @param storiesEntity 消息实体类
     * @param holder        View控件
     */
    public void bindSimpleItem(LatestNewsEntity.StoriesEntity storiesEntity, RecyclerView.ViewHolder holder) {
        ((SimpleItemHolder) holder).getTextView_title().setText(storiesEntity.getTitle());
        ((SimpleItemHolder)holder).itemView.setTag(storiesEntity.getId());
        ((SimpleItemHolder) holder).getImageView_image().setImageResource(R.drawable.image_start);

        if (lruCache.get(storiesEntity.getImages().get(0)) != null) {
            ((SimpleItemHolder) holder).getImageView_image().setImageBitmap(lruCache.get(storiesEntity.getImages().get(0)));
        } else if (aCache.getAsBitmap(storiesEntity.getImages().get(0)) != null) {
            ((SimpleItemHolder) holder).getImageView_image().setImageBitmap(aCache.getAsBitmap(storiesEntity.getImages().get(0)));
        } else {
            new DownloadImageTask(((SimpleItemHolder) holder).getImageView_image()).execute(storiesEntity.getImages().get(0));
        }
    }

    /**
     * 构建顶部ViewPager
     *
     * @param latestNewsEntity 消息实体类
     * @param holder           View控件
     */
    public void bindTopItem(LatestNewsEntity latestNewsEntity, RecyclerView.ViewHolder holder) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        viewPagerAdapter.setTopStoriesEntities(latestNewsEntity.getTop_stories());
        ((TopItemHolder) holder).getViewPager().setAdapter(viewPagerAdapter);
        ((TopItemHolder) holder).getDotsRadioGroup().setDotView(((TopItemHolder) holder).getViewPager(), latestNewsEntity.getTop_stories().size());
        viewPagerAdapter.notifyDataSetChanged();
        //启动一个间隔线程任务实现ViewPager图片轮播
        ViewPagerTask viewPagerTask = new ViewPagerTask((TopItemHolder) holder, latestNewsEntity.getTop_stories().size());
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(viewPagerTask, 8, 8, TimeUnit.SECONDS);
    }

    /**
     * 重写监听事件  点击跳转activity并将Id通过intent传递到新的activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(MyApplication.getContext(), NewsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", (Integer) v.getTag());
        MyApplication.getContext().startActivity(intent);
    }
}
