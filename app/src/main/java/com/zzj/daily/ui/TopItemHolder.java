package com.zzj.daily.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzj.daily.R;

/**
 * Created by xiaoJ on 2016/3/13.
 * 头部ViewPager
 */
public class TopItemHolder extends RecyclerView.ViewHolder {
    private ViewPager viewPager;
    //圆点指示器
    private DotsRadioGroup dotsRadioGroup;
    public TopItemHolder(View itemView) {
        super(itemView);
        viewPager = (ViewPager) itemView.findViewById(R.id.viewPager_topStories);
        dotsRadioGroup = (DotsRadioGroup) itemView.findViewById(R.id.drg_dots);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public DotsRadioGroup getDotsRadioGroup() {
        return dotsRadioGroup;
    }
}
