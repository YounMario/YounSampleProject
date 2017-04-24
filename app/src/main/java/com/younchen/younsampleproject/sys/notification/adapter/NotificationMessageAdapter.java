package com.younchen.younsampleproject.sys.notification.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.RemoteInput;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.sys.notification.model.ChatEntrance;
import com.younchen.younsampleproject.sys.notification.model.ChatNotification;

/**
 * Created by Administrator on 2017/4/24.
 */
public class NotificationMessageAdapter extends BaseAdapter<ChatEntrance>{

    private Context mContext;
    private static final String REMOTE_INPUT_RESULT_KEY = "android_wear_voice_input";
    private RemoteInput mRemoteInput;

    public NotificationMessageAdapter(Context context) {
        super(context, R.layout.item_notification_chat);
        this.mContext = context;
    }


    @Override
    public void covert(ViewHolder holder, final ChatEntrance item) {
        TextView reply = (TextView) holder.getView(R.id.txt_reply);
        ImageView imageView = (ImageView) holder.getView(R.id.img_head);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyMessage(item);
            }
        });
        holder.setText(R.id.txt_contact_name, item.title);
        Glide.with(mContext)
                .load(item.imgUrl)
                .centerCrop()
                .crossFade()
                .into(imageView);
        if (item.action == null || item.action.getActionIntent() == null) {
            reply.setVisibility(View.GONE);
        } else {
            reply.setVisibility(View.VISIBLE);
        }
    }

    private void replyMessage(ChatEntrance item) {
        PendingIntent pendingIntent = item.action.getActionIntent();
        if (pendingIntent != null) {
            final Intent localIntent = new Intent();
            final Bundle localBundle = new Bundle();
            RemoteInput remoteInput = getRemoteInput();
            localBundle.putCharSequence(REMOTE_INPUT_RESULT_KEY, "hello nimeiya");
            RemoteInput.addResultsToIntent(new RemoteInput[]{remoteInput}, localIntent, localBundle);
            try {
                pendingIntent.send(mContext, 0, localIntent);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private RemoteInput getRemoteInput() {
        //choice 指可以快捷回复的内容， 比如 ： 好的，我马上回去 等
        //extra 不知道干嘛的
        //主要是input result key
        if (mRemoteInput == null) {
            mRemoteInput = new RemoteInput.Builder(REMOTE_INPUT_RESULT_KEY).build();
        }
        return mRemoteInput;
    }


    private boolean isSupportReply(ChatNotification item) {
        return item.notificationAction != null && item.notificationAction.remoteInputs != null && item.notificationAction.remoteInputs.length != 0;
    }
}
