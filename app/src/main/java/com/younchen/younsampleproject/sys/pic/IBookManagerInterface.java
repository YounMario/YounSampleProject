package com.younchen.younsampleproject.sys.pic;

import android.os.IBinder;
import android.os.IInterface;

import com.younchen.younsampleproject.sys.pic.bean.Book;

/**
 * Created by 龙泉 on 2016/7/25.
 * 手动实现aidl 不用aidl 的文件
 */
public interface IBookManagerInterface extends IInterface {

    static final String DESCRIPTOR="com.younchen.younsampleproject.sys.pic.bean.manager";

    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION;

    static final int TRANSACTION_querybook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public void addBook(Book book);

    public Book querybook(int id);
}
