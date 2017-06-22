package com.younchen.younsampleproject.sys.loader.contact.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */
public class CleanContactItem implements Serializable{
    public String title;
    public int count;
    private ArrayList<ContactItem> mList;

    public static final int CLEAN_TYPE_NO_PHONE = 1;
    public static final int CLEAN_TYPE_NO_NAME = 2;
    public static final int CLEAN_TYPE_BLOCKED = 3;
    public static final int CLEAN_TYPE_ALL_CONTACT = 4;

    public int type;

    public CleanContactItem() {
        mList = new ArrayList<>();
    }

    public void addContact(ContactItem contact) {
        mList.add(contact);
    }

    public List<ContactItem> getContacts() {
        return mList;
    }
}
