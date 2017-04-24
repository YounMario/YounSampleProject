package com.younchen.younsampleproject.sys.notification;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.younsampleproject.App;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.sys.notification.adapter.NotificationMessageAdapter;
import com.younchen.younsampleproject.sys.notification.controller.ChatEntranceController;
import com.younchen.younsampleproject.sys.notification.model.ChatEntrance;
import com.younchen.younsampleproject.sys.notification.model.ChatNotification;
import com.younchen.younsampleproject.sys.notification.model.NotificationObserver;
import com.younchen.younsampleproject.sys.notification.utils.NotificationServiceUtil;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ChatNotificationFragment extends BaseFragment implements NotificationObserver {

    private TextView mTextView;
    private RecyclerView mRecycleView;
    private NotificationMessageAdapter mNotificationMessageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        openNotificationPermissionIfNeed();
        NotificationDispatcher.getInstance().registObserver(this);
        initUi(view);
    }

    private void initUi(View view) {
        mRecycleView = (RecyclerView) view.findViewById(R.id.notification_message_list);
        mNotificationMessageAdapter = new NotificationMessageAdapter(getActivity());
        mRecycleView.setAdapter(mNotificationMessageAdapter);
        ChatEntrance chatEntrance = ChatEntranceController.getInstance().getSavedChatNotification();
        if (chatEntrance != null) {
            mNotificationMessageAdapter.add(chatEntrance);
        }
    }

    private void openNotificationPermissionIfNeed() {
        boolean isValid = NotificationServiceUtil.checkServiceValid(getActivity());
        if (!isValid) {
            openNotificationPermission();
        }
    }

    private void openNotificationPermission() {
        Intent intent = NotificationServiceUtil.getNotificationServiceSettingIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getInstance().startActivity(intent);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationDispatcher.getInstance().unRegistObserver(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceiveMessage(ChatNotification notification) {
        ChatEntrance chatEntrance = new ChatEntrance();
        chatEntrance.replyIntent = notification.notificationAction.mIntent;
        chatEntrance.imgUrl = Constants.HEAD_IMG[1];
        chatEntrance.title = Constants.NAME[1];
        chatEntrance.action = ChatNotificationUtils.getActions(notification.rawNotification);
        mNotificationMessageAdapter.add(chatEntrance);
    }


}
