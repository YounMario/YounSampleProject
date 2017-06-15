package com.younchen.younsampleproject.sys.loader.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.sys.loader.bean.ContactItem;

/**
 * Created by Administrator on 2017/6/15.
 */

public class ContactAdapter extends CursorAdapter {


    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.CONTACT_PRESENCE,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.STARRED
    };

    private Context mContext;

    private final int KEY_OF_TAG = 0x99999;

    private final int cacheSize = 1024 * 1024 * 2;

    private final Object mLock = new Object();
    private LruCache<Integer, Bitmap> mBitmapCache = new LruCache<>(cacheSize);

    private static final String[] PHOTO_BITMAP_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Photo.PHOTO
    };

    public ContactAdapter(Context context) {
        super(context, null, false);
        this.mContext = context;
    }


    private void bindImage(ImageView imageView, int photoId, int stared) {
        Bitmap photo;
        if (mBitmapCache.get(photoId) != null) {
            photo = mBitmapCache.get(photoId);
        } else {
            photo = fetchThumbnail(photoId);
            if (photo != null) {
                mBitmapCache.put(photoId, photo);
            }
        }
        if (photo == null) {
            if (stared == 1) {
                imageView.setImageResource(R.color.colorAccent);
            } else {
                imageView.setImageResource(R.color.gray);
            }
        } else {
            imageView.setImageBitmap(photo);
        }
    }

    private final Bitmap fetchThumbnail(final int thumbnailId) {
        final Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
        final Cursor cursor = mContext.getContentResolver().query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            Bitmap thumbnail = null;
            if (cursor.moveToFirst()) {
                final byte[] thumbnailBytes = cursor.getBlob(0);
                if (thumbnailBytes != null) {
                    thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);
                }
            }
            return thumbnail;
        } finally {
            cursor.close();
        }
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_contact_style,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.txt_contact_name);
        ImageView headImage = (ImageView) view.findViewById(R.id.img_head);
        name.setText(cursor.getString(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[1])));
        int id = cursor.getInt(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[4]));
        int started = cursor.getInt(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[6]));
        bindImage(headImage, id, started);
    }
}
