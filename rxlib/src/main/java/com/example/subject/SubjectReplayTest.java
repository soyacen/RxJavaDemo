package com.example.subject;

import rx.Observer;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by Jax on 16/12/13 14:06
 * 邮箱:songyancheng@wanda.cn
 */
public class SubjectReplayTest {
  public static void main(String argv[]) {

    /**
     * ReplaySubject会缓存它所订阅的所有数据,向任意一个订阅它的观察者重发:
     */
    ReplaySubject<String> stringReplaySubject = ReplaySubject.create();


    stringReplaySubject.onNext("one");
    stringReplaySubject.onNext("two");
    stringReplaySubject.onNext("three");
    stringReplaySubject.onNext("four");
    stringReplaySubject.onCompleted();

    stringReplaySubject.subscribe(new Observer<String>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no!Something wrong happened!");
      }

      @Override
      public void onNext(String message) {
        System.out.println(1+message);
      }
    });

    stringReplaySubject.subscribe(new Observer<String>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no!Something wrong happened!");
      }

      @Override
      public void onNext(String message) {
        System.out.println(2+message);
      }
    });
  }

}
