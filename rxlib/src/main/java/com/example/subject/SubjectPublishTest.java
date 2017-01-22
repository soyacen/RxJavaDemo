package com.example.subject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 * Created by Jax on 16/12/13 14:06
 * 邮箱:songyancheng@wanda.cn
 */
public class SubjectPublishTest {
  public static void main(String argv[]) {

     //simple();

    complex();
  }

  /**
   * 我们创建了一个PublishSubject，用create()方法发射一个String值，
   * 然后我们订阅了PublishSubject。此时，没有数据要发送，
   * 因此我们的观察者只能等待，没有阻塞线程，也没有消耗资源。
   * 就在这随时准备从subject接收值，如果subject没有发射值那么我们的观察者就会一直在等待。
   * 再次声明的是，无需担心：观察者知道在每个场景中该做什么，
   * 我们不用担心什么时候是因为它是响应式的：系统会响应。
   * 我们并不关心它什么时候响应。我们只关心它响应时该做什么。
   *
   * 最后一行代码展示了手动发射字符串“发个信息”,它触发了观察者的onNext()方法，让我们在控制台打印出“发个信息”信息。
   *
   *
   */
  private static void simple() {


    PublishSubject<String> stringPublishSubject = PublishSubject.create();
    Subscription subscription = stringPublishSubject.subscribe(new Observer<String>() {
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
    stringPublishSubject.onNext("发个信息");
    stringPublishSubject.onNext("发个信息");
    stringPublishSubject.onNext("发个信息");
    stringPublishSubject.onNext("发个信息");
    stringPublishSubject.onNext("发个信息");
  }

  private static void complex() {
    final PublishSubject<Integer> subject = PublishSubject.create();

    subject.subscribe(new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("PublishSubject Completed");
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(Integer aBoolean) {
        System.out.println("PublishSubject Integer: "+aBoolean);
      }
    });

    Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        for (int i = 0; i < 5; i++) {
          subscriber.onNext(i);
        }
        subscriber.onCompleted();
      }
    }).subscribe(subject);

  }


}
