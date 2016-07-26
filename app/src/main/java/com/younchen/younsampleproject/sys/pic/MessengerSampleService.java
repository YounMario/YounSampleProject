package com.younchen.younsampleproject.sys.pic;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerSampleService extends Service {

    private Messenger messenger;
    private static final String TAG = "ipcSample";

    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String content = bundle.getString("content");
            Log.i(TAG, content);
            Messenger client = msg.replyTo;
            Message replyMessage = Message.obtain();
            bundle.putString("content","服务端回复:收到：" + content);
            replyMessage.setData(bundle);
            try {
                client.send(replyMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        messenger = new Messenger(messageHandler);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return messenger.getBinder();
    }
}
