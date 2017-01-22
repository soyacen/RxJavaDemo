package com.example.transforming;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/28 11:02
 * 邮箱:songyancheng@wanda.cn
 */

public class ComposeTest {


  public static void main(String argv[]) {
    // 除了 lift() 之外， Observable 还有一个变换方法叫做 compose(Transformer)。它和 lift() 的区别在于， lift()
    // 是针对事件项和事件序列的，而 compose() 是针对 Observable 自身进行变换。
    compose();
  }

  private static void compose() {
    /**
     * 创建一个自定义的操作符，将某种范型的转换为另一种范型，示例如下，将Integer转为String。
     */
    Observable.range(1, 10).compose(new Observable.Transformer<Integer, String>() {
      @Override
      public Observable<String> call(Observable<Integer> observable) {
        return observable.map(num -> "num is " + num);
      }
    }).subscribe(new Observer<String>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened!");
      }

      @Override
      public void onNext(String message) {
        System.out.println(message);
      }
    });
  }

}
