package com.younchen.younsampleproject.sys.loader.contact;

import android.content.Loader;

import com.younchen.younsampleproject.sys.loader.contact.adapter.BlockContactAdapter;

/**
 * Created by Administrator on 2017/6/22.
 */

public class BlockContactFragment extends CursorFragment<BlockContactAdapter>{

    private BlockContactAdapter mBlockContactAdapter;

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected BlockContactAdapter createAdapter() {
        mBlockContactAdapter = new BlockContactAdapter(getActivity());
        return mBlockContactAdapter;
    }

}
