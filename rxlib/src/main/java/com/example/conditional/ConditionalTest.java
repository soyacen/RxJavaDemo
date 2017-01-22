package com.example.conditional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func0;
import rx.operators.OperatorIfThen;
import rx.operators.OperatorSwitchCase;
import rx.operators.OperatorWhileDoWhile;

/**
 * Created by Jax on 16/12/19 11:19
 * 邮箱:songyancheng@wanda.cn
 */
public class ConditionalTest {

  public static void main(String[] a) {
    // amb();

    // defaultIfEmpty();

    // skipUntil();

    // skipWhile();

    // takeUntil();

    takeWhile();

    // doWhile();

    whileDo();

    // ifThen();

    // switchCase();

    while (true);
  }

  private static void takeWhile() {
    /**
     * 发射原始Observable的数据，直到一个特定的条件为真，然后跳过剩余的数据
     */
    Observable.interval(1000, TimeUnit.MILLISECONDS).takeWhile(aLong -> aLong < 5)
        .subscribe(System.out::println);
  }

  private static void takeUntil() {
    /**
     * 发射来自原始Observable的数据，直到第二个Observable发射了一个数据或一个通知
     */
    Observable.interval(1000, TimeUnit.MILLISECONDS)
        .takeUntil(Observable.timer(3, TimeUnit.SECONDS)).subscribe(System.out::println);

    Observable.interval(1000, TimeUnit.MILLISECONDS).takeUntil(aLong -> aLong > 3)
        .subscribe(System.out::println);

  }

  private static void skipUntil() {
    /**
     * 丢弃原始Observable发射的数据，直到第二个Observable发射了一个数据，然后发射原始Observable的剩余数据
     */
    Observable.interval(1000, TimeUnit.MILLISECONDS)
        .skipUntil(Observable.timer(3000, TimeUnit.MILLISECONDS)).subscribe(System.out::println);
  }

  private static void skipWhile() {
    /**
     * 丢弃原始Observable发射的数据，直到一个特定的条件为假，然后发射原始Observable剩余的数据
     */
    Observable.interval(1000, TimeUnit.MILLISECONDS).skipWhile(num -> num < 5)
        .subscribe(System.out::println);
  }

  private static void amb() {
    /**
     * 给定多个Observable，只让第一个发射数据的Observable发射全部数据
     */
    Observable<String> s =
        Observable.amb(Observable.just("A", "B", "C"), Observable.just("a", "b"));
    s.subscribe(System.out::println);
  }

  private static void defaultIfEmpty() {
    /**
     * 射来自原始Observable的数据，如果原始Observable没有发射数据，就发射一个默认数据
     */
    Observable.empty().defaultIfEmpty("D").subscribe(System.out::println);

  }

  private static void doWhile() {
    /**
     * 发射原始Observable的数据序列，然后重复发射这个序列直到不满足这个条件为止
     */
    Observable
        .create(new OperatorWhileDoWhile<String>(Observable.just("A"), () -> true, () -> false))
        .subscribe(System.out::println);
  }

  private static void whileDo() {
    Observable.create(new OperatorWhileDoWhile<Long>(Observable.interval(1, TimeUnit.SECONDS),
        () -> true, () -> false))
        .subscribe(System.out::println);
  }

  private static void ifThen() {
    /**
     * 只有当某个条件为真时才发射原始Observable的数据序列，否则发射一个空的或默认的序列
     */
    Observable
        .create(new OperatorIfThen<Object>(() -> false, Observable.just("A"), Observable.just("B")))
        .subscribe(System.out::println);
  }

  private static void switchCase() {
    /**
     * 基于一个计算结果，发射一个指定Observable的数据序列
     */
    Map<String, Observable<String>> map = new HashMap<>();
    map.put("A", Observable.just("A"));
    map.put("B", Observable.just("B"));
    Observable.create(new OperatorSwitchCase<>(() -> "C", map, Observable.just("default")))
        .subscribe(System.out::println);
  }
}
