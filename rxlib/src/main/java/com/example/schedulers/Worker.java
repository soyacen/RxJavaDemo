package com.example.schedulers;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/16 10:52
 * 邮箱:songyancheng@wanda.cn
 */
public class Worker {
    public static void main(String []argv){
        System.out.println(Thread.currentThread().getId()+" main ");
        Schedulers.newThread().createWorker().schedule(()->{
            System.out.println(Thread.currentThread().getId()+" Observable ");
        });
    }
}
