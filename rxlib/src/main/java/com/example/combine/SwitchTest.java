package com.example.combine;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/14 16:49
 * 邮箱:songyancheng@wanda.cn
 */
public class SwitchTest {
  public static void main(String argv[]) {
    /*
     * 有这样一个复杂的场景就是在一个 subscribe-unsubscribe 的序列里
     * 我们能够从一个Observable自动取消订阅来订阅一个新的Observable。
     * RxJava的switch()，正如定义的，将一个发射多个Observables的Observable转换成另一个单独的Observable，
     * 后者发射那些Observables最近发射的数据项。
     *
     * 一个发射多个Observables序列的源Observable，
     * switch()订阅到源Observable然后开始发射由第一个发射的Observable发射的一样的数据。
     * 当源Observable发射一个新的Observable时，
     * switch()立即取消订阅前一个发射数据的Observable（因此打断了从它那里发射的数据流）
     * 然后订阅一个新的Observable，并开始发射它的数据。
     */

      switchOnNext();

    while (true);
  }

  private static Observable<String> craete(int index) {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        for (int i = 0; i < 5; i++) {
          subscriber.onNext("index: " + index + ", i: " + i);
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
              subscriber.onCompleted();
          }
        }
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.newThread());
  }


  private static void switchOnNext() {
    Observable.switchOnNext(Observable.create(new Observable.OnSubscribe<Observable<String>>() {
      @Override
      public void call(Subscriber<? super Observable<String>> subscriber) {
        for (int i = 0; i < 10; i++) {
          subscriber.onNext(craete(i));
          try {
            Thread.sleep(1500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        subscriber.onCompleted();
      }
    })).subscribe(new Observer<String>() {
        @Override
        public void onCompleted() {
            System.out.println("Observable completed");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("Oh,no! Something wrong happened！" + e);
        }

        @Override
        public void onNext(String item) {
            System.out.println("Item is " + item);
        }
    });


  }
}
