package com.younchen.younsampleproject.ui.activity;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public interface PlayManager {

    void play();

    void setCurrentPlay(int index);

    void stopPlay();

    int getCurrentPlayIndex();
}
