package com.younchen.younsampleproject.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.ui.activity.PlayManager;
import com.younchen.younsampleproject.ui.holder.VideoViewHolder;
import com.younchen.younsampleproject.ui.widget.FooterHolder;
import com.younchen.younsampleproject.ui.widget.HeadHolder;
import com.younchen.younsampleproject.ui.widget.NBaseAdapter;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public class VideoAdapter extends NBaseAdapter<VideoInfo> implements PlayManager{


    private static String TAG ="VideoAdapter";

    private int[] colors = {Color.BLUE, Color.GRAY, Color.GREEN};

    private int currentPlayingPostion = 0;
    private boolean isPlaying = false;
    private ItemState currentState;

    enum ItemState {
        NOMAL, PALY;
    }

    public VideoAdapter(Context context) {
        super(context, R.layout.item_video);
    }

    @Override
    public void coverHead(HeadHolder holder) {

    }

    @Override
    public void coverFooter(FooterHolder holder) {

    }

    @Override
    public void covert(ViewHolder holder, int position) {
        holder.setText(R.id.txt_num, String.valueOf(position));
        View back = holder.getView(R.id.contain);
        back.setBackgroundColor(colors[position % colors.length]);
        if (currentPlayingPostion == position && isPlaying) {
            holder.setText(R.id.txt_num, "playing");
        }
    }



    @Override
    public void play() {
        Log.i(TAG, "playing position:" + currentPlayingPostion);
        setCurrentState(ItemState.PALY);
        notifyItemChanged(currentPlayingPostion);
        isPlaying = true;
    }

    private void setCurrentState(ItemState currentState) {
        this.currentState = currentState;
    }

    @Override
    public void setCurrentPlay(int index) {
        this.currentPlayingPostion = index;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new VideoViewHolder(itemView);
    }



    @Override
    public void stopPlay() {
        Log.i(TAG, "stop play position:" + currentPlayingPostion);
        setCurrentState(ItemState.NOMAL);
        notifyItemChanged(currentPlayingPostion);
        currentPlayingPostion = -1;
        isPlaying = false;
    }

    @Override
    public int getCurrentPlayIndex() {
        return currentPlayingPostion;
    }
}
