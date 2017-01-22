package com.example.filter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 17:17
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterDistinctTest {

  public static void main(String argv[]) {
    //distinct();

    distinctUntilChanged();
  }

  private static void distinct() {
    /**
     * 去重
     */

    List<Integer> items = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      items.add(i % 5);
    }
    Observable<Integer> observableString = Observable.from(items);

    observableString.distinct().subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened！");
      }

      @Override
      public void onNext(Integer item) {
        System.out.println("Item is " + item);
      }
    });
  }

  private static void distinctUntilChanged() {
    /**
     * 如果在一个可观测序列发射一个不同于之前的一个新值时让我们得到通知这时候该怎么做？
     * 我们猜想一下我们观测的温度传感器，每秒发射的室内温度：
     * 21°...21°...21°...21°...22°...
     * 每次我们获得一个新值，我们都会更新当前正在显示的温度。
     * 我们出于系统资源保护并不想在每次值一样时更新数据。
     * 我们想忽略掉重复的值并且在温度确实改变时才想得到通知。
     * ditinctUntilChanged()过滤函数能做到这一点。
     * 它能轻易的忽略掉所有的重复并且只发射出新的值。
     */
    Observable.just(1, 1, 2, 3, 1, 2, 2, 3, 4).distinctUntilChanged()
        .subscribe(new Observer<Integer>() {
          @Override
          public void onCompleted() {
            System.out.println("Observable completed");
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("Oh,no! Something wrong happened！");
          }

          @Override
          public void onNext(Integer item) {
            System.out.println("Item is " + item);
          }
        });
  }
}
