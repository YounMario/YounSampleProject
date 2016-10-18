package com.younchen.younsampleproject.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public class VideoListScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerView parent;
    private List<Player> playable;

    private PlayManager playManager;

    public VideoListScrollListener(RecyclerView parent,PlayManager playManager){
        this.parent = parent;
        this.playable = new ArrayList<>();
        this.playManager = playManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (parent == null) {
            throw new RuntimeException("please set parent and layoutManager");
        }
        if (newState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }

        playable.clear();
        Player currentPlayer = playManager.getCurrentPlayer();
        if (currentPlayer != null && currentPlayer.isPlaying()) {
            if (currentPlayer.canPlay() && currentPlayer.isPlayable()) {
                playable.add(currentPlayer);
            }
        }

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
                if (player.canPlay() && player.isPlayable()) {
                    player.setItemPosition(i);
                    playable.add(player);
                }
            }
        }

        //
        Player selectedPlayer = findNeedPlay(playable);

        if(selectedPlayer == currentPlayer){
            if (selectedPlayer != null && !selectedPlayer.isPlaying()) {
                playManager.restoreState(currentPlayer.getPlayerId());
                playManager.play();
            }
            return;
        }

        if (currentPlayer != null && currentPlayer.isPlaying()) {
            playManager.saveState(currentPlayer.getPlayerId());
            playManager.stopPlay();
        }

        if (selectedPlayer == null) {
            return;
        }

        playManager.setCurrentPlayer(selectedPlayer, selectedPlayer.getItemPosition());
        playManager.play();
    }

    private Player findNeedPlay(List<Player> playable) {
        if (playable.size() == 0) {
            return null;
        }
        return playable.get(0);
    }

}
