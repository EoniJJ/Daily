package com.zzj.daily.task;

import android.os.Handler;
import android.os.Message;

import com.zzj.daily.ui.TopItemHolder;

/**
 * Created by xiaoJ on 2016/3/14.
 * ViewPager图片轮播任务
 */
public class ViewPagerTask implements Runnable {
    //头部新闻对象
    private TopItemHolder topItemHolder;
    //ViewPager的item总数目
    private int itemCount;
    //当前选择的item
    private int currentItem;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            topItemHolder.getViewPager().setCurrentItem(currentItem);

        }
    };

    public ViewPagerTask(TopItemHolder topItemHolder, int itemCount) {
        this.topItemHolder = topItemHolder;
        this.itemCount = itemCount;
        this.currentItem = topItemHolder.getDotsRadioGroup().getmPosition();
    }

    @Override
    public void run() {
        this.currentItem = topItemHolder.getDotsRadioGroup().getmPosition();
        currentItem = (currentItem + 1) % itemCount;
        handler.sendEmptyMessage(0);
    }

}
