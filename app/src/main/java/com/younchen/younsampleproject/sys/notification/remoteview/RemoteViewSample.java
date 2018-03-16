package com.younchen.younsampleproject.sys.notification.remoteview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;

/**
 * Created by yinlongquan on 2018/3/16.
 */

public class RemoteViewSample extends BaseFragment {

    Button btnSend;

    NotificationManager mNotificationManager;
    RemoteViews remoteViews;
    Notification notification;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSend = (Button) view.findViewById(R.id.send_button);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotificationManager.notify(0, notification);
            }
        });
        mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getContext().getPackageName(), R.layout.remote_view);
        remoteViews.setImageViewResource(R.id.img, R.drawable.ro);
        remoteViews.setTextViewText(R.id.txt_message, "message");


        notification = new NotificationCompat.Builder(getContext()).setContentText("lolo").setContentTitle("Yami")
                .setSmallIcon(R.drawable.ro).setCustomBigContentView(remoteViews)
                .addAction(R.drawable.ro, "xxx", null)
                .build();


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_common, container, false);
    }

    @Override
    public void onBackKeyPressed() {

    }
}
