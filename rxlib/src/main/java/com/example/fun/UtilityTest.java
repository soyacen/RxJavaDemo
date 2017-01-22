package com.example.fun;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class UtilityTest {

  public static void main(String argv[]) {

    // materialize();

    // deMaterialize();

    // timestamp();

    // timeInterval();

    // serialize();

    // cache();

    // observeOn();

    // subscribeOn();

    // doOnEach();

    // doOnCompleted();

    // doOnError();

    // doOnTerminate();

    // doOnSubscribe();

    // doOnUnsubscribe();

    // finallyDo();

    // delay();

    // delaySubscription();

    // using();

    // single();

    // compose();


    while (true);
  }


  private static void single() {
    //如果原始Observable在完成之前不是正好发射一次数据，它会抛出一个NoSuchElementException。
    Observable.just("AA"/*,"BB"*/).single().subscribe(System.out::println);

    //如果没有数据满足条件，返回默认值；如果有多个数据满足条件，以错误通知终止。
    Observable.empty().singleOrDefault("BB").subscribe(System.out::println);

  }

  private static void using() {
    // 创建一个只在Observable生命周期内存在的一次性资源
    /**
     * using操作符接受三个参数：
     *
     * 一个用户创建一次性资源的工厂函数
     * 一个用于创建Observable的工厂函数
     * 一个用于释放资源的函数
     */
    Observable.using(new Func0<BufferedReader>() {
      @Override
      public BufferedReader call() {
        System.out.println("resourceFactory");
        try {
          return new BufferedReader(new FileReader("/Users/Jax/Desktop/android进阶"));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
          return null;
        }
      }
    }, new Func1<BufferedReader, Observable<?>>() {
      @Override
      public Observable<?> call(BufferedReader reader) {
        System.out.println("observableFactory");
        if (reader != null) {
          List<String> list = new ArrayList<String>();
          String line = null;
          try {
            while (null != (line = reader.readLine())) {
              list.add(line);
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          return Observable.from(list);
        }
        return null;
      }
    }, new Action1<BufferedReader>() {
      @Override
      public void call(BufferedReader builder) {
        System.out.println("disposeAction");
        if (builder != null) {
          try {
            builder.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }).subscribe(System.out::println);
  }

  private static void timeInterval() {
    // timeInterval会将每个数据项给重新包装一下，加上了两个连续的发射的时间间隔。
    // emit the time lapsed between consecutive emissions of a source Observable
    Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        for (int i = 0; i < 10; i++) {
          System.out.println("onNext" + i);
          subscriber.onNext(i);
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        subscriber.onCompleted();
      }
    }).timeInterval().subscribe(System.out::println);

  }

  private static void delaySubscription() {
    // 延时处理订阅请求(相当于在一个Timer里面调用ob.subscribe() )
    // hold an Subscriber's subscription request for a specified amount of time
    // before passing it on to the source Observable
    Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        for (int i = 0; i < 10; i++) {
          System.out.println("onNext" + i);
          subscriber.onNext(i);
        }
        subscriber.onCompleted();
      }
    }).delaySubscription(3, TimeUnit.SECONDS)
        .doOnSubscribe(() -> System.out.println("doOnSubscribe")).subscribe(System.out::println);
  }

  private static void delay() {
    // 延时发射Observable的结果(onNext()调用了,Observer延迟收到)
    // shift the emissions from an Observable forward in time by a specified amount
    Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        for (int i = 0; i < 10; i++) {
          System.out.println("onNext" + i);
          subscriber.onNext(i);
        }
        subscriber.onCompleted();
      }
    }).delay(2, TimeUnit.SECONDS).doOnSubscribe(() -> System.out.println("doOnSubscribe"))
        .subscribe(System.out::println);
    // System.out.println("start");
    /*
     * Observable.range(1,2).delay(new Func1<Object, Observable<Long>>() {
     * @Override
     * public Observable<Long> call(Object o) {
     * return Observable.timer(3,TimeUnit.SECONDS);
     * }
     * }).finallyDo(() -> System.out.println("finallyDo" + Thread.currentThread().getId()))
     * .subscribe();
     */

    // Observable.range(1,10).delay(new Func0<Observable<Long>>() {
    // @Override
    // public Observable<Long> call() {
    // return Observable.timer(3,TimeUnit.SECONDS);
    // }
    // }, new Func1<Integer, Observable<Long>>() {
    // @Override
    // public Observable<Long> call(Integer integer) {
    // System.out.println("call: "+integer);
    // return Observable.timer(2,TimeUnit.SECONDS);
    // }
    // }).finallyDo(() -> System.out.println("finallyDo" + Thread.currentThread().getId()))
    // .subscribe(System.out::println);
  }

  private static void finallyDo() {
    // finallyDo use doAfterTerminate(Action0) instead. 在Observer所在线程。
    Observable.empty()
        .finallyDo(() -> System.out.println("finallyDo" + Thread.currentThread().getId()))
        .subscribe();

    Observable ob = Observable.range(1, 10).observeOn(Schedulers.newThread())
        .doAfterTerminate(
            () -> System.out.println("doAfterTerminate" + Thread.currentThread().getId()));
    ob.subscribe(o -> System.out.println("Oberver" + Thread.currentThread().getId()));
  }

  private static void doOnUnsubscribe() {
    // doOnUnsubscribe执行了Observer时候在Observer线程,否者在main线程。
    Observable ob = Observable.range(1, 10).subscribeOn(Schedulers.newThread())
        .doOnSubscribe(() -> System.out.println("doOnSubscribe" + Thread.currentThread().getId()))
        .doOnUnsubscribe(
            () -> System.out.println("doOnUnsubscribe" + Thread.currentThread().getId()));
    System.out.println("subscribe");
    Subscription subscription =
        ob.subscribe(o -> System.out.println("Oberver" + Thread.currentThread().getId()));
    System.out.println("unsubscribe");
    subscription.unsubscribe();
  }

  private static void doOnSubscribe() {
    // doOnSubscribe在main线程。
    Observable ob = Observable.empty().subscribeOn(Schedulers.newThread())
        .doOnSubscribe(() -> System.out.println("doOnSubscribe" + Thread.currentThread().getId()));
    System.out.println("subscribe");
    ob.subscribe();
  }

  private static void doOnTerminate() {
    Observable.empty().doOnTerminate(() -> System.out.print("doOnTerminate")).subscribe();
    Observable.error(new NullPointerException())
        .doOnTerminate(() -> System.out.print("doOnTerminate")).subscribe();
    // Observable.never().doOnTerminate(()->System.out.print("doOnTerminate")).subscribe();
  }

  private static void doOnError() {
    Observable.error(new NullPointerException()).doOnError(System.out::println).subscribe();
  }

  private static void doOnCompleted() {
    Observable<Integer> ob =
        Observable.range(1, 10).doOnCompleted(() -> System.out.println("doOnCompleted"));
    ob.subscribe();
  }

  private static void doOnEach() {
    Observable<Integer> ob =
        Observable.range(1, 10).doOnEach(new Action1<Notification<? super Integer>>() {
          @Override
          public void call(Notification<? super Integer> notification) {
            System.out.println(
                Thread.currentThread().getId() + ", Notification: " + notification.toString());
          }
        }).doOnEach(new Observer<Integer>() {
          @Override
          public void onCompleted() {

        }

          @Override
          public void onError(Throwable e) {

        }

          @Override
          public void onNext(Integer integer) {
            System.out.println(Thread.currentThread().getId() + ", Observer: " + integer);
          }
        });
    ob.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(
        num -> {
          System.out.println(Thread.currentThread().getId() + ", " + num);
        });
  }

  private static void subscribeOn() {
    Observable<Integer> ob = Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        System.out.println(Thread.currentThread().getId() + ", " + 1);
        subscriber.onCompleted();
      }
    });
    ob.subscribeOn(Schedulers.newThread()).subscribe();
    ob.subscribeOn(Schedulers.newThread()).subscribe();
    ob.subscribeOn(Schedulers.newThread()).subscribe();
  }

  private static void observeOn() {
    Observable<Integer> ob = Observable.range(1, 10);
    ob.observeOn(Schedulers.newThread())
        .subscribe(num -> System.out.println(Thread.currentThread().getId() + ", " + num));
    ob.observeOn(Schedulers.newThread())
        .subscribe(num -> System.out.println(Thread.currentThread().getId() + ", " + num));
    ob.observeOn(Schedulers.newThread())
        .subscribe(num -> System.out.println(Thread.currentThread().getId() + ", " + num));
  }

  private static void cache() {
    /**
     * 记住Observable发射的数据序列并发射相同的数据序列给后续的订阅者
     * ReplaySubject 功效一样;
     */
    Observable<Long> ob = Observable.interval(100, TimeUnit.MILLISECONDS).cache();
    ob.subscribe(System.out::println);
    Observable.timer(3, TimeUnit.SECONDS).subscribe(along -> ob.subscribe(System.out::println));
  }

  private static void serialize() {
    /**
     * 强制Observable按次序发射数据并且要求功能是完好的
     *
     * 一个Observable可以异步调用它的观察者的方法，
     * 可能是从不同的线程调用。
     * 这样会让Observable调用冲突，它可能会在某一个onNext调用之前尝试调用onCompleted或onError方法，
     * 或者从两个不同的线程同时调用onNext方法。使
     * 用Serialize操作符，你可以纠正这个Observable的行为，保证它的行为是正确的且是同步的。
     * serialize操作符默认不在任何特定的调度器上执行。
     */
    Observable.interval(100, TimeUnit.MILLISECONDS)
        .mergeWith(Observable.interval(200, TimeUnit.MILLISECONDS)).timestamp()
        .serialize().subscribe(System.out::println);
  }

  private static void timestamp() {
    /**
     * TimeStamp会将每个数据项给重新包装一下，加上了一个时间戳来标明每次发射的时间
     */
    Observable.range(1, 1000).timestamp().subscribe(System.out::println);
  }

  private static void deMaterialize() {
    Observable.range(1, 1000).materialize().dematerialize().subscribe(System.out::println);
  }

  private static void materialize() {
    /**
     * Meterialize操作符将OnNext/OnError/OnComplete都转化为一个Notification对象并按照原来的顺序发射出来，
     * 而DeMeterialize则是执行相反的过程。
     */
    Observable.range(1, 1000).materialize().subscribe(System.out::println);

  }



}
