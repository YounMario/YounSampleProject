package com.younchen.younsampleproject.ui.activity;

/**
 * Created by 龙泉 on 2016/10/18.
 */

public interface PlayManager {

     Player getCurrentPlayer();

    void play();

    void setCurrentPlayer(Player selectedPlayer, int itemPosition);

    void saveState(String playerId);

    void restoreState(String playerId);

    void stopPlay();
}
