package com.younchen.younsampleproject.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public class VideoListScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerView parent;

    private PlayManager playManager;
    private ArrayList<Integer> playbleIndexs;

    private final String TAG  = "Scrollistener";


    public VideoListScrollListener(RecyclerView parent,PlayManager playManager){
        this.parent = parent;
        this.playManager = playManager;
        playbleIndexs = new ArrayList<>();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (parent == null) {
            throw new RuntimeException("please set parent and layoutManager");
        }
        if (newState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }

        playbleIndexs.clear();
        int currentPlayIndex = playManager.getCurrentPlayIndex();

        int firstPosition = RecyclerView.NO_POSITION;
        int lastPosition = RecyclerView.NO_POSITION;

        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
            firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
            lastPosition = linearLayoutManager.findLastVisibleItemPosition();
        }
        //枚举第一次展现的item 到最后一次展现的item的播放权重
        for (int i = firstPosition; i <= lastPosition; i++) {
            RecyclerView.ViewHolder holder = parent.findViewHolderForAdapterPosition(i);
            if (holder instanceof Player) {
                Player player = (Player) holder;
                if (player.canPlay()) {
                    playbleIndexs.add(i);
                }
            }
        }

        //
        int canPlayIndex = findNeedPlay();


        if (canPlayIndex != -1 && canPlayIndex == currentPlayIndex) {
            Log.i(TAG, "notify play current");
            playManager.play();
            return;
        }

        if (currentPlayIndex != -1){
            Log.i(TAG, "notify stop current");
            playManager.stopPlay();
        }

        if (canPlayIndex == -1) {
            return;
        }

        playManager.setCurrentPlay(canPlayIndex);
        playManager.play();
    }

    private int findNeedPlay() {
        return playbleIndexs.size() == 0 ? -1 : playbleIndexs.get(0);
    }

}
