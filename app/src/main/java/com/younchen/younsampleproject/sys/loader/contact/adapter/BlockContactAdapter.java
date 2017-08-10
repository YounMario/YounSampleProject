package com.younchen.younsampleproject.sys.loader.contact.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.sys.provider.BlockedContract;

/**
 * Created by Administrator on 2017/6/22.
 */

public class BlockContactAdapter extends CustomCursorAdapter {


    public BlockContactAdapter(Context context) {
        super(context, null);
    }

    public BlockContactAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public BlockContactAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void configureLoader(Loader<Cursor> loader) {
        CursorLoader cursorLoader = (CursorLoader) loader;
        cursorLoader.setUri(BlockedContract.BlockedContacts.BlockedContactUri);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_contact_style, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.txt_contact_name);
        textView.setText(cursor.getString(cursor.getColumnIndex(BlockedContract.BlockedContactColumns.PHONE_NUMBER)));
    }
}
