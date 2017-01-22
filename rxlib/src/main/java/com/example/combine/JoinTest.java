package com.example.combine;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Jax on 16/12/14 16:49
 * 邮箱:songyancheng@wanda.cn
 */
public class JoinTest {
  public static void main(String argv[]) {
    /*
     * zip()和merge()方法作用在发射数据的范畴内，在决定如何操作值之前有些场景我们需要考虑时间的。
     * RxJava的join()函数基于时间窗口将两个Observables发射的数据结合在一起。
     * join的效果类似于排列组合，
     * 两个数据源left和right都根据自己的节奏来发射数据,
     * 当left发射时,会和right的定义的时间间隔内发射的所有数据都进行互动,
     * 比如right在这段时间内发射了10个,就会调用10次onNext()。
     * 反之,当right发射数据时候,会和left定义的时间间隔内发射的所有数据也都进行互动。
     * 比如left在这段时间发射了5个数据,也会调用5次onNext()
     */
    Observable<Long> ob1 = Observable.interval(1, TimeUnit.SECONDS);
    Observable<Long> ob2 = Observable.interval(1, TimeUnit.SECONDS);

    join(ob1, ob2);

    // groupJoin(ob1, ob2);



    while (true);
  }

  private static void join(Observable<Long> ob1, Observable<Long> ob2) {
    /**
     * 第二个Observable和源Observable结合。
     * Func1参数：在指定的由时间窗口定义时间间隔内，源Observable发射的数据和从第二个Observable发射的数据相互配合返回的Observable。
     * Func1参数：在指定的由时间窗口定义时间间隔内，第二个Observable发射的数据和从源Observable发射的数据相互配合返回的Observable。
     * Func2参数：定义已发射的数据如何与新发射的数据项相结合。
     */

    ob1.join(ob2, new Func1<Long, Observable<Long>>() {
      @Override
      public Observable<Long> call(Long aLong) {
        return Observable.timer(5, TimeUnit.SECONDS);
      }
    }, new Func1<Long, Observable<Long>>() {
      @Override
      public Observable<Long> call(Long aLong) {
        return Observable.timer(10, TimeUnit.SECONDS);
      }
    }, new Func2<Long, Long, String>() {
      @Override
      public String call(Long aLong, Long aLong2) {
        return "aLong1 = " + aLong + ",aLong2 = " + aLong2;
      }
    }).subscribe(new Observer<String>() {
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

  private static void groupJoin(Observable<Long> ob1, Observable<Long> ob2) {
    /**
     * 除了 resultSelector 以外，其他参数和 join 函数的参数是一样的。
     * 这个 resultSelector 从左边的数据流中获取一个数据并从右边数据流中获取一个 Observable。
     * 这个 Observable 会发射和左边数据配对的所有数据。
     * groupJoin 中的配对和 join 一样是对称的，但是结果可以是不一样的。
     */


    ob1.groupJoin(ob2,
        num -> Observable.timer(5, TimeUnit.SECONDS),
        num -> Observable.timer(10, TimeUnit.SECONDS),
        (num, observable) -> observable.map(num2 -> "aLong2 = " + num + ",aLong2 = " + num2))
        .subscribe(observable1 -> {
          observable1.subscribe(item -> {
            System.out.println("Item is " + item);
          });
        });

/*
 * ob1.groupJoin(ob2,
 * num -> Observable.timer(5, TimeUnit.SECONDS),
 * num -> Observable.timer(10, TimeUnit.SECONDS),
 * (num, observable) -> observable.toList().subscribe(list -> {
 * System.out.println("Item is " + num + list);
 * }))
 * .subscribe();
 */
  }
}
