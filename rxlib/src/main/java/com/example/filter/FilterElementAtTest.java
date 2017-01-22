package com.example.filter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 17:17
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterElementAtTest {

  public static void main(String argv[]) {

    /**
     * 如果我们只想要可观测序列发射的第五个元素该怎么办？elementAt()函数仅从一个序列中发射第n个元素然后就完成了。
     * 
     * 如果我们想查找第五个元素但是可观测序列只有三个元素可供发射时该怎么办？我们可以使用elementAtOrDefault()。
     */

    Observable<Integer> observableString = Observable.range(0,100);

    elementAt(observableString);
    //ignoreElements(observableString);


  }

  private static void ignoreElements(Observable<Integer> observableString) {
    /**
     * ignoreElements()丢弃所有发射出来的数据。
     */
    observableString.ignoreElements().subscribe(new Observer<Integer>() {
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

  private static void elementAt(Observable<Integer> observableString) {
    observableString.elementAt(10)/*.elementAtOrDefault(100,111)*/.subscribe(new Observer<Integer>() {
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
