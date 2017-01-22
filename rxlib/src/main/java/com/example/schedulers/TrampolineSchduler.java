package com.example.schedulers;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/16 10:27
 * 邮箱:songyancheng@wanda.cn
 */
public class TrampolineSchduler {

    public static void main(String []argv){

        /**
         * 当我们想在当前线程执行一个任务时，并不是立即，我们可以用.trampoline()将它入队。
         * 这个调度器将会处理它的队列并且按序运行队列中每一个任务。
         * 它是repeat()和retry()方法默认的调度器。
         */

        System.out.println(Thread.currentThread().getId()+" main ");
        Observable.create(subscriber -> {
            System.out.println(Thread.currentThread().getId()+" Observable ");
            int a=10+11;
            subscriber.onNext(a);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.trampoline()).subscribe(System.out::print);



    }

}
