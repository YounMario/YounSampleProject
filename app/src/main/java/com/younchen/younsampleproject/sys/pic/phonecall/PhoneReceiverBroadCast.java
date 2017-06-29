package com.younchen.younsampleproject.sys.pic.phonecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.commons.utils.PhoneUtils;

/**
 * Created by Administrator on 2017/6/26.
 */

public class PhoneReceiverBroadCast extends BroadcastReceiver {

    private static final String TAG = "PhoneReceiverBroadCast";
    private final Context mContext;
    private String mBlockedPhone = "18500065429";
    private final String PHONE_COUNTRY_ISO = "CN";

    public PhoneReceiverBroadCast(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(
                "android.intent.action.PHONE_STATE")) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String inputNumber = intent.getExtras().getString("incoming_number");

            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                //stop the player or mute the audio here
                YLog.i(TAG, "ringing");
                abortBroadcast();
//                String phone = PhoneUtils.formatE164Number(mBlockedPhone, PHONE_COUNTRY_ISO);
//                if (phone.equals(inputNumber)) {
//                    PhoneUtils.endCall(mContext);
//                }
            }
            if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                //start the player or unmute the audio here
                YLog.i(TAG, "idle");
            }
            if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                //stop the player or mute the audio here
                YLog.i(TAG, "phone hook");
            }
        }
    }

    public void setBlockedPhone(String telPhone) {
        mBlockedPhone = PhoneUtils.formatE164Number(telPhone, PHONE_COUNTRY_ISO);
    }

}
