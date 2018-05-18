package com.example.administrator.intelligence.base;

import com.example.administrator.intelligence.R;
import com.example.common.common.base.BaseApplication;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.x;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 2018/5/16 10:20
 * 创建：Administrator on
 * 描述:启动初始化
 */
public class IntelligenceApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(true);
        initRouter();
        SpeechUtility.createUtility(this, SpeechConstant.APPID + getString(R.string.app_id));
    }

    private void initRouter() {

//        ARouter.openLog();     // 打印日志
//        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//
//        ARouter.init(this);
    }
}
