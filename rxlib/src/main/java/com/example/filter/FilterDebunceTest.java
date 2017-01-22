package com.example.filter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Jax on 16/12/13 17:58
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterDebunceTest {
  public static void main(String argv[]) {

    /**
     * debounce()函数过滤掉由Observable发射的速率过快的数据；
     * 如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
     */

    Observable<Long> observableString = Observable.create(new Observable.OnSubscribe<Long>() {
      @Override
      public void call(Subscriber<? super Long> subscriber) {
        long i = 0;
        while (true) {
          subscriber.onNext(i);
          try {
            if (i++ % 2 == 0) {
              Thread.sleep(12);
            } else {
              Thread.sleep(5);
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });

    observableString
        /* .throttleWithTimeout(10,TimeUnit.MICROSECONDS) */.debounce(10, TimeUnit.MILLISECONDS)
        .subscribe(new Observer<Long>() {
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
