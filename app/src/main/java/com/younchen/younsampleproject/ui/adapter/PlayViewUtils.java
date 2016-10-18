package com.younchen.younsampleproject.ui.adapter;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.younchen.younsampleproject.ui.activity.Player;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public class PlayViewUtils {

    public static float visibleAreaOffset(Player player, ViewParent parent) {
        if (player.getPlayerView() == null) {
            throw new IllegalArgumentException("Player must have a valid VideoView.");
        }

        Rect videoRect = getVideoRect(player);
        Rect parentRect = getRecyclerViewRect(parent);

        if (parentRect != null && (parentRect.contains(videoRect) || parentRect.intersect(videoRect))) {
            float visibleArea = videoRect.height() * videoRect.width();
            float viewArea = player.getPlayerView().getWidth() * player.getPlayerView().getHeight();
            return viewArea <= 0.f ? 1.f : visibleArea / viewArea;
        } else {
            return 0.f;
        }
    }

    private static Rect getVideoRect(Player player) {
        Rect rect = new Rect();
        Point offset = new Point();
        player.getPlayerView().getGlobalVisibleRect(rect, offset);
        return rect;
    }

    @Nullable
    private static Rect getRecyclerViewRect(ViewParent parent) {
        if (parent == null) { // view is not attached to RecyclerView parent
            return null;
        }

        if (!(parent instanceof View)) {
            return null;
        }

        Rect rect = new Rect();
        Point offset = new Point();
        ((View) parent).getGlobalVisibleRect(rect, offset);
        return rect;
    }
}
