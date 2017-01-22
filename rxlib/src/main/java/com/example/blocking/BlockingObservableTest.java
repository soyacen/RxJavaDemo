package com.example.blocking;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.BlockingObservable;

/**
 * Created by Jax on 16/12/19 09:55
 * 邮箱:songyancheng@wanda.cn
 */
public class BlockingObservableTest {
  public static void main(String[] arg) {
    /**
     * 使用 BlockingObservable 可以把 Observable 中的数据通过阻塞的方式发射出来。任何一个 Observable 都可以使用下面两种方式来转换为阻塞的
     * Observable。
     */
    // BlockingObservable bo = BlockingObservable.from(Observable.just(10L));
    BlockingObservable bo = Observable.interval(100, TimeUnit.MILLISECONDS)/*.take(1)*/.toBlocking();

    // forEach(bo);

    // first(bo);

    // last(bo);

    // single(bo);

    // mostRecent(bo);

    //next(bo);

    // latest(bo);

      //toFuture(bo);

    getIterator(bo);

    //toIterable(bo);

    System.out.println("Subscribed");
  }

  private static void forEach(BlockingObservable bo) {
    System.out.println("+++++++++++++++++forEach++++++++++++++++++");
    /**
     * Observable 有个函数叫做 forEach。 forEach 为 subscribe 的一个没有返回Subscription 的别名。
     *
     * 这里由于使用的是阻塞的 Observable，所以当 forEach 执行完后，才会执行后面的打印 Subscribed 的代码。
     * 同时 阻塞的 Observable 也没有 onError 和 onCompleted 函数。
     * 当执行完成的时候，就执行完了；当错误发生的时候，异常就直接就地抛出了；
     */
    bo.forEach(System.out::println);

  }

  private static void first(BlockingObservable bo) {
    System.out.println("+++++++++++++++++first++++++++++++++++++");
    /**
     * first 会一直阻塞，直到有数据发射并返回符合条件的数据。
     */
    System.out.println(bo.first());

    System.out.println(bo.first(new Func1<Long, Boolean>() {
      @Override
      public Boolean call(Long s) {
        return s % 11 == 10;
      }
    }));

    System.out.println(bo.firstOrDefault(-1));
    System.out.println(bo.firstOrDefault(-1, new Func1<Long, Boolean>() {
      @Override
      public Boolean call(Long s) {
        return s % 11 == 12;
      }
    }));
  }

  private static void last(BlockingObservable bo) {
    System.out.println("+++++++++++++++++last++++++++++++++++++");
    /**
     * last 会一直阻塞，直到有数据发射并返回符合条件的数据。
     */

    System.out.println(bo.last());

    System.out.println(bo.last(new Func1<Long, Boolean>() {
      @Override
      public Boolean call(Long s) {
        return s % 11 == 10;
      }
    }));

    System.out.println(bo.lastOrDefault(-1));
    System.out.println(bo.lastOrDefault(-1, new Func1<Long, Boolean>() {
      @Override
      public Boolean call(Long s) {
        return s % 11 == 12;
      }
    }));
  }

  private static void single(BlockingObservable bo) {
    System.out.println("+++++++++++++++++single++++++++++++++++++");
    /**
     * single 会一直阻塞，直到有数据发射并返回符合条件的数据。
     */
    System.out.println(bo.single());

    System.out.println(bo.single(new Func1<Long, Boolean>() {
      @Override
      public Boolean call(Long s) {
        return s % 11 == 10;
      }
    }));

    System.out.println(bo.singleOrDefault(-1));
    System.out.println(bo.singleOrDefault(-1, new Func1<Long, Boolean>() {
      @Override
      public Boolean call(Long s) {
        return s % 11 == 12;
      }
    }));
  }

  private static void mostRecent(BlockingObservable bo) {
    System.out.println("+++++++++++++++++mostRecent++++++++++++++++++");
    Iterable<Long> iterable = bo.mostRecent(10L);

    iterable.forEach(num -> {
      System.out.println(num);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

  }


  private static void next(BlockingObservable bo) {
    System.out.println("+++++++++++++++++mostRecent++++++++++++++++++");
    /**
     * 这种实现数据没有缓存。 iterator 总是等待下一个数据并立刻返回。
     * 这里的示例中， 打印语句（消费者）处理的速度比数据发射的速度慢。所以消费者会错过一些数据。
     */
    Iterable<Long> iterable = bo.next();

    iterable.forEach(num -> {
      System.out.println(num);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  private static void latest(BlockingObservable bo) {
    System.out.println("+++++++++++++++++mostRecent++++++++++++++++++");
    /**
     * latest 和 next 类似，区别就是 latest 会缓存一个数据。
     * 使用 latest 的时候，如果在下一个数据发射之前，当前的数据还没有被消费者消费，
     * 则当前的值就会丢失。
     * 如果 消费者比 生产者（Observable）发射的数据快，则 iterator
     * 会阻塞并且等待下一个数据。
     */
    Iterable<Long> iterable = bo.latest();

    iterable.forEach(num -> {
      System.out.println(num);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

  }


    private static void toFuture(BlockingObservable bo) {
        System.out.println("+++++++++++++++++toFuture++++++++++++++++++");

        Future<Long> future = bo.toFuture();
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


  private static void getIterator(BlockingObservable bo) {
    System.out.println("+++++++++++++++++getIterator++++++++++++++++++");
    /**
     * 底层就是用getIterator实现的;
     */
    Iterator<Long> iterator = bo.getIterator();

    while (iterator.hasNext()){
      System.out.println(iterator.next());
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private static void toIterable(BlockingObservable bo) {
    System.out.println("+++++++++++++++++toIterable++++++++++++++++++");
    /**
     * 底层就是用getIterator实现的;
     */
    Iterable<Long> iterable = bo.toIterable();

    iterable.forEach(num -> {
      System.out.println(num);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

}
