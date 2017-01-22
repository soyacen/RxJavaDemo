package com.example.transforming;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by Jax on 16/12/16 11:31
 * 邮箱:songyancheng@wanda.cn
 */
public class CallBackHell {
    public static void main(String arv[]) {

        hell();

        heaven();

    }

    private static void hell() {
        getImList(new Action1<List>() {
            @Override
            public void call(List list) {
                System.out.println("获取到imlist");

            }
        });
    }

    private static void getUser(Action1<Integer> callBack) {
        try {
            Thread.sleep(1000);
            System.out.println("从接口得到用户id1234");
            callBack.call(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getImList(Action1<List> callBack) {
        getUser(new Action1<Integer>() {
            @Override
            public void call(Integer id) {
                try {
                    Thread.sleep(1000);
                    System.out.println("用id"+id+"获取聊天列表");
                    callBack.call(new ArrayList<>());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static void heaven() {
        Observable.create(subscriber -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("从接口得到用户id1234");
                        subscriber.onNext(1234);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).map(
                id -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("用id"+id+"获取聊天列表");
                        return new ArrayList<String>();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).subscribe(strings-> {
            System.out.println("获取到imlist");
        });
    }
}
