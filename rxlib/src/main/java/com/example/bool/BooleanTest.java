package com.example.bool;

import rx.Observable;

/**
 * Created by Jax on 16/12/19 16:11
 * 邮箱:songyancheng@wanda.cn
 */
public class BooleanTest {

  public static void main(String[] a) {

    // all();

    // contains();

    // exists();

    // isEmpty();

    sequenceEqual();

  }

  private static void sequenceEqual() {
    /**
     * 判断两个Observables发射的序列是否相等
     */
    Observable.sequenceEqual(Observable.just("A"), Observable.just("AA"),
        (str1, str2) -> str1.length() == str2.length()).subscribe(System.out::println);
  }

  private static void isEmpty() {
    Observable.just("A").isEmpty().subscribe(System.out::println);

  }

  private static void exists() {
    Observable.just("A", "B").exists(str -> str.equals("A")).subscribe(System.out::println);
  }

  private static void contains() {
    /**
     * 判断Observable是否会发射一个指定的值
     */
    Observable.just("A", "B").contains("A").subscribe(System.out::println);
  }

  private static void all() {
    /**
     * 判断是否所有的数据项都满足某个条件
     */
    Observable.just("A", "B").all(str -> str.equals("A")).subscribe(System.out::println);
  }

}
