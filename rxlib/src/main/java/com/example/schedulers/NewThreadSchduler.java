package com.example.schedulers;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/16 10:25
 * 邮箱:songyancheng@wanda.cn
 */
public class NewThreadSchduler {

    public static void main(String []argv){

        /**
         * 这个调度器正如它所看起来的那样：它为指定任务启动一个新的线程。
         */

        System.out.println(Thread.currentThread().getId()+" main ");
        Observable.create(subscriber -> {
            System.out.println(Thread.currentThread().getId()+" Observable ");
            int a=10+11;
            subscriber.onNext(a);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.newThread()).subscribe(System.out::print);



    }

}
