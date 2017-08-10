package com.younchen.younsampleproject.http.retrofit.data;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.younchen.younsampleproject.http.retrofit.DoubanBookApi;
import com.younchen.younsampleproject.http.retrofit.bean.DoubanBook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class DoubanBookData extends LiveData<List<DoubanBook>> {

    private static final String TAG = "DoubanBookData";
    private Context mContext;
    private Retrofit mRetrofit;
    private DoubanBookApi mDoubanApi;

    private Handler mHandler;

    public DoubanBookData(Context context) {
        this.mContext = context;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(DoubanBookApi.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mDoubanApi = mRetrofit.create(DoubanBookApi.class);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onActive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<DoubanBook> call = mDoubanApi.getTestBook("1220562");
                final DoubanBook book;
                try {
                    book = call.execute().body();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<DoubanBook> list = new ArrayList<>();
                            list.add(book);
                            setValue(list);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onInactive() {
        mHandler.removeCallbacks(null);
    }

}
