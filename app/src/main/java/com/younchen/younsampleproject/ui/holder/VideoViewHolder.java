package com.younchen.younsampleproject.ui.holder;

import android.view.View;

import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.ui.activity.Player;
import com.younchen.younsampleproject.ui.adapter.PlayViewUtils;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public class VideoViewHolder extends ViewHolder implements Player {

    private static final float DEFAULT_OFFSET = 0.8f;

    private static final String TAG = "videoViewHolder";

    public VideoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public boolean canPlay() {
        return PlayViewUtils.visibleAreaOffset(this, getItemView().getParent()) > DEFAULT_OFFSET;
    }

    @Override
    public View getPlayerView() {
        return itemView;
    }


}
