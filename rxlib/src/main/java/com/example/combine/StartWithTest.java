package com.example.combine;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/14 16:49
 * 邮箱:songyancheng@wanda.cn
 */
public class StartWithTest {
  public static void main(String argv[]) {

    Observable.range(1, 10).startWith(-1, -2).subscribe(System.out::println);
    while (true);
  }

}
