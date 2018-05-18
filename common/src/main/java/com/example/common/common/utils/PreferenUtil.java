package com.example.common.common.utils;/**
 * Created by Administrator on 2017/4/28.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * 2017
 * 04
 * 2017/4/28
 * wangxiaoer
 * 功能描述：存储数据，包括用户设置数据存储
 **/
public class PreferenUtil {
    private Context context;

    public PreferenUtil(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    /**
     * 存储设置中跑前秒数设置
     *
     * @param time
     * @return flag
     */
    public boolean saveDjsMsg(String time) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("time_djs", time);
        flag = editor.commit();
        return flag;
    }

    /**
     * 获取设置中的跑前秒数设置
     *
     * @return 我保存的秒数
     */
    public String getDjsMsg() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        String myDjsTime = sharedPreferences.getString("time_djs", "5");
        return myDjsTime;

    }

    /**
     * 清除设置
     */
    public void clearDjsData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
    }

    /**
     * 存储登录信息
     *
     * @param isLogin
     * @param id
     * @param tel
     * @param pwd
     * @param headurl
     * @param licheng
     * @return flag
     */
    public boolean saveLoginMessage(String isLogin, String id, String nickname, String usernumber, String tel,
                                    String pwd, String headurl, String licheng, String points, String gender, String weight, String address) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "myinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("isLogin", isLogin);
        editor.putString("id", id);
        editor.putString("nickname", nickname);
        editor.putString("usernumber", usernumber);
        editor.putString("tel", tel);
        editor.putString("pwd", pwd);
        editor.putString("headurl", headurl);
        editor.putString("licheng", licheng);
        editor.putString("points", points);
        editor.putString("gender", gender);
        editor.putString("weight", weight);
        editor.putString("address", address);
        flag = editor.commit();
        return flag;
    }

    /**
     * 获取登录信息
     *
     * @return
     */
    public Map<String, Object> getLoginMessage() {
        Map<String, Object> map = new HashMap<String, Object>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "myinfo", Context.MODE_PRIVATE);
        String isLogin = sharedPreferences.getString("isLogin", "");
        String id = sharedPreferences.getString("id", "");
        String nickname = sharedPreferences.getString("nickname", "");
        String usernumber = sharedPreferences.getString("usernumber", "");
        String tel = sharedPreferences.getString("tel", "");
        String pwd = sharedPreferences.getString("pwd", "");
        String headurl = sharedPreferences.getString("headurl", "");
        String licheng = sharedPreferences.getString("licheng", "");
        String points = sharedPreferences.getString("points", "0");
        String gender = sharedPreferences.getString("gender", "");
        String weight = sharedPreferences.getString("weight", "0");
        String address = sharedPreferences.getString("address", "");
        map.put("isLogin", isLogin);
        map.put("id", id);
        map.put("nickname", nickname);
        map.put("usernumber", usernumber);
        map.put("tel", tel);
        map.put("pwd", pwd);
        map.put("headurl", headurl);
        map.put("licheng", licheng);
        map.put("points", points);
        map.put("gender", gender);
        map.put("weight", weight);
        map.put("address", address);
        return map;
    }

    /**
     * 保存头像
     *
     * @param
     * @return
     */
    public boolean saveHeadPicUrl(String headpicurl) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "myinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("headurl", headpicurl);
        flag = editor.commit();
        return flag;
    }


    /**
     * 保存新的手机号
     *
     * @param
     * @return
     */
    public boolean saveNewTel(String newtel) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "myinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tel", newtel);
        flag = editor.commit();
        return flag;

    }

    /**
     * 清楚登录信息
     */
    public void clearDate() {
        // TODO Auto-generated method stub
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "myinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
    }

    /**
     * 保存地图模式，0，街道地图，1，卫星地图
     *
     * @param state
     * @return
     */
    public boolean SaveMaplayer(int state) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("state", state);
        flag = editor.commit();
        return flag;

    }

    public int getMaplayer() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        int state = sharedPreferences.getInt("state", 0);
        return state;
    }

    /**
     * 保存锁屏显示消息checkbox
     *
     * @param
     * @return
     */
    public boolean saveLockScreenMsgIscheck(boolean LockScreenMsgischecked) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LockScreenMsgischecked", LockScreenMsgischecked);
        flag = editor.commit();
        return flag;

    }

    /**
     * 获取锁屏显示消息设置
     *
     * @return
     */
    public boolean getLockScreenMsgIschecked() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        boolean isChecked = sharedPreferences.getBoolean("LockScreenMsgischecked", false);
        return isChecked;
    }


}
