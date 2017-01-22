package com.example.filter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 17:17
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterFirstLastTest {

  public static void main(String argv[]) {

    /**
     * first()方法和last()方法很容易弄明白。它们从Observable中只发射第一个元素或者最后一个元素。
     * 这两个都可以传Func1作为参数，：一个可以确定我们感兴趣的第一个或者最后一个的谓词：
     * 
     * 与first()和last()相似的变量有：firstOrDefault()和lastOrDefault().
     * 这两个函数当可观测序列完成时不再发射任何值时用得上。
     * 在这种场景下，如果Observable不再发射任何值时我们可以指定发射一个默认的值
     */

    Observable<Integer> observableString = Observable.range(0,100);

    observableString/*.first()*//*.last()*/.last(number->number%5==0)
            /*firstOrDefault(15,number->number%5==5)*/.subscribe(new Observer<Integer>() {
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
