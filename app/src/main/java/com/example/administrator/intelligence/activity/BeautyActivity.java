package com.example.administrator.intelligence.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.administrator.intelligence.BeautyGame.GamePintuLayout;
import com.example.administrator.intelligence.R;
import com.example.administrator.intelligence.bean.EventsID;
import com.example.administrator.intelligence.bean.ViewEvent;
import com.example.common.common.base.BaseActivity;
import com.example.common.common.view.BombView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

public class BeautyActivity extends BaseActivity {
    private BombView mBombView;
    private GamePintuLayout id_gameview;

    private Timer timer_manager = null;
    private TimerTask task_led;
    private final int TIME_MESSAGE = 2018;//标识
    private int COUNT_RECEIVE = 0;//限制执行次数
    /**
     * 计时器结束
     */
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case TIME_MESSAGE:
                    COUNT_RECEIVE++;
                    if (COUNT_RECEIVE > 4) {
                        mBombView.setVisibility(View.GONE);
                        id_gameview.setVisibility(View.VISIBLE);
                        clearTime();
                        return;
                    }
                    mBombView.setVisibility(View.VISIBLE);
                    id_gameview.setVisibility(View.GONE);
                    mBombView.startBomb();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);
        mBombView = findViewById(R.id.bombview);
        id_gameview = findViewById(R.id.id_gameview);
        id_gameview.setVisibility(View.VISIBLE);
        mBombView.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
    }

    //EventBus 回调接口
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执
    public void onEventMainThread(ViewEvent events) {
        if (events.getEvent() == EventsID.SUCCESS_NEXTLEVEL) {
            startConnectTimeout();
        }
    }

    /**
     * 开启计时器
     * 计时10秒的反复连接操作
     */
    private void startConnectTimeout() {
        if (timer_manager == null) {
            timer_manager = new Timer();
            task_led = new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = TIME_MESSAGE;
                    handler.sendMessage(message);
                }
            };
            // TODO: 2017/12/6 参数二表示延迟时间 参数三表示执行时间
            timer_manager.schedule(task_led, 1000, 1000);
        }
    }

    /**
     * 清理时间
     * 停止执行计时器
     */
    private void clearTime() {
        COUNT_RECEIVE = 0;
        if (timer_manager != null) {
            timer_manager.cancel();
            timer_manager.purge();
            timer_manager = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBombView.release();
        EventBus.getDefault().unregister(this);
    }
}
