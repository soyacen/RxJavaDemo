package com.example.schedulers;

import java.math.BigDecimal;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/16 10:02
 * 邮箱:songyancheng@wanda.cn
 */
public class ComputationScheduler {

    public static void main(String argv[]){
        /**
         * 这个是计算工作默认的调度器，它与I/O操作无关。
         * 它也是许多RxJava方法的默认调度器：buffer(),debounce(),delay(),interval(),sample(),skip()。
         */

        Observable.create(subscriber -> {

            System.out.println("Observable thread: "+Thread.currentThread().getId());
            int a=10+11;
            subscriber.onNext(a);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.computation()).subscribe(System.out::println);

        System.out.println("main thread: "+Thread.currentThread().getId());
    }

}
