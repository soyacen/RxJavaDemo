package com.example.filter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 17:17
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterSkipTest {

  public static void main(String argv[]) {

    /**
     * skip()和skipLast()函数与take()和takeLast()相对应。
     * 它们用整数N作参数，从本质上来说，它们不让Observable发射前N个或者后N个值。
     * 如果我们知道一个序列以没有太多用的“可控”元素开头或结尾时我们可以使用它。
     *
     */

    Observable<Integer> observableString = Observable.range(0,100);

    observableString.skip(10).skipLast(5).subscribe(new Observer<Integer>() {
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
