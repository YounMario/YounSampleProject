package com.younchen.younsampleproject.sys.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/6/22.
 */

public class BlockedContactsProvider extends ContentProvider {

    private BlockContactDbHelper mBlockContactDbHelper;
    private static final int URI_TABLE = 1;
    private static final int URI_ID = 2;

    private static final UriMatcher mUriMather = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        mBlockContactDbHelper = BlockContactDbHelper.getInstance(getContext());
        if (mBlockContactDbHelper == null) {
            return false;
        }
        mUriMather.addURI(BlockedContactProviderHelper.getAuthorities(), BlockedContract.BlockedContacts.TABLE_NAME, URI_TABLE);
        mUriMather.addURI(BlockedContactProviderHelper.getAuthorities(), BlockedContract.BlockedContacts.TABLE_NAME + "/#", URI_ID);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mBlockContactDbHelper.getReadableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(BlockedContract.BlockedContacts.TABLE_NAME);
        int match = mUriMather.match(uri);
        switch (match) {
            case URI_TABLE:
                break;
            case URI_ID:
                selection = getSelectionWithId(selection, ContentUris.parseId(uri));
                break;
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null) {
            if (getContext() != null) {
                cursor.setNotificationUri(getContext().getContentResolver(),
                        BlockedContract.BlockedContacts.BlockedContactUri);
            }
        }
        return cursor;
    }

    private String getSelectionWithId(String selection, long parseId) {
        if (TextUtils.isEmpty(selection)) {
            return BlockedContract.BlockedContactColumns.CONTACT_ID + "=" + parseId;
        } else {
            return selection + "AND " + BlockedContract.BlockedContactColumns.CONTACT_ID + "=" + parseId;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return BlockedContract.BlockedContacts.CONTENT_TYPE;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mBlockContactDbHelper.getWritableDatabase();
        long effectedId = db.insert(BlockedContract.BlockedContacts.TABLE_NAME, null, setDefaultValues(values));
        if (effectedId < 0) {
            return null;
        }
        notifyChange(uri);
        return ContentUris.withAppendedId(uri, effectedId);
    }

    private ContentValues setDefaultValues(ContentValues values) {
        if (values == null) {
            return values;
        }
        values.put(BlockedContract.BlockedContactColumns.CREATED_TIME, System.currentTimeMillis());
        return values;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mBlockContactDbHelper.getWritableDatabase();
        int match = mUriMather.match(uri);
        switch (match) {
            case URI_TABLE:
                break;
            case URI_ID:
                selection = getSelectionWithId(selection, ContentUris.parseId(uri));
                break;
        }
        long effectedId = db.delete(BlockedContract.BlockedContacts.TABLE_NAME, selection, selectionArgs);
        if (effectedId < 0) {
            return 0;
        }
        notifyChange(uri);
        return (int) effectedId;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mBlockContactDbHelper.getWritableDatabase();
        int match = mUriMather.match(uri);
        switch (match) {
            case URI_TABLE:
                break;
            case URI_ID:
                selection = getSelectionWithId(selection, ContentUris.parseId(uri));
                break;
        }
        int rowEffect = db.update(BlockedContract.BlockedContacts.TABLE_NAME, values, selection, selectionArgs);
        if (rowEffect > 0) {
            notifyChange(uri);
        }
        return rowEffect;
    }

    private void notifyChange(Uri uri) {
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}
