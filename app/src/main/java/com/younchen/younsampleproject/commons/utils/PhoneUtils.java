package com.younchen.younsampleproject.commons.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.internal.telephony.ITelephony;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/6/23.
 */

public class PhoneUtils {

    public static String formatE164Number(String phNum, String countryCode) {
        if (TextUtils.isEmpty(phNum)) {
            return phNum;
        }
        String e164Number;
        try {
            PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber phoneNumber = instance.parse(phNum, countryCode);
            e164Number = instance.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            e164Number = phNum;
        }
        return e164Number;
    }

    public static void endCall(Context context) {
        try{
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            Class clazz = Class.forName(telephonyManager.getClass().getName());
            Method method = clazz.getDeclaredMethod("getITelephony");
            method.setAccessible(true);
            ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
            telephonyService.endCall();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
