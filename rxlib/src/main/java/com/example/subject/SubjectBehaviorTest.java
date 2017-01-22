package com.example.subject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by Jax on 16/12/13 14:06
 * 邮箱:songyancheng@wanda.cn
 */
public class SubjectBehaviorTest {
  public static void main(String argv[]) {
    /**
     * 简单的说，BehaviorSubject会首先向他的订阅者发送初始值,
     * 然后正常发送订阅后的数据流。
     * 
     * 在这个短例子中，我们创建了一个能发射整形(Integer)的BehaviorSubject。
     * 由于每当Observes订阅它时就会发射最新的数据，所以它需要一个初始值。
     */
    BehaviorSubject<String> stringBehaviorSubject = BehaviorSubject.create("start");

    stringBehaviorSubject.subscribe(new Observer<String>() {
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
        System.out.println(message);
      }
    });

    stringBehaviorSubject.onNext("one");
    stringBehaviorSubject.onNext("two");
    stringBehaviorSubject.onNext("three");
    stringBehaviorSubject.onNext("four");
    stringBehaviorSubject.onCompleted();

  }

}
