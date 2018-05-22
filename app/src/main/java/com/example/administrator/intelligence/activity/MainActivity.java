package com.example.administrator.intelligence.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.intelligence.R;
import com.example.administrator.intelligence.adapter.ChatMessageAdapter;
import com.example.administrator.intelligence.bean.ChatMessage;
import com.example.administrator.intelligence.bean.ChatMessage.Type;
import com.example.administrator.intelligence.bean.EventsID;
import com.example.administrator.intelligence.bean.ViewEvent;
import com.example.administrator.intelligence.utils.Config;
import com.example.administrator.intelligence.utils.HttpUtils;
import com.example.administrator.intelligence.utils.MscManager;
import com.example.common.common.base.BaseActivity;
import com.example.common.common.utils.PermissionsUtils;
import com.example.common.common.view.SharedPreferencesUtils;
import com.flyco.animation.FlipEnter.FlipTopEnter;
import com.flyco.animation.FlipExit.FlipHorizontalExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {
    /**
     * 展示消息的listview
     */
    private ListView mChatView;
    /**
     * 文本域
     */
    private EditText mMsg;
    /**
     * 聊天背景
     */
    private RelativeLayout chat_rl;
    /**
     * 聊天输入框
     */
    private RelativeLayout ly_chat_bottom;
    /**
     * 头部姓名栏
     */
    private RelativeLayout ly_chat_title;
    /**
     * Bob菜单
     */
    private BoomMenuButton bmb_menu;
    /**
     * 存储聊天消息
     */
    private List<ChatMessage> mDatas = new ArrayList<ChatMessage>();
    /**
     * 适配器
     */
    private ChatMessageAdapter mAdapter;
    private int TYPE_MSG = 0;//信息类型 0为文字，1为语音
    private String TAG = "MainActivity";
    final String[] permission = new String[]{
            Manifest.permission.RECORD_AUDIO
    };
    private PermissionsUtils mPermissionsUtils;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            ChatMessage from = (ChatMessage) msg.obj;
            mDatas.add(from);
            mAdapter.notifyDataSetChanged();
            mChatView.setSelection(mDatas.size() - 1);
            ly_chat_bottom.setVisibility(View.VISIBLE);
            if (TYPE_MSG == 1) {
                // TODO: 2018/5/16 语音读取文字并显示,回归输入界面
                TYPE_MSG = 0;
                MscManager.getInstance().speech(from.getMsg());
            }
            // TODO: 2018/5/17 存储最后一次的聊天内容
            SharedPreferencesUtils.putString(MainActivity.this, Config.FINAL_RESULT, from.getMsg());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_chatting);
        initView();
        mAdapter = new ChatMessageAdapter(MainActivity.this, mDatas);
        mChatView.setAdapter(mAdapter);
        MscManager.getInstance().init(MainActivity.this);
        mPermissionsUtils = new PermissionsUtils(this)
                .setResultCallBack(this);
        this.setIsTitleBar(false);
        EventBus.getDefault().register(this);
        initListener();
    }

    private void initView() {
        chat_rl = findViewById(R.id.chat_rl);
        ly_chat_bottom = findViewById(R.id.ly_chat_bottom);
        mChatView = findViewById(R.id.id_chat_listView);
        ly_chat_title = findViewById(R.id.ly_chat_title);
        mMsg = findViewById(R.id.id_chat_msg);
        bmb_menu = findViewById(R.id.bmb_menu);
        //判断用户上一次操作的内容是否存在本地
        if (TextUtils.isEmpty(SharedPreferencesUtils.getString(MainActivity.this, Config.FINAL_RESULT, ""))) {
            mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, getString(R.string.default_welcome)));
        } else {
            mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, SharedPreferencesUtils.getString(MainActivity.this, Config.FINAL_RESULT, "")));
        }
        createNumber(1, 100);
        autoScrollView(chat_rl, ly_chat_bottom);//弹出软键盘时滚动视图
        for (int i = 0; i < bmb_menu.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //点击进入响应界面
                            if (index == 0) {
                                // TODO: 2018/5/18 拼图
                                Intent start_Beauty = new Intent(MainActivity.this, BeautyActivity.class);
                                startActivity(start_Beauty);
                            } else if (index == 1) {
                                // TODO: 2018/5/22 dribbble数据展示
                                Intent start_Beauty = new Intent(MainActivity.this,DribbbleActivity.class);
                                startActivity(start_Beauty);
                            } else if (index == 2) {

                            } else if (index == 3) {

                            } else if (index == 4) {

                            } else if (index == 5) {

                            } else if (index == 6) {

                            }
                        }
                    })
                    .normalImageRes(getImageResource())
                    .normalText(getext());
            bmb_menu.addBuilder(builder);
        }
        VoiceListener();
    }

    private static int index = 0;

    static String getext() {
        if (index >= text.length) {
            index = 0;
        }
        return text[index++];

    }

    private static String[] text = new String[]{"拼图游戏", "待开发", "待开发", "待开发", "待开发", "待开发", "待开发"};
    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) {
            imageResourceIndex = 0;
        }
        return imageResources[imageResourceIndex++];
    }

    private static int[] imageResources = new int[]{
            R.mipmap.dolphin,
            R.mipmap.owl,
            R.mipmap.deer,
            R.mipmap.butterfly,
            R.mipmap.bee,
            R.mipmap.bear,
            R.mipmap.eagle
    };

    /**
     * 初始化语言弹窗监听事件
     */
    private void initListener() {
        mAdapter.setOnContentClick(new ChatMessageAdapter.OnContentClickListener() {
            @Override
            public void getContentCLick(String content) {

            }
        });
        mAdapter.setOnContentLongClick(new ChatMessageAdapter.OnContentLongClickListener() {
            @Override
            public void getContentLong(final String content) {
                FlipTopEnter mBasIn = new FlipTopEnter();
                FlipHorizontalExit mBasOut = new FlipHorizontalExit();
                final MaterialDialog dialog = new MaterialDialog(MainActivity.this);
                dialog.content(getString(R.string.voice_play_content))
                        .btnText(getString(R.string.no_need), getString(R.string.str_need))//
                        .showAnim(mBasIn)//
                        .dismissAnim(mBasOut)//
                        .show();
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog.dismiss();
                            }
                        }, new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                MscManager.getInstance().speech(content);
                                dialog.dismiss();
                            }
                        });

            }
        });
    }

    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition = 0;

    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

    /**
     * 创建随机数值
     * 生成随机背景图
     */
    private void createNumber(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max) % (max - min + 1) + min;
        if (randomNumber % 3 == 0) {
            chat_rl.setBackgroundResource(R.mipmap.chat_bg_star);
        } else if (randomNumber % 7 == 0) {
            chat_rl.setBackgroundResource(R.mipmap.chat_bg_quarantine);
        } else {
            chat_rl.setBackgroundResource(R.mipmap.chat_bg_default);
        }
    }

    /**
     * 发送信息点击
     *
     * @param view
     */
    public void sendMessage(View view) {
        TYPE_MSG = 0;
        ResultMessage();
    }


    /**
     * 发送语音信息
     *
     * @param view
     */
    public void sendVoice(View view) {
        if (authorizeRuntimePermission()) {
            TYPE_MSG = 1;
            VoiceDialog();
        } else {
            mPermissionsUtils.requestRuntimePermissions(permission);
        }
    }

    /**
     * 添加实时监听功能
     */
    public void VoiceListener() {
        if (authorizeRuntimePermission()) {
            MscManager.getInstance().voiceDistinguish("iat", "zh_cn", "mandarin");
            MscManager.getInstance().getVoiceDistinguish(new MscManager.VoiceDistinguishListener() {
                @Override
                public void VoiceDistinguish(String result) {
                    if (result.equals("拼图游戏")) {
                        Intent start_Beauty = new Intent(MainActivity.this, BeautyActivity.class);
                        startActivity(start_Beauty);
                    }
                }

                @Override
                public void VoiceErrorListener(String result) {

                }
            });
        }
    }

    @Override
    protected String[] getRuntimePermissions() {
        final String[] permission = new String[]{
                Manifest.permission.RECORD_AUDIO
        };
        return permission;
    }

    //TODO 是否已经授权
    public boolean authorizeRuntimePermission() {
        if (null != mPermissionsUtils) {
            return mPermissionsUtils.authorizeRuntimePermissions(permission);
        }
        return false;
    }

    /**
     * 显示语音录制弹窗
     */
    private void VoiceDialog() {
        ly_chat_bottom.setVisibility(View.GONE);
        MscManager.getInstance().voiceDistinguish("iat", "zh_cn", "mandarin");
        MscManager.getInstance().getVoiceDistinguish(new MscManager.VoiceDistinguishListener() {
            @Override
            public void VoiceDistinguish(String result) {
                mMsg.setText(result);
//                //获取焦点
//                mMsg.requestFocus();
//                //将光标定位到文字最后，以便修改
//                mMsg.setSelection(result.length());
                ResultMessage();
            }

            @Override
            public void VoiceErrorListener(String result) {
                ly_chat_bottom.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * 发送当前的输入框内容
     */
    private void ResultMessage() {
        final String msg = mMsg.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            ly_chat_bottom.setVisibility(View.VISIBLE);
            Toast.makeText(this, getString(R.string.no_content), Toast.LENGTH_SHORT).show();
            return;
        }
        ChatMessage to = new ChatMessage(Type.OUTPUT, msg);
        to.setDate(new Date());
        mDatas.add(to);

        mAdapter.notifyDataSetChanged();
        mChatView.setSelection(mDatas.size() - 1);

        mMsg.setText("");

        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        new Thread() {
            public void run() {
                ChatMessage from = null;
                try {
                    from = HttpUtils.sendMsg(msg);
                } catch (Exception e) {
                    from = new ChatMessage(Type.INPUT, getString(R.string.http_error));
                }
                Message message = Message.obtain();
                message.obj = from;
                mHandler.sendMessage(message);
            }

        }.start();
    }

    //EventBus 回调接口
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执
    public void onEventMainThread(ViewEvent events) {
        if (events.getEvent() == EventsID.GONE_CHAT_BOOTM) {
            ly_chat_bottom.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
