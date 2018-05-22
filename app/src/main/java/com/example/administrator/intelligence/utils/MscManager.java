package com.example.administrator.intelligence.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.intelligence.bean.DictationResult;
import com.example.administrator.intelligence.bean.EventsID;
import com.example.administrator.intelligence.bean.ViewEvent;
import com.example.common.common.view.VoiceDistinguishDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;


/**
 * Created by 2018/5/16 15:57
 * 创建：Administrator on
 * 描述:科大讯飞语音识别和合成
 */
public class MscManager {
    private Context mContext;
    private String TAG = "MscManager";
    private SpeechSynthesizer speechSynthesizer;
    private static MscManager instance;
    private VoiceDistinguishListener voiceDistinguishListener;
    private static final int MSG_VOICE_CHANGED = 0X112;
    private VoiceDistinguishDialog voice_Dialog = null;
    private String[] Name_List = {"xiaoyan", "xiaoxin", "vils", "xiaomei", "xiaorong", "xiaoqian", "nannan", "vixy", "catherine", "henry", "xiaoyu", "xiaolin"};

    public static MscManager getInstance() {
        if (instance == null) {
            synchronized (MscManager.class) {
                if (null == instance) {
                    instance = new MscManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        initTts();
    }

    private void initTts() {
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, null);
        if (createNumber() < Name_List.length) {
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, Name_List[createNumber()]);//设置发音人
        } else {
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        }
        speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }

    /**
     * 创建随机数值
     */
    private int createNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(12) % (12 - 0 + 1) + 0;
        return randomNumber;
    }

    /**
     * 普通语音
     *
     * @param content 播放内容
     */
    public void speech(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        int code = speechSynthesizer.startSpeaking(content, mSynListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(mContext, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

    /**
     * 监听语音识别
     *
     * @param domain   地域
     * @param language 语言
     * @param accents  口音
     */
    private String resultJson = "";

    public void voiceDistinguish(String domain, String language, String accents) {
        // 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(mContext, null);
        //设置
        mIat.setParameter(SpeechConstant.DOMAIN, domain);
        mIat.setParameter(SpeechConstant.LANGUAGE, language);
        mIat.setParameter(SpeechConstant.ACCENT, accents);
        // 开始听写
        resultJson = "[";
        mIat.startListening(mRecognizerListener);
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {


        @Override
        public void onBeginOfSpeech() {
            //显示自定义的dialog
            voice_Dialog = new VoiceDistinguishDialog(mContext);
            voice_Dialog.show();
            voice_Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    EventBus.getDefault().post(new ViewEvent(EventsID.GONE_CHAT_BOOTM));
                }
            });
        }

        @Override
        public void onError(SpeechError speechError) {
            //隐藏自定义dialog
            voice_Dialog.setFlickerAnimation(voice_Dialog.getVoice_icon(), 3);
            voice_Dialog.setTitle(speechError.getErrorDescription());
            //自动生成的方法存根
            speechError.getPlainDescription(true);
            if (voiceDistinguishListener != null) {
                voiceDistinguishListener.VoiceErrorListener(speechError.getErrorDescription());
            }
        }

        @Override
        public void onEndOfSpeech() {
            //语音监听结束
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            Log.d(TAG, recognizerResult.getResultString());
            System.out.println("-----------------   onResult   -----------------");
            if (!isLast) {
                resultJson += recognizerResult.getResultString() + ",";
            } else {
                resultJson += recognizerResult.getResultString() + "]";
            }
            if (isLast) {
                //解析语音识别后返回的json格式的结果
                Gson gson = new Gson();
                List<DictationResult> resultList = gson.fromJson(resultJson,
                        new TypeToken<List<DictationResult>>() {
                        }.getType());
                String result = "";
                for (int i = 0; i < resultList.size() - 1; i++) {
                    result += resultList.get(i).toString();
                }
                if (voiceDistinguishListener != null) {
                    voiceDistinguishListener.VoiceDistinguish(result);
                    resultJson = "";
                    voice_Dialog.dismiss();
                }
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Log.d(TAG, "返回音频数据：" + data.length);
            Message msg = Message.obtain();
            msg.what = volume;
            mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 若使用本地能力，会话id为null
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                Log.d(TAG, "session id =" + sid);
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_VOICE_CHANGED:
                    voice_Dialog.updateVoiceLevel(msg.what);
                    break;
            }
        }
    };

    /**
     * 返回对应的弹窗
     *
     * @return
     */
    public VoiceDistinguishDialog getVoice_Dialog() {
        if (voice_Dialog != null) {
            return voice_Dialog;
        }
        return null;
    }

    /**
     * 对外开放监听语音内容
     *
     * @param voiceDistinguishListener
     */
    public void getVoiceDistinguish(VoiceDistinguishListener voiceDistinguishListener) {
        this.voiceDistinguishListener = voiceDistinguishListener;
    }

    public interface VoiceDistinguishListener {
        void VoiceDistinguish(String result);

        void VoiceErrorListener(String result);
    }
}
