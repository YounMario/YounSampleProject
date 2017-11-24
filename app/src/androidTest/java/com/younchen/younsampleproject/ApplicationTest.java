package com.younchen.younsampleproject;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

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

    @Test
    public void testMatchXaid() {
        String stringArray = "{0,1,2,3,4,5,6,7}";
        Assert.assertEquals(true, xaidIn(stringArray));

        String homeArray = "SAMSUNG;LEG;GOogLe";
        Assert.assertEquals(true, homeBrandMatched(homeArray));

        String s= Settings.System.getString(mContext
                        .getContentResolver(),
                Settings.System.ANDROID_ID);
        Assert.assertNotNull(s);
    }

    private String getAndroidID() {
        return Settings.System.getString(mContext
                        .getContentResolver(),
                Settings.System.ANDROID_ID);
    }

    @Test
    public void testGetDimen() {
        float dimens = mContext.getResources().getDimension(R.dimen.activity_vertical_margin);
        Assert.assertNotNull(dimens);
    }

    private boolean xaidIn(String guideOn) {
        String androidId = getAndroidID();
        if (TextUtils.isEmpty(guideOn) || TextUtils.isEmpty(androidId) || androidId.length() < 10) {
            return false;
        }
        char charToAbTest = androidId.charAt(9);
        return guideOn.indexOf(charToAbTest) != -1;
    }

    private static boolean homeBrandMatched(String homeBrand) {
        if (TextUtils.isEmpty(homeBrand)) {
            return false;
        }
        String[] brands = homeBrand.split(";");
        if (brands.length <= 0) {
            return false;
        }
        String brand = Build.BRAND.toLowerCase();
        for (String b : brands) {
            if (TextUtils.isEmpty(b)) {
                continue;
            }
            if (brand.contains(b.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}