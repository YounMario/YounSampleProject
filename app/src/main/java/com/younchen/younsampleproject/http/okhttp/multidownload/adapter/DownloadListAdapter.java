package com.younchen.younsampleproject.http.okhttp.multidownload.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;
import com.younchen.younsampleproject.http.okhttp.download.DownloadModel;

/**
 * Created by yinlongquan on 2017/10/12.
 */

public class DownloadListAdapter extends BaseAdapter<DownLoadInfo> {


    public DownloadListAdapter(Context context) {
        super(context, R.layout.item_mulit_download);
    }

    @Override
    public void covert(final ViewHolder holder, DownLoadInfo item, int position) {
        final DownLoadInfo downloadInfo = getItem(holder.getAdapterPosition());
        String fileName = downloadInfo.getUrl().substring(downloadInfo.getUrl().lastIndexOf('/'));
        final ProgressBar bar = (ProgressBar) holder.getView(R.id.progress_bar);
        holder.setText(R.id.txt_file_name, fileName);
        initProgress(holder, downloadInfo);


        Button startBtn = (Button) holder.getView(R.id.btn_start);
        Button endBtn = (Button) holder.getView(R.id.btn_end);

        if (DownloadModel.getInstance().isDownloaded(downloadInfo)) {
            updateState(holder, downloadInfo, DownLoadInfo.FINISHED);
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadInfo.getState() == DownLoadInfo.NORMAL || downloadInfo.getState() == DownLoadInfo.PAUSE) {
                    DownloadModel.getInstance().download(downloadInfo);
                    updateState(holder, downloadInfo, DownLoadInfo.DOWNLOADING);
                } else if (downloadInfo.getState() == DownLoadInfo.DOWNLOADING) {
                    DownloadModel.getInstance().pause(downloadInfo.getUrl());
                    updateState(holder, downloadInfo, DownLoadInfo.PAUSE);
                }
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadModel.getInstance().cancel(downloadInfo);
                updateState(holder, downloadInfo, DownLoadInfo.NORMAL);
                delete(downloadInfo);
            }
        });


        holder.getView(R.id.btn_cancel);
        downloadInfo.setCallback(new CallBack() {
            @Override
            public void onStart(long current, long total) {

            }

            @Override
            public void onProgress(long current, long total, int percent) {
                bar.setProgress(percent);
                holder.setText(R.id.txt_progress, percent + "%");
            }

            @Override
            public void onFinish() {
                updateState(holder, downloadInfo, DownLoadInfo.FINISHED);
            }

            @Override
            public void onFail(Throwable throwable) {

            }
        });
    }

    private void updateState(ViewHolder holder, DownLoadInfo info, int state) {
        Button startBtn = (Button) holder.getView(R.id.btn_start);
        ProgressBar progressBar = (ProgressBar) holder.getView(R.id.progress_bar);
        info.setState(state);
        switch (state) {
            case DownLoadInfo.NORMAL:
                startBtn.setText("start");
                break;
            case DownLoadInfo.PAUSE:
                startBtn.setText("resume");
                break;
            case DownLoadInfo.DOWNLOADING:
                startBtn.setText("pause");
                break;
            case DownLoadInfo.FINISHED:
                progressBar.setProgress(100);
                holder.setText(R.id.txt_progress, "100%");
                startBtn.setVisibility(View.GONE);
                break;
        }
    }

    private void initProgress(ViewHolder holder, DownLoadInfo downloadInfo) {
        ProgressBar bar = (ProgressBar) holder.getView(R.id.progress_bar);
        bar.setProgress((int) downloadInfo.getDownloadedSize());
        long downloadedSize = downloadInfo.getDownloadedSize();
        long totalSize = downloadInfo.getContentLength();

        if (downloadedSize > 0 && totalSize > 0) {
            int percent = (int) (downloadedSize * 100 / totalSize);
            holder.setText(R.id.txt_progress, percent + "%");
            bar.setProgress(percent);
        } else {
            holder.setText(R.id.txt_progress, "0%");
            bar.setProgress(0);
        }
    }
}
