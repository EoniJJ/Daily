package com.zzj.daily.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zzj.daily.R;

/**
 * ViewPager指示点
 *
 * @author zhangke
 */
public class DotsRadioGroup extends RadioGroup implements OnPageChangeListener {

    /**
     * 上下文  
     */
    private Context mContext;
    /**
     * 关联的Viewpager  
     */
    private ViewPager mVPContent;

    /**
     * 当前显示指示点  
     */
    private int mPosition;

    /**
     * 指示点集合  
     */
    private RadioButton[] mDotsButton;

    /**
     * 构造方法  
     *
     * @param context   上下文  
     */
    public DotsRadioGroup(Context context) {
        super(context, null);
    }

    /**
     * 构造方法  
     * @param context   上下文  
     * @param attrs
     */
    public DotsRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 将上下文赋值给当前类  
        this.mContext = context;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        if (mDotsButton != null) {
            for (int i = 0; i < mDotsButton.length; i++) {
                boolean isChecked = i == mPosition ? true : false;
                mDotsButton[i].setChecked(isChecked);
            }
        }
    };

    /**
     * 获得该位置  
     *
     * @return
     */
    public int getmPosition() {
        return mPosition;
    }

    /**
     * 关联Viewpager并初始化指示点  
     *
     * @author zhangke
     * @Date 2015-9-25  
     * @param viewPager
     * @param pageCount
     */
    public void setDotView(ViewPager viewPager, int pageCount) {
        if (pageCount < 2) {
            setVisibility(View.GONE);
            return;
        }
        // 清理所有的点  
        setVisibility(View.VISIBLE);
        removeAllViews();
        mDotsButton = new RadioButton[pageCount];
        this.mVPContent = viewPager;
        mVPContent.setOnPageChangeListener(this);
        // 设置属性  
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        params.gravity = Gravity.CENTER;

        RadioButton radioButton = null;
        for (int i = 0; i < pageCount; i++) {
            radioButton = new RadioButton(mContext);
            radioButton.setButtonDrawable(R.drawable.selector_dots);
            radioButton.setLayoutParams(params);
            radioButton.setClickable(false);
            addView(radioButton, params);
            mDotsButton[i] = radioButton;
        }

        // 第一个默认选中  
        mDotsButton[0].setChecked(true);
    }

}  