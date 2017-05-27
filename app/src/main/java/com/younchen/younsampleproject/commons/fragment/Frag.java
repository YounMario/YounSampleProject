package com.younchen.younsampleproject.commons.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class Frag implements Parcelable {
    private final String name;
    private final String simpleName;
    private final boolean parent;
    private final List<Frag> children;

    public Frag(String name) {
        this(name, false);
    }

    Frag(String name, boolean parent) {
        this.name = name;
        this.parent = parent;
        simpleName = name == null || !name.contains(".") ? name : name.substring(name.lastIndexOf('.') + 1);
        children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    boolean isParent() {
        return parent;
    }

    int len() {
        return isParent() ? children.size() : -1;
    }

    List<Frag> getChildren() {
        return children;
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }

    public boolean add(Frag c) {
        return c != null && !children.contains(c) && children.add(c);
    }

    @Override
    public String toString() {
        return "Cls{" +
                "name='" + name + '\'' +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.simpleName);
        dest.writeByte(this.parent ? (byte) 1 : (byte) 0);
        dest.writeList(this.children);
    }

    private Frag(Parcel in) {
        this.name = in.readString();
        this.simpleName = in.readString();
        this.parent = in.readByte() != 0;
        this.children = new ArrayList<>();
        in.readList(this.children, Frag.class.getClassLoader());
    }

    public static final Parcelable.Creator<Frag> CREATOR = new Parcelable.Creator<Frag>() {
        @Override
        public Frag createFromParcel(Parcel source) {
            return new Frag(source);
        }

        @Override
        public Frag[] newArray(int size) {
            return new Frag[size];
        }
    };
}
