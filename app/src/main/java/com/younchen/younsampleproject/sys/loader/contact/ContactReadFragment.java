package com.younchen.younsampleproject.sys.loader.contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.log.YLog;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/8/3.
 */


public class ContactReadFragment extends BaseFragment{

    private static final String TAG = "ContactReadFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_common_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        readContact();
    }

    private void readContact() {
        String id;
        String mimetype;
        ContentResolver contentResolver = getActivity().getContentResolver();
        //只需要从Contacts中获取ID，其他的都可以不要，通过查看上面编译后的SQL语句，可以看出将第二个参数
        //设置成null，默认返回的列非常多，是一种资源浪费。
        Cursor cursor = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI,
                new String[]{android.provider.ContactsContract.Contacts._ID}, null, null, null);
        while(cursor.moveToNext()) {
            id=cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));

            //从一个Cursor获取所有的信息
            Cursor contactInfoCursor = contentResolver.query(
                    android.provider.ContactsContract.Data.CONTENT_URI,
                    new String[]{android.provider.ContactsContract.Data.CONTACT_ID,
                            android.provider.ContactsContract.Data.MIMETYPE,
                            android.provider.ContactsContract.Data.DATA1
                    },
                    android.provider.ContactsContract.Data.CONTACT_ID+"="+id, null, null);
            while(contactInfoCursor.moveToNext()) {
                mimetype = contactInfoCursor.getString(
                        contactInfoCursor.getColumnIndex(android.provider.ContactsContract.Data.MIMETYPE));
                String value = contactInfoCursor.getString(
                        contactInfoCursor.getColumnIndex(android.provider.ContactsContract.Data.DATA1));
                if(mimetype.contains("/name")){
                    YLog.i(TAG,"姓名="+value);
                } else if(mimetype.contains("/im")){
                    YLog.i(TAG,"聊天(QQ)账号="+value);
                } else if(mimetype.contains("/email")) {
                    YLog.i(TAG,"邮箱="+value);
                } else if(mimetype.contains("/phone")) {
                    YLog.i(TAG,"电话="+value);
                } else if(mimetype.contains("/postal")) {
                    YLog.i(TAG,"邮编="+value);
                } else if(mimetype.contains("/photo")) {
                    YLog.i(TAG,"照片="+value);
                } else if(mimetype.contains("/group")) {
                    YLog.i(TAG,"组="+value);
                }
            }
            YLog.i(TAG,"*********");
            contactInfoCursor.close();
        }
        cursor.close();
    }
    

    @Override
    public void onBackKeyPressed() {

    }
}
