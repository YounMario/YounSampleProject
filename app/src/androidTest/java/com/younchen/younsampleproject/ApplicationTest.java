package com.younchen.younsampleproject;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest{

    private Context mContext;

    @Before
    public void init(){
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testNumber() {
        // Look for a contact that has the given phone number.
        String[] projection = {ContactsContract.PhoneLookup.LOOKUP_KEY, ContactsContract.PhoneLookup.NUMBER};

        int numberIndex = 1;
        int lookupKeyIndex = 0;
        String number = "18610777430";
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number.trim()));
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String lookUpKey = cursor.getString(lookupKeyIndex);
            String lookUpNumber = cursor.getString(numberIndex);
            Assert.assertNotNull(lookUpKey);
            Assert.assertNotNull(lookUpNumber);
        }
    }

}