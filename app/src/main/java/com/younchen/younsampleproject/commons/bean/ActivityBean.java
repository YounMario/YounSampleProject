package com.younchen.younsampleproject.commons.bean;

import android.app.Activity;


/**
 * Created by 龙泉 on 2016/7/21.
 */
public class ActivityBean {

    private Class<Activity> activity;
    private String description;

    public ActivityBean(Class activityClass, String ui) {
        this.activity = activityClass;
        this.description = ui;
    }

    public Class<Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<Activity> activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
