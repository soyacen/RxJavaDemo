package com.example.create;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 16:57
 * 邮箱:songyancheng@wanda.cn
 */
public class RangTest {
  public static void main(String argv[]) {

    /**
     * 你需要从一个指定的数字X开始发射N个数字吗？你可以用range:
     * 
     * range()函数用两个数字作为参数：第一个是起始点，第二个是我们想发射数字的个数。
     */

    Observable.range(1, 10).subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened!");
      }

      @Override
      public void onNext(Integer message) {
        System.out.println(message);
      }
    });


  }
}
