package com.younchen.younsampleproject.sys.notification;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.younsampleproject.App;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.sys.notification.model.NotificationAction;
import com.younchen.younsampleproject.sys.notification.model.NotificationObserver;
import com.younchen.younsampleproject.sys.notification.utils.NotificationServiceUtil;

/**
 * Created by Administrator on 2017/4/19.
 */

public class NotificationFragment extends BaseFragment implements NotificationObserver {

    private TextView mTextView;

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
        mTextView = (TextView) view.findViewById(R.id.txt_view);
        openNotificationPermissionIfNeed();
        NotificationDispatcher.getInstance().registObserver(this);
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

    @Override
    public void onReceiveMessage(NotificationAction notification) {
        mTextView.setText(notification.extraStr);
    }


}
