package com.example.transforming;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 14:06
 * 邮箱:songyancheng@wanda.cn
 */
public class CastTest {
  public static void main(String argv[]) {

    /**
     * RxJava的cast()函数是map()操作符的特殊版本。
     * 它将源Observable中的每一项数据都转换为新的类型，把它变成了不同的Class。
     */


    List<String> items = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      items.add(i + "======");
    }
    Observable<String> observableString = Observable.from(items);

    observableString.cast(CharSequence.class).subscribe(new Observer<CharSequence>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened！" + e);
      }

      @Override
      public void onNext(CharSequence s) {
        System.out.println("Item is " + s);
      }
    });

    while (true);
  }
}
