package com.younchen.younsampleproject.sys.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.commons.utils.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/6/22.
 */

public class BlockContactDbHelper extends SQLiteOpenHelper {


    private static BlockContactDbHelper mBlockContactDbHelper;
    private Context mContext;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "launcher_dialer.db";
    private static final String DB_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + DATABASE_NAME;


    public static BlockContactDbHelper getInstance(Context context) {
        if (mBlockContactDbHelper == null) {
            mBlockContactDbHelper = new BlockContactDbHelper(context.getApplicationContext(),
                    DATABASE_NAME);
        }
        return mBlockContactDbHelper;
    }

    private BlockContactDbHelper(Context context, String databaseName) {
        this(context, databaseName, DATABASE_VERSION);
    }

    private BlockContactDbHelper(Context context, String databaseName, int dbVersion) {
        super(context, databaseName, null, dbVersion);
        YLog.i("BlockContactDbHelper", "creating instance");
        mContext = context;
    }

    private void createBlockContactsTable(SQLiteDatabase db) {
        YLog.i("BlockContactDbHelper", "creating table");
        db.execSQL("CREATE TABLE " + BlockedContract.BlockedContacts.TABLE_NAME + " ("
                + BlockedContract.BlockedContactColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BlockedContract.BlockedContactColumns.COUNTRY_ISO + " INTEGER,"
                + BlockedContract.BlockedContactColumns.CREATED_TIME + " TEXT,"
                + BlockedContract.BlockedContactColumns.PHONE_NUMBER + " TEXT,"
                + BlockedContract.BlockedContactColumns.CONTACT_ID + " INTEGER,"
                + BlockedContract.BlockedContactColumns.CONTACT_LOOK_UP_KEY + " TEXT"
                + ");");
    }

    private void dropBlockContactsTableIfExists(SQLiteDatabase db) {
        YLog.i("BlockContactDbHelper", "drop table");
        db.execSQL("DROP TABLE IF EXISTS " + BlockedContract.BlockedContacts.TABLE_NAME);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        dropBlockContactsTableIfExists(db);
        createBlockContactsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void copyDbFile() {
        String dbSourcePath = covertDatabaseFilePath(mContext, DATABASE_NAME);
        if (!FileUtils.isExist(DB_FILE_PATH)) {
            FileUtils.copyFile(dbSourcePath, DB_FILE_PATH);
        }
    }


    protected String covertDatabaseFilePath(Context context, String path) {
        // 把相对路径转为绝对路径，以防4.2及其以上系统因无权限发生崩溃。
        if (null != context && !TextUtils.isEmpty(path) && Build.VERSION.SDK_INT >= 17) {
            if (!path.startsWith(File.separator)) {
                if (context.getFilesDir() != null) {
                    path = FileUtils.addSlash(
                            context.getFilesDir().getAbsolutePath().replace("files", "databases")
                    ) + path;
                }
            }
        }
        return path;
    }

}
