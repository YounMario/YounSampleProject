package com.younchen.younsampleproject.sys.pic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.sys.pic.bean.Book;

public class AidlSampleActivity extends AppCompatActivity {

    private TextView result;
    private EditText txt_id;
    private EditText txt_name;

    private IBookManagerInterface remotoBookManager;
    private String TAG  = "AidlSampleActivity";


    private ServiceConnection serviceConnection;
    private IBinder.DeathRecipient binderDeathRecipient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_sample);
        init();
        initData();
        bindAndStartService(serviceConnection);


    }

    private void initData() {
        binderDeathRecipient = new IBinder.DeathRecipient() {
            @Override
            public void binderDied() {
                if (remotoBookManager == null) return;
                //解绑 binder死亡监听
                Log.i(TAG,"binder dead !");
                remotoBookManager.asBinder().unlinkToDeath(binderDeathRecipient, 0);
                //TODO reconnect servic  here
            }

        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("younchen", "Service connected");
                remotoBookManager = AbstractBookManager.asInterface(service);
                try {
                    //添加binder死亡监听
                    service.linkToDeath(binderDeathRecipient, 0);

                    Messenger m = new Messenger(service);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("younchen", "Service disconnected");
            }
        };
    }


    private void bindAndStartService(ServiceConnection connection) {
        Intent intent = new Intent(AidlSampleActivity.this, AidlSampleService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    private void init() {
        result = (TextView) findViewById(R.id.txt_result);
        txt_id = (EditText) findViewById(R.id.bookId);
        txt_name = (EditText) findViewById(R.id.bookName);
    }

    public void save(View v) {
        if (TextUtils.isEmpty(txt_id.getText()) || TextUtils.isEmpty(txt_name.getText())) {
            return;
        }
        String id = txt_id.getText().toString();
        Book book = new Book(Integer.parseInt(id), txt_name.getText().toString());
        remotoBookManager.addBook(book);
    }

    public void query(View v) {
        if (TextUtils.isEmpty(txt_id.getText())) {
            return;
        }
        String id = txt_id.getText().toString();
        //如果是耗时操作，会造成 ANR;
        Book book = remotoBookManager.querybook(Integer.parseInt(id));
        if (book == null) {
            result.setText("book is null");
        } else {
            result.setText("book name:" + book.getBookName() + "  book id:" + book.getId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }
}
