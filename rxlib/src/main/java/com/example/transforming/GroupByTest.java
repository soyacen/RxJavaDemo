package com.example.transforming;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

/**
 * Created by Jax on 16/12/14 14:45
 * 邮箱:songyancheng@wanda.cn
 */
public class GroupByTest {

  public static void main(String argv[]) {

    /**
     * RxJava提供了一个有用的函数从列表中按照指定的规则：groupBy()来分组元素。
     */

    Observable<Integer> o = Observable.range(0, 100);

    /*
     * 这个函数将源Observable变换成一个发射Observables的新的Observable。
     * 它们中的每一个新的Observable都发射一组指定的数据。
     */

    Observable<GroupedObservable<Integer, Integer>> groupedItems =
        o.groupBy(new Func1<Integer, Integer>() {
          @Override
          public Integer call(Integer integer) {

            return integer % 2;
          }
        });

    /**
     * 现在我们创建了一个新的Observable，groupedItems，它将会发射一个带有GroupedObservable的序列。
     * GroupedObservable是一个特殊的Observable，它源自一个分组的key。
     * 在这个例子中，key就是Integer，代表的意思是偶数归一组,奇数归一组。
     */
    /*groupedItems.subscribe(new Subscriber<GroupedObservable<Integer, Integer>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(GroupedObservable<Integer, Integer> observable) {
          System.out.println("key: " + observable.getKey());
          observable.subscribe(System.out::println);
      }
    });*/

    Observable.concat(groupedItems).subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened！" + e);
      }

      @Override
      public void onNext(Integer integer) {
        System.out.println("item is " + integer);
      }
    });
  }

}
