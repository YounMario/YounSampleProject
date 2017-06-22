package com.younchen.younsampleproject.sys.loader.contact.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.younchen.younsampleproject.R;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CallLogAdapter extends CursorAdapter {

    static final String[] CALL_LOG = new String[]{
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.TYPE,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE
    };


    public CallLogAdapter(Context context) {
        super(context, null, false);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_contact_style, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(CALL_LOG[0]));
        TextView nameTxtView = (TextView) view.findViewById(R.id.txt_contact_name);
        nameTxtView.setText(name);
    }
}
