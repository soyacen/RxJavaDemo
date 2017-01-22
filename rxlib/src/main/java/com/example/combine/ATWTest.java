package com.example.combine;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.joins.Pattern2;
import rx.joins.Plan0;
import rx.joins.Plan1;
import rx.observables.JoinObservable;

/**
 * Created by Jax on 16/12/15 11:30
 * 邮箱:songyancheng@wanda.cn
 */
public class ATWTest {
  public static void main(String argv[]) {
    /*
     * 还有一些zip()满足不了的场景。
     * 如复杂的架构，或者是仅仅为了个人爱好，
     * 你可以使用And/Then/When解决方案。
     * 它们在RxJava的joins包下，使用Pattern和Plan作为中介，将发射的数据集合并到一起。
     */

     sync();

    //async();

    while (true);
  }


  private static void sync() {
    Observable<Integer> ob1 = Observable.range(0, 100);

    Observable<Integer> ob2 = Observable.range(200, 200);

    Observable<Integer> ob3 = Observable.range(50, 50);

    Observable<Integer> ob4 = Observable.range(1000, 150);

    Pattern2<Integer, Integer> pattern2 = JoinObservable.from(ob1).and(ob2);

    Plan0<String> plan0 = pattern2.then((num1, num2) -> num1 + " --- " + num2);

    Plan0<String> plan1 =
        JoinObservable.from(ob3).and(ob4).then((num1, num2) -> num1 + " === " + num2);


    JoinObservable.when(plan0, plan1).toObservable().subscribe(new Observer<String>() {
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
    Observable<Long> ob1 = Observable.interval(11, TimeUnit.MILLISECONDS);

    Observable<Long> ob2 = Observable.interval(10, TimeUnit.MILLISECONDS);

    Observable<Long> ob3 = Observable.interval(14, TimeUnit.MILLISECONDS);

    Plan0<String> plan = JoinObservable.from(ob1).and(ob2)
        .then((num1, num2) -> num1 + " + " + num2 + " = " + Math.addExact(num1, num2));

    Plan0<String> plan2 = JoinObservable.from(ob3).and(ob2)
            .then((num1, num2) -> num1 + " - " + num2 + " = " + Math.subtractExact(num1, num2));


    JoinObservable.when(plan,plan2).toObservable().subscribe(new Observer<String>() {
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
