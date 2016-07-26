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
import android.util.Log;
import android.widget.TextView;

import com.younchen.younsampleproject.IPersonAidlInterface;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.sys.pic.bean.Person;
import com.younchen.younsampleproject.sys.pic.listener.PersonNotifiyListener;

public class AidlCallBackSampleActivity extends AppCompatActivity {

    private ServiceConnection serviceConnection;
    private IPersonAidlInterface personServerManager;

    private PersonNotifiyListener notification;
    private final String TAG = "aidlCallBackClient";

    private TextView txtView;
    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Person person = (Person) msg.obj;
            txtView.setText("personName:"+person.getName()+" id:"+person.getId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_call_back_sample);
        init();
        bindService();
    }

    private void init() {
        txtView = (TextView) findViewById(R.id.textView);

        notification = new PersonNotifiyListener.Stub() {
            @Override
            public void onPersonAdded(Person person) throws RemoteException {
                Log.i(TAG, "person added :" + person.getName());
                Message msg = Message.obtain();
                msg.obj = person;
                handler.sendMessage(msg);
            }
        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                personServerManager = IPersonAidlInterface.Stub.asInterface(service);
                try {
                    personServerManager.registListenter(notification);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                personServerManager = null;
            }
        };
    }


    private void bindService() {
        Intent intent = new Intent(AidlCallBackSampleActivity.this, AidlCallBackService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notification != null) {
            try {
                personServerManager.unRegistListener(notification);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }

}
