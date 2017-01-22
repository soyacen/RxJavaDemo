package com.example.error;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Jax on 16/12/19 17:12
 * 邮箱:songyancheng@wanda.cn
 */
public class ErrorHandingTest {

  public static void main(String[] a) {
    // onErrorResumeNext();

    // onErrorReturn();

    // onExceptionResumeNext();

    // retry();

    // retryWhen();

    // repeat();

     repeatWhen();

    while (true);
  }

  private static void onErrorResumeNext() {
    /**
     * 当原始的Observable出现error时候,让另一个Observable发射一串数据。
     */
    // getObservable().onErrorResumeNext(Observable.just(111L,400L)).subscribe(System.out::println);
    getObservable().onErrorResumeNext(throwable -> {
      System.out.println(throwable.getClass().getName());
      return Observable.just(111L, 400L);
    }).subscribe(getObserver());
  }

  private static void onErrorReturn() {
    /**
     * 当原始的Observable出现error时候,让另一个Observable发射一个特定的数据。
     */
    getObservable().onErrorReturn(throwable -> {
      System.out.println(throwable.getClass().getName());
      return -1L;
    }).subscribe(getObserver());
  }

  private static void onExceptionResumeNext() {
    /**
     * 当原始的Observable出现error时候,让另一个Observable继续发射数据。
     */
    getObservable().onExceptionResumeNext(Observable.just(-1L, -2L)).subscribe(getObserver());
  }

  private static void retry() {
    /**
     * 当.retry()接收到.onError()事件后触发重订阅。
     *
     * 当原始的Observable出现error时候,重试。
     */
    // 无限重试,直到onCompleted
    // getObservable().retry().subscribe(getObserver());

    // 重试3次,否则调用onError();
    // getObservable().retry(3).subscribe(getObserver());

    // 有条件的重试,当fun2返回ture接着重试,返回false则终止重试,调用onError();第一个参数是此次重试的次序。
    getObservable().retry(new Func2<Integer, Throwable, Boolean>() {
      @Override
      public Boolean call(Integer integer, Throwable throwable) {
        System.out.println(integer + "," + throwable.getClass().toString());
        return true;
      }
    }).subscribe(getObserver());

  }

  private static void retryWhen() {
    /**
     * see {@link <a href="http://www.jianshu.com/p/023a5f60e6d0">}
     *
     * 如果原始的Observable出现error时候,把这个error传给另一个Observable,来确定是否重新订阅原始的Observable。
     */
    /**
     * Func1像个工厂类，用来实现你自己的重试逻辑。
     * 输入的是一个Observable< Throwable>。
     *
     * 返回的是一个Observable< ?>。所要发送的事件决定了重订阅是否会发生。
     * 如果发送的是onCompleted或者onError事件，将不会触发重订阅。
     * 相对的，如果它发送onNext事件，则触发重订阅（不管onNext实际上是什么事件）。
     * 这就是为什么使用了通配符作为泛型类型：这仅仅是个通知（next, error或者completed），一个很重要的通知而已。
     *
     * 输入的Observable必须作为输出Observable的源。
     * 你必须对Observable<Throwable>做出反应，然后基于它发送事件；
     * 你不能只返回一个通用泛型流。也就就是说不能直接返回一个与输入的Observable没啥关系的Observable
     * 比如:return Observable.range(1,3).flatMap(new Func1<Integer, Observable<?>>() {
        @Override
        public Observable<?> call(Integer integer) {
          System.out.println("delay retry by " + integer + " second(s)");
          return Observable.timer(integer,TimeUnit.SECONDS);
          }
        };
     * repeat也一样;
     */
    getObservable()
        .retryWhen(
            attempts -> attempts.zipWith(Observable.range(1, 3), (exception, i) -> i).flatMap(i -> {
              System.out.println("delay retry by " + i + " second(s)");
              return Observable.timer(i, TimeUnit.SECONDS);
            }))
        .toBlocking().forEach(System.out::println);
  }


  private static void repeat() {
    //当.repeat()接收到.onCompleted()事件后触发重订阅。
    Observable.just("hello rxjava").repeat(10).subscribe(new Observer<String>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened!");
      }

      @Override
      public void onNext(String message) {
        System.out.println(message);
      }
    });
  }

  private static void repeatWhen() {
    /**
     * .repeatWhen()与.retryWhen()非常相似，只不过不再响应onError作为重试条件，而是onCompleted。
     * 因为onCompleted没有类型，所有输入变为Observable<Void>。
     */
    Observable.just("hello rxjava").repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
      @Override
      public Observable<?> call(Observable<? extends Void> observable) {

        return observable.zipWith(Observable.range(1, 3), new Func2<Void, Integer, Integer>() {
          @Override
          public Integer call(Void aVoid, Integer integer) {
            return integer;
          }
        }).flatMap(new Func1<Integer, Observable<?>>() {
          @Override
          public Observable<?> call(Integer integer) {
            System.out.println("delay retry by " + integer + " second(s)");
            return Observable.timer(integer,TimeUnit.SECONDS);
          }
        });
      }
    }).subscribe(new Observer<String>() {
      @Override
      public void onCompleted() {
        System.out.println("Observable completed");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("Oh,no! Something wrong happened!");
      }

      @Override
      public void onNext(String message) {
        System.out.println(message);
      }
    });
  }


  public static Observable<Long> getObservable() {

    return Observable.create(new Observable.OnSubscribe<Long>() {
      @Override
      public void call(Subscriber<? super Long> subscriber) {
        System.out.println("subscribing");
        for (long i = 0; i < 100; i++) {
          subscriber.onNext(i);
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          if (i > 30) {
            subscriber.onError(new RuntimeException());
          }
        }
        subscriber.onCompleted();
      }
    });

  }

  private static Observer<Long> getObserver() {
    return new Observer<Long>() {
      @Override
      public void onCompleted() {
        System.out.println("onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        System.out.println("onError" + e.getClass().toString());
      }

      @Override
      public void onNext(Long aLong) {
        System.out.println(aLong);
      }
    };
  }


}
