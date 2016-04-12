package com.zzj.daily.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzj.daily.R;
import com.zzj.daily.cache.ACache;
import com.zzj.daily.task.DownloadImageTask;

public class ShowImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        aCache = ACache.get(this);
        imageView = (ImageView) findViewById(R.id.show_image);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        } else {
            if (aCache.getAsBitmap(url) != null) {
                imageView.setImageBitmap(aCache.getAsBitmap(url));
            }else{
                new DownloadImageTask(imageView).execute(new String[]{url});
            }
        }
    }
}
