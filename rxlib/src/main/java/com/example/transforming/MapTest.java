package com.example.transforming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.AsyncSubject;

/**
 * Created by Jax on 16/12/13 14:06
 * 邮箱:songyancheng@wanda.cn
 */
public class MapTest {
  public static void main(String argv[]) {

    /**
     * RxJava提供了几个mapping函数：map(),flatMap(),concatMap(),flatMapIterable()以及switchMap().
     * 所有这些函数都作用于一个可观测序列，然后变换它发射的值，最后用一种新的形式返回它们。
     */

    // map();

    // flatMap();

    // concatMap();

    // flatMapIterable();

     switchMap();

    while (true);
  }



  /**
   * RxJava的map函数接收一个指定的Func对象然后将它应用到每一个由Observable发射的值上。
   */
  private static void map() {

    Observable<Integer> observableString = Observable.range(0, 100);

    observableString.map(num -> num * 10).subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened！" + e);
      }

      @Override
      public void onNext(Integer item) {
        System.out.println("Item is " + item);
      }
    });

  }

  /**
   * flatMap() 和 map() 有一个相同点：它也是把传入的参数转化之后返回另一个对象。
   * 但需要注意，和 map() 不同的是， flatMap() 中返回的是个 Observable 对象，
   * 并且这个 Observable 对象并不是被直接发送到了 Subscriber 的回调方法中。
   * flatMap() 的原理是这样的：
   * 1. 使用传入的事件对象创建一个 Observable 对象；
   * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
   * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，
   * 而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法。
   * 这三个步骤，把事件拆成了两级，通过一组新创建的 Observable 将初始的对象『铺平』之后通过统一路径分发了下去。
   * 而这个『铺平』就是 flatMap() 所谓的 flat。
   *
   *
   * 在复杂的场景中，我们有一个这样的Observable：它发射一个数据序列，这些数据本身也可以发射Observable。
   * RxJava的flatMap()函数提供一种铺平序列的方式，然后合并这些Observables发射的数据，
   * 最后将合并后的结果作为最终的Observable。
   *
   * 重要的一点提示是关于合并部分：它允许交叉。
   * 这意味着flatMap()不能够保证在最终生成的Observable中源Observables确切的发射顺序。
   */
  private static void flatMap() {

    Observable<Integer> o1 = Observable.range(0, 100);

    o1.flatMap(num -> Observable.range(0, 10).map(offset -> offset + num * 10))
        .subscribe(new Observer<Integer>() {
          @Override
          public void onCompleted() {
            System.out.println("Observable completed");
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("Oh,no! Something wrong happened！" + e);
          }

          @Override
          public void onNext(Integer item) {
            System.out.println("Item is " + item);
          }
        });

  }

  /**
   * RxJava的concatMap()函数解决了flatMap()的交叉问题，
   * 提供了一种能够把发射的值连续在一起的铺平函数，而不是合并它们
   */
  private static void concatMap() {

    Observable<Long> o1 = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

    o1.concatMap(num -> Observable.interval(10, TimeUnit.MILLISECONDS).take(100))
        .subscribe(new Observer<Long>() {
          @Override
          public void onCompleted() {
            System.out.println("Observable completed");
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("Oh,no! Something wrong happened！" + e);
          }

          @Override
          public void onNext(Long item) {
            System.out.println("Item is " + item);
          }
        });
    while (true);
  }

  /**
   * flatMapInterable()和flatMap()很像。
   * 仅有的本质不同是它将源数据两两结成对并生成Iterable，
   * 而不是原始数据项和生成的Observables。
   */
  private static void flatMapIterable() {
    Observable<Integer> o1 = Observable.range(0, 100);

    o1.flatMapIterable(num -> {
      List<Long> items = new ArrayList<>();
      for (long i = 0; i < 10; i++) {
        items.add(i + num * 10);
      }
      return items;
    }).subscribe(new Observer<Long>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened！" + e);
      }

      @Override
      public void onNext(Long item) {
        System.out.println("Item is " + item);
      }
    });
  }

  /**
   * switchMap()和flatMap()很像，
   * 除了一点：每当源Observable发射一个新的数据项（Observable）时，
   * 它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个。
   */
  private static void switchMap() {
    Observable<Long> o1 = Observable.interval(10, TimeUnit.MILLISECONDS);

    o1.switchMap(num -> Observable.interval(5, TimeUnit.MILLISECONDS))
        .subscribe(new Observer<Long>() {
          @Override
          public void onCompleted() {
            System.out.println("Observable completed");
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("Oh,no! Something wrong happened！" + e);
          }

          @Override
          public void onNext(Long item) {
            System.out.println("Item is " + item);
          }
        });
  }

}
