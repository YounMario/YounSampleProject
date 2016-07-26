package com.younchen.younsampleproject.sys.pic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 龙泉 on 2016/7/25.
 */
public class Person implements Parcelable {

    private String name;
    private int id;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Person(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
