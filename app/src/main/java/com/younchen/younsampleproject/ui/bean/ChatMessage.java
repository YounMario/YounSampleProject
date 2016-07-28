package com.younchen.younsampleproject.ui.bean;

/**
 * Created by 龙泉 on 2016/7/27.
 */
public class ChatMessage {

    public static final int TYPE_IMG = 1;
    public static final int TYPE_TEXT = 2;

    private int type;
    private String msg;

    public ChatMessage(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
