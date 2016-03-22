package com.zzj.daily.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzj.daily.R;

/**
 * Created by xiaoJ on 2016/3/12.
 * 不带头部时间的cardView
 */
public class SimpleItemHolder extends RecyclerView.ViewHolder {
    private TextView textView_title;
    private ImageView imageView_image;
    public SimpleItemHolder(View itemView) {
        super(itemView);
        textView_title = (TextView) itemView.findViewById(R.id.textView_title);
        imageView_image = (ImageView) itemView.findViewById(R.id.imageView_image);
    }

    public TextView getTextView_title() {
        return textView_title;
    }

    public ImageView getImageView_image() {
        return imageView_image;
    }
}
