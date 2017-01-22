package com.example.create;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

public class FromTest {

  public static void main(String argv[]) {

    /**
     * from()创建符可以从一个列表/数组来创建Observable,
     * 并一个接一个的从列表/数组中发射出来每一个对象，
     * 或者也可以从Java Future类来创建Observable，
     * 并发射Future对象的.get()方法返回的结果值。
     * 传入Future作为参数时，我们可以指定一个超时的值。
     * Observable将等待来自Future的结果；
     * 如果在超时之前仍然没有结果返回，Observable将会触发onError()方法通知观察者有错误发生了。
     */

    List<Integer> items = new ArrayList<>();
    items.add(100);
    items.add(222);
    items.add(3333);
    Observable<Integer> observableString = Observable.from(items);

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
