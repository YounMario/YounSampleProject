package com.younchen.younsampleproject.http.retrofit;

import com.younchen.younsampleproject.http.retrofit.bean.DoubanBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public interface DoubanBookApi {

    public static String HOST = "https://api.douban.com/v2/";

    @GET("book/{bookId}")
    public Call<DoubanBook> getTestBook(@Path("bookId") String id);
}
