package com.example.async;

import rx.functions.Action0;
import rx.util.async.Async;

/**
 * Created by Jax on 16/12/19 09:20
 * 邮箱:songyancheng@wanda.cn
 */
public class FromTest {

    public static void main(String argvp[]){
        System.out.println("main"+Thread.currentThread().getId());
        fromAction();

        fromCallable();

        fromRunnable();

        while (true);
    }



    private static void fromAction() {
        Async.fromAction(() ->{
                System.out.println("call_fromAction"+Thread.currentThread().getId());
        },"fromAction").subscribe(System.out::println);
    }

    private static void fromCallable() {
        Async.fromCallable(()->"call_fromCallable"+Thread.currentThread().getId()).subscribe(System.out::println);
    }

    private static void fromRunnable() {
        Async.fromRunnable(() ->{
            System.out.println("call_fromRunnable"+Thread.currentThread().getId());
        },"fromRunnable").subscribe(System.out::println);
    }
}
