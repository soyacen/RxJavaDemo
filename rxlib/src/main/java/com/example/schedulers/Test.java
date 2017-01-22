package com.example.schedulers;

import java.io.File;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/28 09:59
 * 邮箱:songyancheng@wanda.cn
 */

public class Test {

    public static void main(String []a){
        /**
         * 通过 observeOn() 的多次调用，程序实现了线程的多次切换。
         * 不过，不同于 observeOn() ， subscribeOn() 的位置放在哪里都可以，
         * 但它是只能调用一次的。当使用了多个 subscribeOn() 的时候，只有第一个 subscribeOn() 起作用。
         */
        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())

                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        System.out.println("thread1: "+ Thread.currentThread().getId());
                        return "map1-> "+integer;
                    }
                }) // 新线程，由 observeOn() 指定

                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        System.out.println("thread2: "+ Thread.currentThread().getId());
                        return "map2-> "+s;
                    }
                }) // IO 线程，由 observeOn() 指定

                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                            System.out.println("Subscriber thread: "+ Thread.currentThread().getId());
                    }
                });  // newThread 线程，由 observeOn() 指定

        while (true);
    }

}
