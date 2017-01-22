package com.example.create;

import rx.Observable;

/**
 * Created by Jax on 16/12/13 15:20
 * 邮箱:songyancheng@wanda.cn
 */
public class DeferTest {
  public static void main(String argv[]) {
    /**
     * 这样一个场景，你想在这声明一个Observable但是你又想推迟这个Observable的创建直到观察者订阅时。
     *
     */
    Observable<Integer> o = null;
     //o = new DeferTest().newMethod();

//     o = new DeferTest().newSlowBlockingMethod();

     o = new DeferTest().newDeferMethod();
     o.subscribe();
  }


  private Integer oldMethod() {
    System.out.println("oldMethod");
    return 0;
  }

  public Observable<Integer> newMethod() {
    return Observable.just(oldMethod());
  }

  /**
   * 上面的例子中如果oldMethod()足够快是没有什么问题的，但是如果很慢呢？
   * 调用oldMethod()将会阻塞住他所在的线程。
   */

  private Integer slowBlockingMethod() {
    System.out.println("slowBlockingMethod");
    int i = 0;
    while (true) {
      i++;
      System.out.println("slowBlockingMethod" + i);
      if (i == 100000000) {
        break;
      }
    }
    return i;
  }

  public Observable<Integer> newSlowBlockingMethod() {
    return Observable.just(slowBlockingMethod());
  }

  /**
   * 现在，newDeferMethod()的调用不会阻塞了，除非你订阅返回的observable对象。
   *
   * @return
   */
  public Observable<Integer> newDeferMethod() {
    return Observable.defer(this::newSlowBlockingMethod);
  }

}
