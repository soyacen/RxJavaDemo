package com.example.combine;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/14 16:49
 * 邮箱:songyancheng@wanda.cn
 */
public class ZipTest {
  public static void main(String argv[]) {
    /*
     * 在一种新的可能场景中处理多个数据来源时会带来：多从个Observables接收数据，
     * 处理它们，然后将它们合并成一个新的可观测序列来使用。RxJava有一个特殊的方法可以完成：zip()
     * 合并两个或者多个Observables发射出的数据项，根据指定的函数Func*变换它们，并发射一个新值。
     */

    sync();

    // async();

    while (true);
  }


  private static void sync() {
    Observable<Integer> ob1 = Observable.range(0, 100);

    Observable<Integer> ob2 = Observable.range(200, 50);

    Observable<String> mergeOb = Observable.zip(ob1, ob2, (num1, num2) -> num1 + ", " + num2);
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
    Observable.zip(Observable.interval(10, TimeUnit.MILLISECONDS),
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
