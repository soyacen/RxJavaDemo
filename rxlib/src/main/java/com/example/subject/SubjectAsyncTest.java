package com.example.subject;

import rx.Observer;
import rx.subjects.AsyncSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by Jax on 16/12/13 14:06
 * 邮箱:songyancheng@wanda.cn
 */
public class SubjectAsyncTest {
  public static void main(String argv[]) {

    /**
     * 当Observable完成时AsyncSubject只会发布最后一个数据给已经订阅的每一个观察者。
     */
    AsyncSubject<String> subject = AsyncSubject.create();

    subject.subscribe(new Observer<String>() {
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

    subject.onNext("one");
    subject.onNext("two");
    subject.onNext("three");
    subject.onCompleted();
  }

}
