package com.example.common.common.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.common.common.utils.AppManager;
import com.example.common.common.utils.ImmerseHelper;
import com.example.common.common.utils.Logger;
import com.example.common.common.utils.PermissionsUtils;

import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 2017/4/7
 * wangxiaoer
 * 功能描述：基类
 **/
public class BaseActivity extends Activity implements PermissionsUtils.IResultCallBack, ActivityCompat.OnRequestPermissionsResultCallback {
    private static BaseActivity activity;
    private PermissionsUtils mPermissionsUtils;
    private String TAG = "BaseActivity";
    private boolean isTitleBar = true;
    private boolean canSystemBarTransparent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppManager.addActiviy(this);
        activity = this;
        x.view().inject(this);// 注册xutils3注解
        Log.e("测试", "" + this);
    }

    /**
     * 启动activity
     *
     * @param pClass
     */
    protected void appStartActivity(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        startActivity(intent);
    }

    /**
     * 设置是否需要状态栏
     * 全屏时调用
     *
     * @param isTitleBar true开启全屏（默认全屏） false禁止全屏
     */
    public void setIsTitleBar(boolean isTitleBar) {
        this.isTitleBar = isTitleBar;
    }

    //TODO 置侵入式状态栏
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT
                && canSystemBarTransparent) {
            if (isTitleBar) {
                ImmerseHelper.setSystemBarTransparent(this);
            }
        }
    }

    //必须要在oncreate下调用
    protected void setCanSystemBarTransparent(boolean bset) {
        canSystemBarTransparent = bset;
    }

    /**
     * 特殊字符判断
     */
    public static boolean IsFilter(EditText et) throws PatternSyntaxException {

        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(et.getText().toString().trim());
        if (m.find()) {
            return false;
        }
        return true;
    }

    /**
     * 检测EditText不能为空;
     *
     * @param ets
     * @return true：表示EditTexts都不为空； false:表示有EditTexts为空；
     */

    public boolean chkEditText(EditText... ets) {
        for (EditText et : ets) {
            String str = et.getText().toString();
            if (TextUtils.isEmpty(str)) {
//	                et.setError(et.getHint() + "不能为空");
                return false;
            }
        }
        return true;
    }

    /**
     * 根据包名启动应用
     *
     * @param pakge_name
     * @param class_name
     */
    protected void appStartActivity(String pakge_name, String class_name) {
        ComponentName componetName = new ComponentName(
                //这个是另外一个应用程序的包名
                pakge_name,
                //这个参数是要启动的Activity
                class_name);
        Intent intent = new Intent();
        intent.setComponent(componetName);
        startActivity(intent);
    }

    //TODO 检查权限
    private void checkPermission() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(580);
                    if (null != mPermissionsUtils) {
                        mPermissionsUtils.checkRuntimePermissions(getRuntimePermissions());
                    }
                } catch (Exception e) {
                    Logger.w(TAG, "checkPermission() " + e.getMessage());
                }

            }
        });
    }

    //TODO 是否已经授权运行时权限
    public boolean authorizeRuntimePermission() {
        if (null != mPermissionsUtils) {
            return mPermissionsUtils.authorizeRuntimePermissions(getRuntimePermissions());
        }
        return false;
    }

    //TODO 返回当前界面需要申请的权限
    protected String[] getRuntimePermissions() {
        return null;
    }

    //TODO 权限申请成功的回调
    protected void onSuccess() {
    }

    @Override
    public void onRequestPermissionsSuccess() {
        onSuccess();
    }

    @Override
    protected void onRestart() {
        activity = this;
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        AppManager.removeActivity(this);
        super.onDestroy();
    }
}
