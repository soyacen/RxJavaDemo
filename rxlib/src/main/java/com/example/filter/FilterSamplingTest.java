package com.example.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Jax on 16/12/13 17:58
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterSamplingTest {
    public static void main(String argv[]) {

        sample();
    }

    private static void sample() {

        Observable<Long> observableString = Observable.interval(100,TimeUnit.MILLISECONDS);

        observableString/*.sample(1, TimeUnit.SECONDS)*/
                /*.throttleLast(1,TimeUnit.SECONDS)*/
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(Long item) {
                System.out.println("*********>Item is " + item);
            }
        });

        while (true);
    }
}
