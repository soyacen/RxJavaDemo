package com.example.transforming;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by Jax on 16/12/14 14:23
 * 邮箱:songyancheng@wanda.cn
 */
public class BufferTest {
  public static void main(String argv[]) {
    /**
     * RxJava中的buffer()函数将源Observable变换一个新的Observable，
     * 这个新的Observable每次发射一组列表值而不是一个一个发射。
     * 
     * buffer()函数有几种变体。
     * 其中有一个是允许你指定一个skip值：此后每skip项数据，然后又用count项数据填充缓冲区。
     * 
     * buffer()带一个timespan的参数，会创建一个每隔timespan时间段就会发射一个列表的Observable。
     *
     * Buffer(bufferClosingSelector)
     * 源会Observable缓存数据,当bufferClosingSelector产生第二个Observable,
     * 当第二个Observable发射数据时候,源Observable把缓存的数据一次性发出。直到两个Observable不在无数据发出时终止。
     */


    Observable<Long> ob = Observable.interval(1, TimeUnit.MILLISECONDS);

    ob.buffer(10)/*.buffer(10,5)*//*.buffer(500,TimeUnit.MILLISECONDS,10)*//*.buffer(
        new Func0<Observable<Long>>() {
          @Override
          public Observable<Long> call() {
            return Observable.create(new Observable.OnSubscribe<Long>() {
              @Override
              public void call(Subscriber<? super Long> subscriber) {

                long i = 0;
                while (true) {
                  if (i > 10) {
                    break;
                  }
                  System.out.println("==========" + i);
                  try {
                    Thread.sleep(10);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                  subscriber.onNext(i++);

                }
                subscriber.onCompleted();
              }
            }).subscribeOn(Schedulers.newThread());
            // return Observable.interval(1000,TimeUnit.MILLISECONDS);
          }
        })*/.subscribe(new Observer<List<Long>>() {
          @Override
          public void onCompleted() {
            System.out.println("Observable completed");
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("Oh,no! Something wrong happened！" + e);
          }


          @Override
          public void onNext(List<Long> integers) {
            System.out.println("sequence is " + Arrays.toString(integers.toArray()));
          }
        });



    while (true);
  }
}
