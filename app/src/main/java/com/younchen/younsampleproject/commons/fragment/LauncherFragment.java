package com.younchen.younsampleproject.commons.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.activity.BaseActivity;
import com.younchen.younsampleproject.commons.adapter.FragAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class LauncherFragment extends BaseFragment {

    private static final String KEY_FRAG_ITEM = "KEY_FRAG";
    private static String KEY_PARENT_FRAG_ITEM = "KEY_PARENT_FRAG";

    private RecyclerView mRecycleView;
    private Frag mFrag;
    private Frag mParentFrag;

    public static LauncherFragment newInstance(BaseActivity mActivity, Frag frag) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_FRAG_ITEM, frag);
        return (LauncherFragment) Fragment.instantiate(mActivity, LauncherFragment.class.getName(), bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_launcher_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        init(view);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFrag = bundle.getParcelable(KEY_FRAG_ITEM);
            mParentFrag = bundle.getParcelable(KEY_PARENT_FRAG_ITEM);
        }
    }

    private void init(View view) {
        mRecycleView = (RecyclerView) view.findViewById(R.id.function_list_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FragAdapter fragAdapter = new FragAdapter(getActivity());
        fragAdapter.setOnItemClickListener(new FragAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Frag item) {
                BaseFragment baseFragment;
                if (item.isParent()) {
                    baseFragment = LauncherFragment.newInstance((BaseActivity) getActivity(), item);
                } else {
                    baseFragment = (BaseFragment) Fragment.instantiate(getActivity(), item.getName());
                }
                baseFragment.show((BaseActivity) getActivity());
            }
        });
        List<Frag> fragList = getFragList();
        if (fragList != null) {
            fragAdapter.setData(fragList);
            mRecycleView.setAdapter(fragAdapter);
        }
    }

    private List<Frag> getFragList() {
        if (mFrag == null) {
            try {
                return FragmentInflater.inflate(getActivity().getPackageName(), new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        try {
                            if (isAbstractClass(filename) || LauncherFragment.class.getName().equals(filename)) {
                                return false;
                            }
                            return BaseFragment.class.isAssignableFrom(Class.forName(filename));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return mFrag.getChildren();
        }
        return Collections.EMPTY_LIST;
    }

    private boolean isAbstractClass(String filename) {
        try {
            Class<?> clazz = Class.forName(filename);
            return Modifier.isAbstract(clazz.getModifiers());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public void onBackKeyPressed() {
    }
}
