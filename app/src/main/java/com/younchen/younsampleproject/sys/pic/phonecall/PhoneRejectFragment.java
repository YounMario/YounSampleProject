package com.younchen.younsampleproject.sys.pic.phonecall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.commons.utils.PhoneUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/23.
 */

public class PhoneRejectFragment extends BaseFragment {

    @BindView(R.id.btn_save)
    Button mBtnDel;

    @BindView(R.id.edit_txt_phone_number)
    EditText mEditText;

    private String mBlockedPhone = "18500065429";

    private final String PHONE_COUNTRY_ISO = "CN";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_block_contact, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telPhone = mEditText.getText().toString();
                mBlockedPhone = PhoneUtils.formatE164Number(telPhone, PHONE_COUNTRY_ISO);
            }
        });

    }

    private void initData() {
        MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener();
        TelephonyManager mTelephoneManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        mTelephoneManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        mEditText.setText(mBlockedPhone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackKeyPressed() {

    }

    private class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    String phoneNum = PhoneUtils.formatE164Number(incomingNumber, PHONE_COUNTRY_ISO);
                    if (phoneNum.equals(mBlockedPhone)) {
                        YLog.i("YounSamplePhone", "call end");
                        PhoneUtils.endCall(getActivity());
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                default:
                    break;
            }
        }
    }
}
