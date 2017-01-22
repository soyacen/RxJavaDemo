package com.example.string;

import com.google.common.base.Utf8;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observables.StringObservable;

/**
 * Created by Jax on 16/12/20 16:11
 * 邮箱:songyancheng@wanda.cn
 */
public class StringObservableTest {

  public static void main(String[] a) {
    // byLine();

    // decode();

    // encode();

    // from();

    // join();

    // split();

    // stringConcat();

    // using();

    while (true);


  }

  private static void using() {
    /**
     * Helps in creating an Observable that automatically calls {@link Closeable#close()} on
     * completion, error or unsubscribe.
     */
    StringObservable.using(() -> new FileReader("/Users/Jax/Desktop/android进阶"),
        (reader) -> StringObservable.from(reader)).subscribe(System.out::println);
  }

  private static void stringConcat() {
    StringObservable
        .stringConcat(
            Observable.just("stringConcat1", "stringConcat2", "stringConcat3", "stringConcat4"))
        .subscribe(System.out::println);
  }

  private static void split() {
    StringObservable.split(Observable.just("split-split-split-split"), "-")
        .subscribe(System.out::println);
  }

  private static void join() {
    StringObservable.join(Observable.just("join", "join", "join", "join"), "-")
        .subscribe(System.out::println);
  }

  private static void from() {
    try {
      StringObservable
          .decode(StringObservable.from(new FileInputStream("/Users/Jax/Desktop/android进阶")),
              Charset.defaultCharset())
          .subscribe(System.out::println);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void encode() {
    StringObservable.encode(Observable.just("encode"), Charset.defaultCharset())
        .subscribe(System.out::println);
  }

  private static void decode() {
    StringObservable.decode(Observable.just("decode".getBytes()), Charset.defaultCharset())
        .subscribe(System.out::println);

  }

  private static void byLine() {
    /**
     * converts an Observable of Strings into an Observable of Lines by treating the source sequence
     * as a stream and splitting it on line-endings
     */

    StringObservable
        .byLine(Observable.interval(100, TimeUnit.MILLISECONDS).map(i -> {
          if (i % 10 == 9)
            return String.valueOf(i) + java.security.AccessController
                .doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
          else
            return String.valueOf(i) + " ";
        }))
        .subscribe(System.out::println);
  }

}
