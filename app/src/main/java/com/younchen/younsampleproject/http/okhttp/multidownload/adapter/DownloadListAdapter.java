package com.younchen.younsampleproject.http.okhttp.multidownload.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;
import com.younchen.younsampleproject.http.okhttp.download.DownloadModel;
import com.younchen.younsampleproject.http.okhttp.download.DownloadService;

/**
 * Created by yinlongquan on 2017/10/12.
 */

public class DownloadListAdapter extends BaseAdapter<DownLoadInfo> {

    private DownloadEventReceiver mDownloadReceiver;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public DownloadListAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_mulit_download);
        mRecyclerView = recyclerView;
        mContext = context;
        mDownloadReceiver = new DownloadEventReceiver(this);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(DownloadService.ACTION_DOWNLOAD_LOCAL_BROADCAST);
        LocalBroadcastManager.getInstance(context).registerReceiver(mDownloadReceiver, mIntentFilter);
    }

    @Override
    public void covert(final ViewHolder holder, DownLoadInfo item, int position) {
        final int itemPosition = holder.getAdapterPosition();
        final DownLoadInfo downloadInfo = getItem(holder.getAdapterPosition());
        String fileName = downloadInfo.getUrl().substring(downloadInfo.getUrl().lastIndexOf('/'));
        holder.setText(R.id.txt_file_name, fileName);
        initProgress(holder, downloadInfo);


        Button startBtn = (Button) holder.getView(R.id.btn_start);
        Button endBtn = (Button) holder.getView(R.id.btn_end);

        if (DownloadModel.getInstance().isDownloaded(downloadInfo)) {
            downloadInfo.setState(DownLoadInfo.FINISHED);
            downloadInfo.setIndex(itemPosition);
            updateStateFinish(downloadInfo, holder);
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadInfo.getState() == DownLoadInfo.NORMAL || downloadInfo.getState() == DownLoadInfo.PAUSE) {
                    DownloadService.startDownload(mContext, itemPosition, downloadInfo.getUrl());
                } else if (downloadInfo.getState() == DownLoadInfo.DOWNLOADING) {
                    DownloadService.pauseDownload(mContext, downloadInfo.getUrl());
                }
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadService.cancelDownload(mContext, itemPosition, downloadInfo.getUrl());
            }
        });
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

    private static class DownloadEventReceiver extends BroadcastReceiver {

        private DownloadListAdapter mDownloadAdapter;

        public DownloadEventReceiver(DownloadListAdapter downloadListAdapter) {
            mDownloadAdapter = downloadListAdapter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            DownLoadInfo downLoadInfo = (DownLoadInfo) intent.getSerializableExtra(DownloadService.KEY_DOWNLOAD_INFO);
            switch (downLoadInfo.getState()) {
                case DownLoadInfo.NORMAL:
                    mDownloadAdapter.updateStateNormal(downLoadInfo);
                    break;
                case DownLoadInfo.PREPARE:
                    mDownloadAdapter.updateStateToPrepare(downLoadInfo);
                    break;
                case DownLoadInfo.PAUSE:
                    mDownloadAdapter.updateStatePause(downLoadInfo);
                    break;
                case DownLoadInfo.DOWNLOADING:
                    mDownloadAdapter.updateStateDownloading(downLoadInfo);
                    break;
                case DownLoadInfo.FINISHED:
                    mDownloadAdapter.updateStateFinish(downLoadInfo);
                    break;
                case DownLoadInfo.CANCEL:
                    mDownloadAdapter.updateStateCancel(downLoadInfo);
            }
        }
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mDownloadReceiver);
    }

    private void updateStateToPrepare(DownLoadInfo downLoadInfo) {
        RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(downLoadInfo.getIndex());
        getItem(downLoadInfo.getIndex()).setState(downLoadInfo.getState());
        TextView txtView = (TextView) holder.itemView.findViewById(R.id.txt_progress);
        txtView.setText("connecting...");
    }

    private void updateStateCancel(DownLoadInfo downLoadInfo) {
        RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(downLoadInfo.getIndex());
        getItem(downLoadInfo.getIndex()).setState(downLoadInfo.getState());
        holder.itemView.findViewById(R.id.btn_start).setVisibility(View.GONE);
        holder.itemView.findViewById(R.id.btn_end).setVisibility(View.GONE);
        TextView txtView = (TextView) holder.itemView.findViewById(R.id.txt_progress);
        txtView.setText("canceled download");

    }

    private void updateStateFinish(DownLoadInfo downLoadInfo) {
        ViewHolder holder = (ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(downLoadInfo.getIndex());
        updateStateFinish(downLoadInfo, holder);
    }

    private void updateStateFinish(DownLoadInfo downLoadInfo, ViewHolder viewHolder) {
        getItem(downLoadInfo.getIndex()).setState(downLoadInfo.getState());
        Button startBtn = (Button) viewHolder.getView(R.id.btn_start);
        ProgressBar progressBar = (ProgressBar) viewHolder.getView(R.id.progress_bar);
        TextView txtView = (TextView) viewHolder.getView(R.id.txt_progress);
        progressBar.setProgress(100);
        startBtn.setVisibility(View.GONE);
        txtView.setText("100%");
    }


    private void updateStateDownloading(DownLoadInfo downLoadInfo) {
        ViewHolder holder = (ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(downLoadInfo.getIndex());
        getItem(downLoadInfo.getIndex()).setState(downLoadInfo.getState());
        ProgressBar progressBar = (ProgressBar) holder.getView(R.id.progress_bar);
        Button startBtn = (Button) holder.getView(R.id.btn_start);
        startBtn.setText("pause");
        progressBar.setProgress(downLoadInfo.getProgress());
        holder.setText(R.id.txt_progress, downLoadInfo.getProgress() + "%");
    }

    private void updateStatePause(DownLoadInfo downLoadInfo) {
        ViewHolder holder = (ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(downLoadInfo.getIndex());
        getItem(downLoadInfo.getIndex()).setState(downLoadInfo.getState());
        Button startBtn = (Button) holder.getView(R.id.btn_start);
        startBtn.setText("resume");
    }

    private void updateStateNormal(DownLoadInfo downLoadInfo) {
        ViewHolder holder = (ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(downLoadInfo.getIndex());
        getItem(downLoadInfo.getIndex()).setState(downLoadInfo.getState());
        Button startBtn = (Button) holder.getView(R.id.btn_start);
        startBtn.setText("start");
    }
}
