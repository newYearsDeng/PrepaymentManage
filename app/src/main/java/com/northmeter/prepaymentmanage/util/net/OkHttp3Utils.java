package com.northmeter.prepaymentmanage.util.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Lht
 * on 2016/12/19.
 * des: okHttp的配置
 */
public class OkHttp3Utils {
    public static OkHttpClient okHttpClient;

    /**
     *获取okHTTPClient对象
     */
    public static OkHttpClient getOkHttpClient(){
        if(okHttpClient==null){
            okHttpClient=new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .build();
        }
        return  okHttpClient;
    }
}
