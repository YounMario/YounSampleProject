package com.younchen.younsampleproject.sys.loader.contact.adapter;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;

/**
 * Created by Administrator on 2017/6/22.
 */

public class BlockContactAdapter extends CustomCursorAdapter{


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
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_contact_style, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
