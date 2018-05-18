package com.example.common.common.view;/**
 * Created by Administrator on 2017/4/7.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.common.R;


/**
 * 2017/4/7
 * wangxiaoer
 * 功能描述：
 **/
public class TitleBarView extends RelativeLayout {
    public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context, attrs);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.title, null);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.titleBar);
        String title_context = a.getString(R.styleable.titleBar_title_content);
        String title_right_context;
        title_right_context = a
                .getString(R.styleable.titleBar_title_right_tv_content);
        if (title_right_context != null && title_right_context.length() != 0) {

        } else {
            title_right_context = context.getResources().getString(R.string.save);
        }

        int title_tv_fontcolor = a.getColor(
                R.styleable.titleBar_title_fontcolor, context.getResources()
                        .getColor(R.color.white));
        int title_right_tv_fontcolor = a.getColor(
                R.styleable.titleBar_title_right_tv_fontcolor, context
                        .getResources().getColor(R.color.white));
        String visible = a
                .getString(R.styleable.titleBar_title_right_tv_visible);
        String leftIvVisible = a.getString(R.styleable.titleBar_title_left_iv_visible);
        title_tv = (TextView) view.findViewById(R.id.title_tv);
        right_tv = (TextView) view.findViewById(R.id.right_tv);
        right_img = (ImageView) view.findViewById(R.id.right_img);
        left_img = (ImageView) view.findViewById(R.id.backImageView);
        title_tv.setText(title_context);
        title_tv.setTextColor(title_tv_fontcolor);
        right_tv.setText(title_right_context);
        right_tv.setTextColor(title_right_tv_fontcolor);
        if (visible != null && "visible".equals(visible.toLowerCase())) {
            right_tv.setVisibility(View.VISIBLE);
        } else {
            right_tv.setVisibility(View.INVISIBLE);
        }
        if (leftIvVisible != null && "invisible".equals(leftIvVisible.toLowerCase())) {
            left_img.setVisibility(View.INVISIBLE);
        } else {
            left_img.setVisibility(View.VISIBLE);
        }
        left_img.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        leftCLick.click();
                    }
                });
        right_tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sureClick.click(arg0);
            }
        });
        right_img.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                rightClick.click();
            }
        });
        a.recycle();
        addView(view);
    }

    private LeftClick leftCLick;
    private RightClick rightClick;
    private SureClick sureClick;
    private TextView title_tv, right_tv;
    private ImageView right_img, left_img;


    public interface SureClick {
        void click(View arg0);
    }

    public interface LeftClick {
        void click();
    }
    public interface RightClick {
        void click();
    }
    public void setTitle(String titleStr) {
        title_tv.setText(titleStr);
    }

    public void setright_tv(String title) {
        right_tv.setText(title);
    }

    public void setrightImag( boolean isTrue) {
        if (isTrue) {
            right_img.setVisibility(View.VISIBLE);
        } else {
            right_img.setVisibility(View.GONE);
        }
    }

    public void setHideLeft() {
        left_img.setVisibility(View.GONE);
    }

    public void setVisible() {
        title_tv.setVisibility(View.VISIBLE);
    }


    public void setleftImgVisible() {
        left_img.setVisibility(View.VISIBLE);
    }

    public void setLeftClick(LeftClick leftCLick) {
        this.leftCLick = leftCLick;
    }
    public void setRightClick(RightClick rightClick) {
        this.rightClick = rightClick;
    }
    public void setSureClick(SureClick sureClick) {
        this.sureClick = sureClick;
    }

    public void changeLeftImage(int resId) {
        left_img.setImageResource(resId);
    }

    public void changeRightImage(int resId) {
        right_img.setImageResource(resId);
    }


}