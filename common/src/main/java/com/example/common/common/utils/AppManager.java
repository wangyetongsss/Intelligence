package com.example.common.common.utils;


import com.example.common.common.base.BaseActivity;

import java.util.LinkedList;

/**
 * Activity管理工具类
 *
 * @author wangxiaoer
 */
public class AppManager {
    private static LinkedList<BaseActivity> list = new LinkedList<>();

    public static void addActiviy(BaseActivity a) {
        if (!list.contains(a)) {
            list.add(a);
        }
    }


    public static BaseActivity getLastActivity() {
        return list.getLast();
    }

    public static void removeActivity(BaseActivity a) {
        if (!list.isEmpty()) {
            list.remove(a);
        }
    }

    public static LinkedList<BaseActivity> getActivityList() {
        return list;
    }

    /**
     * 退出，结束程序的所有界面
     */
    public static void tuichu() {
        int lenth = list.size();
        for (int i = 0; i < lenth; i++) {
            try {
                list.get(i).finish();
            } catch (Exception e) {
            }
        }
        if (list.size() > 0) {
            list.clear();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 退出登录，留下一个登录界面
     */
    public static void existLogin() {
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                list.get(i).finish();
            }
        }
    }


}
