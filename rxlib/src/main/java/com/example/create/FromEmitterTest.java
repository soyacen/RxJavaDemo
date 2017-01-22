package com.example.create;

import java.util.ArrayList;
import java.util.List;

import rx.Emitter;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Cancellable;

public class FromEmitterTest {

    private static Subscription subscription;

    public static void main(String argv[]) {

        /**
         * create safe, backpressure-enabled, unsubscription-supporting Observable via a function and
         * push events.
         *
         * 在合适的地方调用 onNext(), onCompleted(), 和 onError()
         * 如果需要清理资源，则使用 setCancellation()
         * 选择正确的 BackPressureMode 策略
         */
        List<Integer> items = new ArrayList<>();
        items.add(100);
        items.add(222);
        items.add(3333);
        Observable<Integer> observableString = Observable.fromEmitter(new Action1<Emitter<Integer>>() {
            @Override
            public void call(Emitter<Integer> emitter) {
                emitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        System.out.println("======");
                    }
                });
                //emitter.setSubscription(subscription);
                for (int item : items) {

                    emitter.onNext(item);
                }
                emitter.onCompleted();
            }
        }, Emitter.BackpressureMode.ERROR);
        subscription = observableString.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened！" + e.toString());
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("Item is " + item);
            }
        });
        subscription.unsubscribe();
    }

}
