package com.example.async;

import rx.Observable;
import rx.functions.Func0;
import rx.util.async.Async;

/**
 * Created by Jax on 16/12/16 14:36
 * 邮箱:songyancheng@wanda.cn
 */
public class StartTest {
    public static  void main(String argv[]){
        System.out.println(Thread.currentThread().getId());
        Async.start(()->{
            System.out.println(Thread.currentThread().getId());
            return getJson("www.baidu.com");
        }).subscribe(System.out::println);

    }

    private static String getJson(String url){
        return "{data:null}";
    }

}
