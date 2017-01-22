package com.example.combine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/14 16:49
 * 邮箱:songyancheng@wanda.cn
 */
public class MergeTest {
  public static void main(String argv[]) {
    /*
     * 在”异步的世界“中经常会创建这样的场景，我们有多个来源但是又只想有一个结果：多输入，单输出。RxJava的merge()
     * 方法将帮助你把两个甚至更多的Observables合并到他们发射的数据项里。
     * 注意如果同步合并Observable，它们将连接在一起并且不会交叉。
     */

    //sync();

     //async();

      mergeDelayError();

    while (true);
  }

  private static void sync() {
    Observable<Integer> ob1 = Observable.range(0, 100);

    Observable<Integer> ob2 = Observable.range(50, 100);

    Observable<Integer> mergeOb = Observable.merge(ob1, ob2);
    mergeOb.subscribe(new Observer<Integer>() {
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
        System.out.println("Item is -> " + item);
      }
    });
  }


  private static void async() {
    Observable.merge(Observable.interval(10, TimeUnit.MILLISECONDS),
        Observable.interval(100, TimeUnit.MILLISECONDS)).subscribe(new Observer<Long>() {
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

  private static void mergeDelayError() {
      Observable.merge/*mergeDelayError*/(Observable.interval(5, TimeUnit.MILLISECONDS),
              Observable.create(new Observable.OnSubscribe<Long>() {
                  @Override
                  public void call(Subscriber<? super Long> subscriber) {
                      long i=0;
                      while (true){
                          System.out.println(Thread.currentThread().getId());
                          subscriber.onNext(i++);
                          try {
                              Thread.sleep(5);
                          } catch (InterruptedException e) {
                          }
                          if(i==100){
                              subscriber.onError(new NumberFormatException());
                              break;
                          }
                      }
                  }
              }).subscribeOn(Schedulers.newThread())).subscribe(new Observer<Long>() {
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
              System.out.println(Thread.currentThread().getId()+" Item is " + item);
          }
      });


  }
}
