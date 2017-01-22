package com.example.connectable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observables.ConnectableObservable;

/**
 * Created by Jax on 16/12/19 16:28
 * 邮箱:songyancheng@wanda.cn
 */
public class ConnectableObservableTest {

  public static void main(String[] a) {

    /*
     * Observable<Long>ob=Observable.interval(100, TimeUnit.MILLISECONDS);
     * ob.subscribe(along->{System.out.println("sub1111111"+along);});
     * ob.subscribe(along->{System.out.println("sub2222222"+along);});
     */
    //publish();

    //replay();

    refCount();

    while (true);

  }

  private static void refCount() {
    //ConnectableObservable.refCount( ) — 让一个可连接的Observable表现得像一个普通的Observable
    Observable<Long> ob =
            Observable.interval(100, TimeUnit.MILLISECONDS).take(50).publish().refCount();
    ob.subscribe(along -> {
      System.out.println("sub1111111" + along);
    });
    ob.subscribe(along -> {
      System.out.println("sub2222222" + along);
    });
  }

  private static void replay() {

    //Observable.replay( ) — 确保所有的订阅者看到相同的数据序列，即使它们在Observable开始发射数据之后才订阅
    ConnectableObservable<Long> ob =
            Observable.interval(100, TimeUnit.MILLISECONDS).take(50).replay();
    ob.subscribe(along -> {
      System.out.println("sub1111111" + along);
    });
    ob.subscribe(along -> {
      System.out.println("sub2222222" + along);
    });
    // ConnectableObservable.connect( ) — 指示一个可连接的Observable开始发射数据

    Observable.timer(3, TimeUnit.SECONDS).subscribe(num -> {
      ob.connect();
    });


    Observable.timer(4,TimeUnit.SECONDS).subscribe(num->{
      ob.subscribe(along -> {
        System.out.println("sub3333333" + along);
      });
    });
  }

  private static void publish() {
    // Observable.publish( ) — 将一个Observable转换为一个可连接的Observable
    ConnectableObservable<Long> ob =
        Observable.interval(100, TimeUnit.MILLISECONDS).take(50).publish();
    ob.subscribe(along -> {
      System.out.println("sub1111111" + along);
    });
    ob.subscribe(along -> {
      System.out.println("sub2222222" + along);
    });
    // ConnectableObservable.connect( ) — 指示一个可连接的Observable开始发射数据

    Observable.timer(3, TimeUnit.SECONDS).subscribe(num -> {
      ob.connect();
    });


    Observable.timer(4,TimeUnit.SECONDS).subscribe(num->{
      ob.subscribe(along -> {
        System.out.println("sub3333333" + along);
      });
    });
  }


}
