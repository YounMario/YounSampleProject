package com.younchen.younsampleproject.sys.notification.controller;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;

import com.younchen.younsampleproject.commons.utils.Evn;
import com.younchen.younsampleproject.commons.utils.FileUtils;
import com.younchen.younsampleproject.commons.utils.ParcelableUtil;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.sys.notification.model.ChatEntrance;

import java.io.File;

/**
 * Created by Administrator on 2017/4/24.
 */

public class ChatEntranceController {

    private static final String ENTRANCE_SAVE_DIR = Evn.getAppPath() + File.separator + "entrance";
    private static final String YOUN_ENTRANCE = ENTRANCE_SAVE_DIR + File.separator + "youn_entrance";
    private static ChatEntranceController mInstance;

    public static ChatEntranceController getInstance() {
        if (mInstance == null) {
            mInstance = new ChatEntranceController();
        }
        return mInstance;
    }


    public void saveEntranceIfNotExist(Bundle bundle) {
        if (!FileUtils.isExist(ENTRANCE_SAVE_DIR)) {
            File file = new File(ENTRANCE_SAVE_DIR);
            file.mkdir();
        }
        if (FileUtils.isExist(ENTRANCE_SAVE_DIR) && FileUtils.isExist(YOUN_ENTRANCE)) {
            return;
        } else {
            if (bundle == null) {
                return;
            }
            try {
                byte[] intentBytes = ParcelableUtil.marshall(bundle);
                FileUtils.writeFile(intentBytes, YOUN_ENTRANCE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private PendingIntent getSavedPendingIntent() {
        if (!FileUtils.isExist(YOUN_ENTRANCE)) {
            return null;
        }
        byte[] readByte = null;
        try {
            readByte = FileUtils.readFile(YOUN_ENTRANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (readByte != null) {
            Parcel parcel = ParcelableUtil.unmarshall(readByte);
            PendingIntent pendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            return pendingIntent;
        }
        return null;
    }

    public ChatEntrance getSavedChatNotification() {
        PendingIntent pendingIntent = getSavedPendingIntent();
        if (pendingIntent == null) {
            return null;
        }
        ChatEntrance chatEntrance = new ChatEntrance();
        chatEntrance.imgUrl = Constants.HEAD_IMG[1];
        chatEntrance.title = Constants.NAME[1];
        chatEntrance.replyIntent = pendingIntent;
        return chatEntrance;
    }

}
