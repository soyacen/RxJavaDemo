package com.example.async;

import java.awt.AWTException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action2;
import rx.functions.ActionN;
import rx.functions.Func0;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;
import rx.util.async.Async;
import rx.util.async.StoppableObservable;

/**
 * Created by Jax on 16/12/16 14:36
 * 邮箱:songyancheng@wanda.cn
 */
public class AsyncTest {
    public static void main(String argv[]) {
        System.out.println(Thread.currentThread().getId());

        Func0<Observable<String>> func0 = Async.toAsync(() -> {
            System.out.println(Thread.currentThread().getId());
            return getJson("toAsync");
        });
        func0.call().subscribe(System.out::println);


        FuncN<Observable<Void>> func1 = Async.asyncAction(args -> {
            System.out.println(Thread.currentThread().getId());
            getJson((String) args[0]);
        });
        func1.call("asyncAction").subscribe(System.out::println);



        FuncN<Observable<String>> func2 = Async.asyncFunc(args -> {
            System.out.println(Thread.currentThread().getId());
            return getJson((String) args[0]);
        });
        func2.call("asyncFunc").subscribe(System.out::println);



        StoppableObservable stoppableObservable = Async.runAsync(
                Schedulers.newThread(),
                new Action2<Observer<? super String>, Subscription>() {
                    @Override
                    public void call(Observer<? super String> observer, Subscription subscription) {
                        String i = "0";
                        System.out.println("runAsync :"+Thread.currentThread().getId());
                        while (!subscription.isUnsubscribed()) {
                            i+="0";
                            observer.onNext(i);
                            if(i.length()>10){
                                subscription.unsubscribe();
                            }
                        }
                        observer.onCompleted();
                    }
                });
        stoppableObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {
                System.out.println(o);
            }
        });

        while (true);
    }

    private static String getJson(String url) {
        return  url+"{data:null}" ;
    }

}
