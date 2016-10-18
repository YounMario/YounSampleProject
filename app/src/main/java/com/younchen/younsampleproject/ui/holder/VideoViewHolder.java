package com.younchen.younsampleproject.ui.holder;

import android.util.Log;
import android.view.View;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.ui.activity.Player;
import com.younchen.younsampleproject.ui.adapter.PlayViewUtils;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public class VideoViewHolder extends ViewHolder implements Player {

    private static final float DEFAULT_OFFSET = 0.8f;
    private boolean isPlaying;
    private int itemPosition;

    private static final String TAG = "videoViewHolder";

    public VideoViewHolder(View itemView) {
        super(itemView);
        init();
    }

    private void setUItoPlay(){
        setText(R.id.txt_num, "playing");
    }

    private void setUItoStop() {
        setText(R.id.txt_num, getItemPosition() + "");
    }

    private void init(){
        this.isPlaying = false;
    }

    @Override
    public boolean isPlayable() {
        return true;
    }

    @Override
    public boolean canPlay() {
        return PlayViewUtils.visibleAreaOffset(this, getItemView().getParent()) > DEFAULT_OFFSET;
    }

    @Override
    public boolean isPlaying() {
        Log.i(TAG, "is playing:" + isPlaying);
        return isPlaying;
    }

    @Override
    public void saveCurrentState() {

    }

    @Override
    public void stopPlaying() {
        isPlaying = false;
        Log.i(TAG, "stop playing ");
        setUItoStop();
    }

    @Override
    public View getPlayerView() {
        return itemView;
    }

    @Override
    public void play() {
        isPlaying = true;
        setUItoPlay();
    }


    @Override
    public String getPlayerId() {
        return getAdapterPosition() + "@" + hashCode();
    }

    @Override
    public int getItemPosition() {
        return itemPosition;
    }

    @Override
    public void setItemPosition(int i) {
        this.itemPosition = i;
    }
}
