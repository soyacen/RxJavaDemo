package com.example.schedulers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/16 08:57
 * 邮箱:songyancheng@wanda.cn
 */
public class IOScheduler {
  public static void main(String a[]) {
    /*
     * Schedulers.io()
     * 这个调度器时用于I/O操作。它基于根据需要，增长或缩减来自适应的线程池。
     * 我们将使用它来修复我们之前看到的StrictMode违规做法。由于它专用于I/O操作，
     * 所以并不是RxJava的默认方法；正确的使用它是由开发者决定的。
     * 重点需要注意的是线程池是无限制的，大量的I/O调度操作将创建许多个线程并占用内存。
     * 一如既往的是，我们需要在性能和简捷两者之间找到一个有效的平衡点。
     */
    System.out.println("main thread : " + Thread.currentThread().getId());
    Observable.create(subscriber -> {
      System.out.println("Observable thread : " + Thread.currentThread().getId());
      BufferedReader br = null;
      try {
        URL url = new URL("http://www.ffan.com/new/index.html");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while (null != (line = br.readLine())) {
          subscriber.onNext(line);
        }
        subscriber.onCompleted();
      } catch (IOException e) {
        subscriber.onError(e);
      } finally {
        if (br != null) {
          try {
            br.close();
          } catch (IOException e) {
            subscriber.onError(e);
          }
        }
      }
    }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread())
        .subscribe(msg -> {
          System.out.println("observer thread : " + Thread.currentThread().getId());
          System.out.println(msg);
        });

    while (true);
  }


}
