package com.example.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Jax on 16/12/13 17:17
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterTakeTest {

    public static void main(String argv[]) {

        /**
         * 当我们不需要整个序列时，而是只想取开头或结尾的几个元素，我们可以用take()或takeLast()或takeLastBuffer()
         *
         */

        Observable<Integer> observableString = Observable.range(0,100);

        observableString.take(20).takeLast(10).takeFirst(num -> num % 5 == 4)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Observable completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Oh,no! Something wrong happened！");
                    }

                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Item is " + item);
                    }
                });

        observableString.takeLastBuffer(10).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(List<Integer> item) {
                System.out.println("Item is " + Arrays.toString(item.toArray()));
            }
        });

    }
}
