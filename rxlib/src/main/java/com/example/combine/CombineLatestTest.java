package com.example.combine;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/15 11:30
 * 邮箱:songyancheng@wanda.cn
 */
public class CombineLatestTest {
  public static void main(String argv[]) {
    /*
     * RxJava的combineLatest()函数有点像zip()函数的特殊形式。
     * zip()作用于最近未打包的两个Observables。
     * 相反，combineLatest()作用于最近发射的数据项：
     * 如果Observable1发射了A并且Observable2发射了B和C，
     * combineLatest()将会分组处理AB和AC，
     */

    sync();

    // async();

    while (true);
  }


  private static void sync() {
    Observable<Integer> ob1 = Observable.range(0, 100);

    Observable<Integer> ob2 = Observable.range(200, 100);

    Observable<String> mergeOb =
        Observable.combineLatest(ob1, ob2, (num1, num2) -> num1 + ", " + num2);
    mergeOb.subscribe(new Observer<String>() {
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


  private static void async() {
    Observable.combineLatest(Observable.interval(11, TimeUnit.MILLISECONDS),
        Observable.interval(10, TimeUnit.MILLISECONDS), (item1, item2) -> item1 + ", " + item2)
        .subscribe(new Observer<String>() {
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
