package com.northmeter.prepaymentmanage.util.net;

import com.northmeter.prepaymentmanage.model.Entity.HttpResult;
import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;
import com.northmeter.prepaymentmanage.util.FileUtil;

import java.io.File;
import java.util.List;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import io.rx_cache.internal.RxCache;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Lht
 * on 2016/12/19.
 * des：所有的请求数据的方法集中地
 * 根据MovieService的定义编写合适的方法
 * 其中observable是获取API数据
 * observableCahce获取缓存数据
 * new EvictDynamicKey(false) false使用缓存  true 加载数据不使用缓存
 */
public class HttpData extends RetrofitHelper {

    private static File cacheDir = FileUtil.getcacheDir();
    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDir)
            .using(CacheProviders.class);
    protected static final ApiService service = getmRetrofit().create(ApiService.class);
    private HttpData httpData;

    private static class SingleHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    public static HttpData getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    private <T> void setSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * @param <T>
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
            if (tHttpResult.getRESCODE().equals("0")) {
                return null;
            }
            return tHttpResult.getRESPONSEXML();
        }
    }

    /**
     *
     */
    private class HttpReultFuncCache<T> implements Func1<Reply<T>, T> {
        @Override
        public T call(Reply<T> tHttpResult) {
            return tHttpResult.getData();
        }


    }

    //1.获取建筑一级目录
    public void getAreaInfo(Subscriber<List<RequestBuilding>> subscriber) {
        final Observable<List<RequestBuilding>> observable = service.getAreaInfo()
                .map(new HttpResultFunc<List<RequestBuilding>>());

        final Observable<List<RequestBuilding>> observableCache = providers.getBuilding(observable, new DynamicKey("getSchool"), new EvictDynamicKey(true))
                .map(new HttpReultFuncCache<List<RequestBuilding>>());
        setSubscribe(observableCache, subscriber);
    }

    //2.获取建筑 子目录 建筑 楼层 房间
    public void getSonInfo(String area_id, String dynamicKey, Subscriber<List<RequestBuilding>> subscriber) {
        final Observable<List<RequestBuilding>> observable = service.getSonInfo(area_id)
                .map(new HttpResultFunc<List<RequestBuilding>>());

        final Observable<List<RequestBuilding>> observableCache = providers.getBuilding(observable, new DynamicKey(dynamicKey), new EvictDynamicKey(true))
                .map(new HttpReultFuncCache<List<RequestBuilding>>());
        setSubscribe(observableCache, subscriber);

    }

}

