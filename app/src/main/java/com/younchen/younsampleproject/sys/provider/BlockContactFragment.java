package com.younchen.younsampleproject.sys.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.material.bean.Contact;


/**
 * Created by Administrator on 2017/6/22.
 */

public class BlockContactFragment extends BaseFragment {

    Button mSaveBtn;
    EditText mInputBar;

    private BlockContactDbHelper mBlockContactDbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_block_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBlockContactDbHelper = BlockContactDbHelper.getInstance(getActivity());
        mSaveBtn = (Button) view.findViewById(R.id.btn_save);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = mInputBar.getText().toString();
                ContentValues values = new ContentValues();
                values.put(BlockedContract.BlockedContactColumns.PHONE_NUMBER, phoneNum);
                values.put(BlockedContract.BlockedContactColumns.CONTACT_LOOK_UP_KEY, "lookUpKey");
                values.put(BlockedContract.BlockedContactColumns.CONTACT_ID, 23);
                values.put(BlockedContract.BlockedContactColumns.COUNTRY_ISO, "CN");
                values.put(BlockedContract.BlockedContactColumns.PHONE_NUMBER, phoneNum);
                Uri uri = getActivity().getContentResolver().insert(BlockedContract.BlockedContacts.BlockedContactUri, values);
                long rowId = ContentUris.parseId(uri);
                if (rowId > 0) {
                    Snackbar.make(mRootView, "save ok", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mInputBar = (EditText) view.findViewById(R.id.edit_txt_phone_number);
    }

    @Override
    public void onBackKeyPressed() {

    }
}
