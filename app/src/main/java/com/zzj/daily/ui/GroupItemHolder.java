package com.zzj.daily.ui;

import android.view.View;
import android.widget.TextView;

import com.zzj.daily.R;

/**
 * Created by xiaoJ on 2016/3/12.
 * 带头部时间的cardView
 */
public class GroupItemHolder  extends SimpleItemHolder{
    //头部时间显示
    private TextView textView_time;
    public GroupItemHolder(View itemView) {
        super(itemView);
        textView_time = (TextView) itemView.findViewById(R.id.textView_time);
    }
    public TextView getTextView_time() {
        return textView_time;
    }
}
