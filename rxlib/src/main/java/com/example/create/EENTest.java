package com.example.create;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class EENTest {

    public static void main(String argv[]) {
        /**
         * 当我们需要一个Observable毫无理由的不再发射数据正常结束时，我们可以使用empty()。
         */
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened!");
            }

            @Override
            public void onNext(Object message) {
                System.out.println(message);
            }
        });

        /**
         * 我们可以使用never()创建一个不发射数据并且也永远不会结束的Observable。
         */
        Observable.never().subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened!");
            }

            @Override
            public void onNext(Object message) {
                System.out.println(message);
            }
        });
        /**
         * 我们也可以使用throw()创建一个不发射数据并且以错误结束的Observable。
         */
        Observable.error(new NullPointerException()).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened!" + e.getMessage());
            }

            @Override
            public void onNext(Object message) {
                System.out.println(message);
            }
        });


    }

}
