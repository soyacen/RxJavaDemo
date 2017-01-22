package com.example.schedulers;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/16 10:16
 * 邮箱:songyancheng@wanda.cn
 */
public class ImmediateScheduler {
    public static void main(String []argv){

        /**
         * rxjava默认的schduler
         * 这个调度器允许你立即在当前线程执行你指定的工作。它是timeout(),timeInterval(),以及timestamp()方法默认的调度器。
         */

        System.out.println(Thread.currentThread().getId()+" main ");
        Observable.create(subscriber -> {
            System.out.println(Thread.currentThread().getId()+" Observable ");
            int a=10+11;
            subscriber.onNext(a);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.immediate()).subscribe(System.out::print);



    }

}
