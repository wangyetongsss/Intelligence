package com.example.administrator.intelligence.bean;

/**
 * Created by 2018/5/21 09:31
 * 创建：Administrator on
 * 描述:eventbus定义类
 */
public class ViewEvent {
    private final int Event;
    private String Message;
    private int What;

    public ViewEvent(int event) {
        Event = event;
    }

    public int getEvent() {
        return Event;
    }

    public String getMessage() {
        return Message;
    }

    public ViewEvent setMessage(String message) {
        Message = message;
        return this;
    }

    public int getWhat() {
        return What;
    }

    public ViewEvent setWhat(int what) {
        What = what;
        return this;
    }
}
