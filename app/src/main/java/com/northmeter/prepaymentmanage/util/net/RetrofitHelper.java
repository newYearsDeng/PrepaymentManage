package com.northmeter.prepaymentmanage.util.net;


import com.northmeter.prepaymentmanage.util.Contants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lht on 2016/11/10.
 */
public class RetrofitHelper {
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static ApiService service;

    /**
     * 获取retrofit 对象
     *
     * @return
     */
    protected static Retrofit getmRetrofit() {

        if (mRetrofit == null) {
            if (mOkHttpClient == null) {
                mOkHttpClient = OkHttp3Utils.getOkHttpClient();
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Contants.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;

    }

    public static ApiService getApiService() {
        if (service == null) {
            service = getmRetrofit().create(ApiService.class);
        }

        return service;
    }


}
