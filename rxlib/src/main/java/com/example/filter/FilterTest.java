package com.example.filter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Jax on 16/12/13 17:17
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterTest {

  public static void main(String argv[]) {

    /**
     * RxJava让我们使用filter()方法来过滤我们观测序列中不想要的值，
     */

    Observable<Integer> observableString = Observable.range(1,100);
    observableString.filter((number) -> number % 2 == 0).subscribe(new Observer<Integer>() {
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
