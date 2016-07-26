package com.younchen.younsampleproject.sys.pic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.younchen.younsampleproject.R;

public class MessengerSampleActivity extends AppCompatActivity {

    private ServiceConnection connection;
    private Messenger serverMessenger;

    private EditText txtEdit;
    private TextView resultText;

    private int MESSAGE_FROM_CLIENT = 1;

    private Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String content = bundle.getString("content");
            resultText.setText(content);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_sample);
        initView();
        initData();
        bindService();
    }

    private void initView() {
        resultText = (TextView) findViewById(R.id.txt_result);
        txtEdit = (EditText) findViewById(R.id.edit_content);
    }

    private void initData() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serverMessenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    public void send(View view) {
        if (TextUtils.isEmpty(txtEdit.getText())) {
            return;
        }
        String content = txtEdit.getText().toString();
        Message msg = Message.obtain(null, MESSAGE_FROM_CLIENT);
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        msg.setData(bundle);
        msg.replyTo = clientMessenger;
        try {
            serverMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void bindService() {
        Intent intent = new Intent(MessengerSampleActivity.this, MessengerSampleService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }
}
