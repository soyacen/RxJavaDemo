package com.example.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

/**
 * Created by Jax on 16/12/16 15:59
 * 邮箱:songyancheng@wanda.cn
 */
public class FutureTest {

    public static void main(String[] argv) {
        System.out.println("main thread:" + Thread.currentThread().getId());
        //startFuture();

        //deferFuture();

        forEachFuture();

        while (true);
    }

    public static void startFuture() {
        Observable<String> o = Async.startFuture(() -> Executors.newCachedThreadPool().submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("call()");
                return getJson("startFuture thread:" + Thread.currentThread().getId());
            }
        }));
        o.subscribe(System.out::println);

    }

    //延迟到subscribe才调用call()
    public static void deferFuture() {
        Observable<String> o = Async.deferFuture(() -> Executors.newCachedThreadPool().submit(new Callable<Observable<String>>() {
            @Override
            public Observable<String> call() throws Exception {
                System.out.println("call()");
                return Observable.just(getJson("deferFuture thread:" + Thread.currentThread().getId()));
            }
        }));
        o.subscribe(System.out::println);
    }

    //如果你想要一个函数阻塞直到Observable执行完成，可以使用这个操作符。函数与Observable在同一个线程。
    public static void forEachFuture() {
        FutureTask<Void> f = Async.forEachFuture(
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        System.out.println("sleep 5 s");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onNext(getJson("forEachFuture"));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()),
                string -> {
                    System.out.println(Thread.currentThread().getId());
                    System.out.println(string);
                },
                Schedulers.io()
        );
        //Executors.newCachedThreadPool().execute(f);
    }


    private static String getJson(String url) {
        return url + "{data:null}";
    }

}
