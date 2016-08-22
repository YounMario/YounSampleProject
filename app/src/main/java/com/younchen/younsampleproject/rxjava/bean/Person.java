package com.younchen.younsampleproject.rxjava.bean;

/**
 * Created by 龙泉 on 2016/8/22.
 */
public class Person {

    public Person(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    private String name;
    private int sex;
}
