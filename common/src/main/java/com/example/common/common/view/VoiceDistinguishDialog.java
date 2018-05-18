package com.example.common.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

/**
 * Created by 2018/5/17 10:40
 * 创建：Administrator on
 * 描述:语音识别弹窗
 */
public class VoiceDistinguishDialog extends Dialog {
    private TextView voice_title;
    private ImageView voice_icon;


    public VoiceDistinguishDialog(Context context) {
        super(context, R.style.AlertDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_distinguish_dialog);
        voice_title = findViewById(R.id.voice_title);
        voice_icon = findViewById(R.id.voice_icon);
        setCanceledOnTouchOutside(false);
        setFlickerAnimation(voice_icon, 2);
    }

    /**
     * 闪烁动画
     *
     * @param iv_chat_head 对应的图标
     * @param Staus        对应的状态值
     */
    public void setFlickerAnimation(ImageView iv_chat_head, int Staus) {
        //创建旋转动画
        Animation animation_rotate = new RotateAnimation(0, 359);
        animation_rotate.setDuration(500);
        animation_rotate.setRepeatCount(8);//动画的重复次数
        animation_rotate.setFillAfter(true);//设置为true，动画转化结束后被应用
        //创建闪烁动画
        final Animation animation_alpha = new AlphaAnimation(1, 0);
        animation_alpha.setDuration(750);//闪烁时间间隔
        animation_alpha.setInterpolator(new AccelerateDecelerateInterpolator());
        animation_alpha.setRepeatCount(Animation.INFINITE);
        animation_alpha.setRepeatMode(Animation.REVERSE);
        if (Staus == 1) {
            //旋转动画
            iv_chat_head.clearAnimation();
            iv_chat_head.setBackgroundResource(R.drawable.waiting);
            if (animation_rotate != null) {
                iv_chat_head.startAnimation(animation_rotate);
            }
        } else if (Staus == 2) {
            //闪烁动画
            iv_chat_head.clearAnimation();
            if (animation_alpha != null) {
                iv_chat_head.setAnimation(animation_alpha);
            }
        } else if (Staus == 3) {
            iv_chat_head.clearAnimation();
            iv_chat_head.setBackgroundResource(R.drawable.warning);
        }
    }

    public VoiceDistinguishDialog setTitle(String title) {
        voice_title.setText(title + "");
        return this;
    }

    /**
     * 获取图片View
     *
     * @return
     */
    public ImageView getVoice_icon() {
        if (voice_icon != null) {
            return voice_icon;
        }
        return null;
    }

    /**
     * 根据语音大小更新图片
     *
     * @param level
     */
    public void updateVoiceLevel(int level) {
        if (level > 0) {
            voice_icon.setBackgroundResource(R.drawable.voice_full);
        } else {
            voice_icon.setBackgroundResource(R.drawable.voice_empty);
        }
    }

}
