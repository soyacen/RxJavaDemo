package com.example.create;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 16:57
 * 邮箱:songyancheng@wanda.cn
 */
public class IntervalTest {
  public static void main(String argv[]) {

    /**
     * 使用interval做周期性操作。当有“每隔xx秒后执行yy操作”类似的需求的时候，想到使用interval
     * 
     */

    Observable.interval(1, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened!");
      }

      @Override
      public void onNext(Long message) {
        System.out.println("Observer" + Thread.currentThread().getId());
        System.out.println(message);
      }
    });
    System.out.println("main:" + Thread.currentThread().getId());
    while (true);
  }
}
