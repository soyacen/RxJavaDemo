package com.example.transforming;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by Jax on 16/12/14 16:11
 * 邮箱:songyancheng@wanda.cn
 */
public class WindowTest {

  public static void main(String argv[]) {
    /**
     * RxJava的window()函数和buffer()很像，但是它发射的是Observable而不是列表。
     *
     */
    Observable<Long> ob = Observable.interval(1, TimeUnit.MILLISECONDS);

    Observable<Observable<Long>> oob = ob.window(10);
    oob.subscribe(new Action1<Observable<Long>>() {
      @Override
      public void call(Observable<Long> observable) {
        System.out.println("=========observable:" + observable.toString());
        observable.subscribe(new Action1<Long>() {
          @Override
          public void call(Long aLong) {
            System.out.println(aLong.toString());
          }
        });
      }
    });
    while (true);
  }
}
