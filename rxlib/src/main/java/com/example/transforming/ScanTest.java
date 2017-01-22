package com.example.transforming;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/14 14:23
 * 邮箱:songyancheng@wanda.cn
 */
public class ScanTest {
  public static void main(String argv[]) {
    /**
     * RxJava的scan()函数可以看做是一个累积函数。
     * scan()函数对原始Observable发射的每一项数据都应用一个函数，
     * 计算出函数的结果值，并将该值填充回可观测序列，
     * 等待和下一次发射的数据一起使用。
     *
     *
     * Func2 call(T1 t1, T2 t2);第一个参数是上次发射的值,第二个参数是这次原始Observable发射的值,返回值是实际要发射的值
     *
     */

    Observable<Integer> observableString = Observable.range(0,100);

    observableString.scan(/*-1,*/(sum, num2) -> {
      System.out.println(sum + "," + num2);
      sum += num2;
      return sum;
    }).subscribe(new Observer<Integer>() {
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
}
