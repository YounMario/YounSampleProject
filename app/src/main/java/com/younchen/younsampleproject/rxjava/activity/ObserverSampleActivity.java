package com.younchen.younsampleproject.rxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.rxjava.bean.Person;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ObserverSampleActivity extends AppCompatActivity {

    private String TAG = "ObserverSampleActivity";
    private Observable<String> observable;
    private Observer<String> observer;

    private Action1<String> action;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_sample);
        btn = (Button) findViewById(R.id.button2);

        observer =  new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG,s);
            }
        };
        initObservable();
    }

    private void initObservable(){
        observable =  Observable.just("ssd","dfdf").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        });

        //
    }

    public void notify(View view){
        observable.subscribe(observer);
    }


}
