package com.younchen.younsampleproject.sys.pic.phonecall;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/23.
 */

public class PhoneRejectFragment extends BaseFragment {

    @BindView(R.id.btn_save)
    Button mBtnDel;

    @BindView(R.id.edit_txt_phone_number)
    EditText mEditText;


    private PhoneReceiverBroadCast mPhoneReceiverBroadCast;


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
                mPhoneReceiverBroadCast.setBlockedPhone(telPhone);
            }
        });

    }

    private void initData() {
        mPhoneReceiverBroadCast = new PhoneReceiverBroadCast(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.setPriority(1000);
        getActivity().registerReceiver(mPhoneReceiverBroadCast, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackKeyPressed() {

    }
}
