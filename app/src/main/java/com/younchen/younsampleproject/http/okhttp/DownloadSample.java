package com.younchen.younsampleproject.http.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.http.okhttp.download.DownLoadView;
import com.younchen.younsampleproject.http.okhttp.download.DownloadPresenter;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/9/20.
 */

public class DownloadSample extends BaseFragment implements DownLoadView {


    @BindView(R.id.btn_start)
    Button mStartBtn;
    @BindView(R.id.btn_cancel)
    Button mCancelBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private DownloadPresenter mDownloadPresenter;

    private String mDownloadUrl = "http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_5mb.mp4";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.download_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mDownloadPresenter = new DownloadPresenter(getActivity(), this);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadPresenter.startDownload(mDownloadUrl);
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadPresenter.cancelDownload();
            }
        });
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void progress(int progress) {

    }

    @Override
    public void downloadStart() {
        mStartBtn.setText("Pause");
    }

    @Override
    public void downloadPause() {
        mStartBtn.setText("Resume");
    }

    @Override
    public void downloadFinish() {
        mStartBtn.setText("Start");
    }

    @Override
    public void downloadResume() {
        mStartBtn.setText("Pause");
    }

    @Override
    public void downloadCanceled() {
        mStartBtn.setText("Start");
    }
}
