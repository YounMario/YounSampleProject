package com.younchen.younsampleproject.http.okhttp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.utils.PermissionsUtil;
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
    @BindView(R.id.txt_message)
    TextView mMessageText;
    @BindView(R.id.txt_progress)
    TextView mTxtProgress;


    private DownloadPresenter mDownloadPresenter;

    private String mDownloadUrl = "http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_5mb.mp4";
    private boolean isInit;
    private int PERMISSION_REQUEST_CODE = 200;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.download_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!PermissionsUtil.hasPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !PermissionsUtil.hasPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            if (!isInit) {
                initView();
                isInit = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    throw new RuntimeException("need permission:" + permissions[i]);
                }
            }
            initView();
        }
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
        progressBar.setProgress(progress);
        mTxtProgress.setText(progress + "%");
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

    @Override
    public void downloadFail(Throwable throwable) {
        mMessageText.setText("download failed:" + throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDownloadPresenter.cancelAll();
    }
}
