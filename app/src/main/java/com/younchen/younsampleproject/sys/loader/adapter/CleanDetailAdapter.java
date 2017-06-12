package com.younchen.younsampleproject.sys.loader.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.LruCache;
import android.util.SparseArray;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.sys.loader.bean.ContactItem;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/6/12.
 */

public class CleanDetailAdapter extends BaseAdapter<ContactItem> {

    private ActionListener mCheckChangeListener;
    private SparseArray<ContactItem> mSelectedItems;
    private Context mContext;

    private final int cacheSize = 1024 * 1024 * 2;

    private final Object mLock = new Object();
    private LruCache<Integer, Bitmap> mBitmapCache = new LruCache<>(cacheSize);

    private static final String[] PHOTO_BITMAP_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Photo.PHOTO
    };

    public CleanDetailAdapter(Context context) {
        super(context, R.layout.item_contact_style2);
        this.mSelectedItems = new SparseArray<>();
        this.mContext = context;
    }

    @Override
    public void covert(ViewHolder holder, final ContactItem item, final int position) {
        holder.setText(R.id.txt_title, item.name);
        CheckBox checkBox = (CheckBox) holder.getView(R.id.select_box);
        //todo check 乱序
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                synchronized (mLock) {
                    if (isChecked && mSelectedItems.get(position) == null) {
                        mSelectedItems.put(position, item);
                    } else if (!isChecked && mSelectedItems.get(position) != null) {
                        mSelectedItems.remove(position);
                    }
                }
                if (mCheckChangeListener != null) {
                    mCheckChangeListener.onCheckChanged(position, isChecked);
                }
            }
        });
        bindImage((ImageView) holder.getView(R.id.user_image), item);
    }

    private void bindImage(ImageView imageView, ContactItem item) {
        Bitmap photo;
        if (mBitmapCache.get(item.photoId) != null) {
            photo = mBitmapCache.get(item.photoId);
        } else {
            photo = fetchThumbnail(item.photoId);
            if (photo != null) {
                mBitmapCache.put(item.photoId, photo);
            }
        }
        if (photo == null) {
            imageView.setImageResource(R.color.gray);
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


    public int getSelectedItemCount() {
        synchronized (mLock) {
            return mSelectedItems.size();
        }
    }

    public void cleanSelectedContact() {
        synchronized (mLock) {
            //todo delete contact here
            if (mSelectedItems.size() == 0) {
                return;
            }
            ArrayList<ContactItem> itemsToRemove = new ArrayList<>();
            StringBuilder keys = new StringBuilder();
            keys.append("(");
            for (int i = 0; i < mSelectedItems.size(); i++) {
                ContactItem item = mSelectedItems.get(mSelectedItems.keyAt(i));
                itemsToRemove.add(item);
                keys.append('\'').append(item.lookUpKey).append('\'').append(",");
            }
            keys.deleteCharAt(keys.length() - 1).append(")");


            ContentResolver contentResolver = mContext.getContentResolver();
            int deletedCount = 0;
            for (ContactItem item : itemsToRemove) {
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, item.lookUpKey);
                int result = contentResolver.delete(uri, null, null);
                if (result > 0) {
                    deletedCount++;
                }
            }
            //todo 批量删除not working
            //int rowCount = mContext.getContentResolver().delete(ContactsContract.Contacts.CONTENT_URI, getWhereString(keys.toString()), null);
            if (deletedCount > 0) {
                mSelectedItems.clear();
                remove(itemsToRemove);
            }
            mCheckChangeListener.onClearFinished();
        }
    }


    private String getWhereString(String keys) {
        return ContactsContract.Contacts.LOOKUP_KEY + " in " + keys;
    }

    public void setActionListener(ActionListener checkChangeListener) {
        this.mCheckChangeListener = checkChangeListener;
    }


    public interface ActionListener {

        void onCheckChanged(int position, boolean isChecked);

        void onClearFinished();
    }
}
