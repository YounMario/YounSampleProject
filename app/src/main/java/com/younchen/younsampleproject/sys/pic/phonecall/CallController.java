package com.younchen.younsampleproject.sys.pic.phonecall;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.younchen.younsampleproject.commons.log.YLog;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/6/26.
 */

class CallController {

    private static final String TAG = "CallController";

    private Context mContext;
    private MyCallHandler mHandler;

    CallController(Context context) {
        mContext = context;
        MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener();
        TelephonyManager mTelephoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        mTelephoneManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        mHandler = new MyCallHandler(this);
    }

    private static class MyCallHandler extends Handler {

        private WeakReference<CallController> mContextRef;

        MyCallHandler(CallController callController) {
            mContextRef = new WeakReference<>(callController);
        }

        @Override
        public void handleMessage(Message msg) {
            String incomingNumber = (String) msg.obj;
            switch (msg.what) {
                case TelephonyManager.CALL_STATE_RINGING:
                    YLog.i(TAG, "call ringing");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    YLog.i(TAG, "call idle");
                    break;
            }
        }
    }


    private class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Message message = mHandler.obtainMessage();
            message.what = state;
            message.obj = incomingNumber;
            mHandler.sendMessage(message);
        }
    }
}
