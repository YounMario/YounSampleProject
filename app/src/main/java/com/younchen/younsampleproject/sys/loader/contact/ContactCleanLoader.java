package com.younchen.younsampleproject.sys.loader.contact;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.younchen.younsampleproject.sys.loader.contact.bean.CleanContactItem;
import com.younchen.younsampleproject.sys.loader.contact.bean.ContactItem;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ContactCleanLoader extends AsyncTaskLoader<List<CleanContactItem>> {


    private CancellationSignal mCancellationSignal;

    private List<CleanContactItem> mData;

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.CONTACT_PRESENCE,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.STARRED
    };

    /* Runs on a worker thread */
    @Override
    public List<CleanContactItem> loadInBackground() {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledException();
            }
            mCancellationSignal = new CancellationSignal();
        }
        try {
            ArrayList<CleanContactItem> data = new ArrayList<>();
            //query no phone
            String noPhoneSelect = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND (" + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=0))";
            Cursor noPhoneCursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, noPhoneSelect,
                    null, null, mCancellationSignal);
            if (noPhoneCursor != null) {
                CleanContactItem noPhoneItem = parseCursor(noPhoneCursor);
                if (noPhoneItem != null) {
                    noPhoneItem.title = "No Phone";
                    noPhoneItem.type = CleanContactItem.CLEAN_TYPE_NO_PHONE;
                    data.add(noPhoneItem);
                    //noPhoneCursor.close();
                }
            }

            //query no name
            String noNameSelect = noNameSelectString();
            Cursor noNameCursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, noNameSelect, null, null, mCancellationSignal);
            if (noNameCursor != null) {
                noNameCursor = new MergeCursor(new Cursor[]{noNameCursor, noPhoneCursor});
                CleanContactItem noNameItem = parseCursor(noNameCursor);
                if (noNameItem != null) {
                    noNameItem.title = "No Name";
                    noNameItem.type = CleanContactItem.CLEAN_TYPE_NO_NAME;
                    data.add(noNameItem);
                    noPhoneCursor.close();
                    noNameCursor.close();
                }
            }

            //query all contact
            Cursor allContact = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, null, null, null, mCancellationSignal);
            if (allContact != null) {
                CleanContactItem noNameItem = parseCursor(allContact);
                if (noNameItem != null) {
                    noNameItem.title = "All Contact";
                    noNameItem.type = CleanContactItem.CLEAN_TYPE_ALL_CONTACT;
                    data.add(noNameItem);
                    allContact.close();
                }
            }
            return data;
        } finally {
            synchronized (this) {
                mCancellationSignal = null;
            }
        }
    }


    //todo 在电话簿里不保存姓名，电话号被当做名称.
    @NonNull
    private String noNameSelectString() {
        return "((" + ContactsContract.Contacts.DISPLAY_NAME + " ISNULL OR " + ContactsContract.Contacts.DISPLAY_NAME + "='') AND (" + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1))";
    }

    private CleanContactItem parseCursor(Cursor cursor) {
        CleanContactItem cleanContactItem = new CleanContactItem();
        cleanContactItem.count = cursor.getCount();
        while (cursor.moveToNext()) {
            ContactItem contact = new ContactItem();
            contact.id = cursor.getInt(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[0]));
            contact.name = cursor.getString(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[1]));
            contact.status = cursor.getInt(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[2]));
            contact.presence = cursor.getInt(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[3]));
            contact.photoId = cursor.getInt(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[4]));
            contact.lookUpKey = cursor.getString(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[5]));
            contact.stared = cursor.getInt(cursor.getColumnIndex(CONTACTS_SUMMARY_PROJECTION[6]));
            cleanContactItem.addContact(contact);
        }
        return cleanContactItem;
    }


    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();

        synchronized (this) {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }
    }

    /* Runs on the UI thread */
    public void deliverResult(List<CleanContactItem> data) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            return;
        }


        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    public ContactCleanLoader(Context context) {
        super(context);
    }

    /**
     * Starts an asynchronous load of the contacts list data. When the result is ready the callbacks
     * will be called on the UI thread. If a previous load has been completed and is still valid
     * the result may be passed to the callbacks immediately.
     * <p>
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }
        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }


    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();
        mData = null;
    }


    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mData=");
        writer.println(mData);
        writer.print(prefix);
    }
}
