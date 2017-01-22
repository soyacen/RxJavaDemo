package com.example.filter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Jax on 16/12/13 17:58
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterTimeOutTest {
  public static void main(String argv[]) {

    distinctUntilChanged();
  }

  private static void distinctUntilChanged() {

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
    observableString.timeout(15, TimeUnit.MILLISECONDS).subscribe(new Observer<Long>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened！" + e.toString());
      }

      @Override
      public void onNext(Long item) {
        System.out.println("*********>Item is " + item);
      }
    });

    while (true);
  }
}
