package com.example.create;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

public class CreateTest {

  public static void main(String argv[]) {
    /**
     * create()方法创建一个Observable。它
     * 需要一个OnSubscribe对象,这个对象继承Action1,
     * 当观察者订阅我们的Observable时，它作为一个参数传入并执行call()函数。
     *
     * Observable通过使用subscriber变量并根据条件调用它的方法来和观察者通信。
     * 
     * 我们创建一个新的Observable<Integer>,它执行了5个元素的for循环，一个接一个的发射他们，最后完成。
     * 
     * 另一方面，我们订阅了Observable，返回一个Subscription。
     * 一旦我们订阅了，我们就开始接受整数，并一个接一个的打印出它们。
     */
    Observable<Integer> observableString = Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        for (int i = 0; i < 5; i++) {
          subscriber.onNext(i);
        }
        subscriber.onCompleted();
      }
    });

    Subscription subscription = observableString.subscribe(new Observer<Integer>() {
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
