package com.younchen.younsampleproject.sys.loader.contact.adapter;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Created by Administrator on 2017/6/22.
 */

public abstract class CustomCursorAdapter extends CursorAdapter {


    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public abstract void configureLoader(Loader<Cursor> loader);
}
