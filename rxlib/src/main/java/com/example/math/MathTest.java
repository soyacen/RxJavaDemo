package com.example.math;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.observables.MathObservable;

/**
 * Created by Jax on 16/12/20 10:41
 * 邮箱:songyancheng@wanda.cn
 */
public class MathTest {

    public static void main(String[] a) {

        // avg();

        // max();

        // min();

        // sum();

        // concat();

        // count();

        // reduce();

        // collect();

        // toList();

        // toSortedList();

        // toMap();

        toMultiMap();

        while (true) ;
    }

    private static void toMultiMap() {
        /**
         * convert the sequence of items emitted by an Observable into an ArrayList that is also a map keyed by a specified key function
         */
        Observable.just("E", "Q", "W", "R", "T", "Y").toMultimap(
                item -> {
                    System.out.println("key: " + item);
                    return item;
                },
                item -> {
                    System.out.println("value: " + item);
                    return item + item;
                },
                () -> {
                    System.out.println("mapFactory");
                    return new LinkedHashMap<String, Collection<String>>();},
                (item) -> {
                    System.out.println("collectionFactory: " + item);
                    return new LinkedList<String>();
                }
        ).subscribe(System.out::println);
    }

    private static void toMap() {
        /**
         *  convert the sequence of items emitted by an Observable into a map keyed by a specified key function
         */
        Observable.just("E", "Q", "W", "R", "T", "Y").toMap(item -> {
            System.out.println("key: " + item);
            return item;
        }, item -> {
            System.out.println("value: " + item);
            return item + item;
        }, () -> new HashMap<String, String>()).subscribe(System.out::println);
    }

    private static void toSortedList() {

        Observable.just(1, 10, 3, 7, 6, 2, 5, 4, 8).toSortedList().subscribe(System.out::println);
        Observable.just("E", "Q", "W", "R", "T", "Y")
                .toSortedList((left, right) -> -1 * left.compareTo(right)).subscribe(System.out::println);
    }

    private static void toList() {
        /**
         * 把一个一个发射的放进list中。然后发射这个list
         */
        Observable.range(1, 10).toList().subscribe(System.out::println);
    }

    private static void collect() {
        /**
         * collect items emitted by the source Observable into a single mutable data structure and
         * return an Observable that emits this structure
         * 收集源Observable发射出来的items,放进一个独立的可变的数据结构。返回发射这个结构的Observable。
         *
         */
        Observable.range(1, 10).collect(new Func0<Integer>() {
            @Override
            public Integer call() {
                int r = new Random().nextInt(10);
                System.out.println(" Random: " + r);
                return r;
            }
        }, new Action2<Integer, Integer>() {
            @Override
            public void call(Integer integer, Integer integer2) {
                System.out.println(integer + " , " + integer2);
            }
        }).subscribe(System.out::println);
    }

    private static void reduce() {
        /**
         * apply a function to each emitted item, sequentially, and emit only the final accumulated
         * value
         * 用个函数线性的每一个元素,最后只发射最后的值
         * Func2有两个参数,第一参数是默认值或者第一个值或者上一次返回值。第二参数是第一个或者第二个线性的拍下去呗。
         */
        Observable.range(1, 10).reduce(-1, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                System.out.println(integer + " + " + integer2 + " = " + (integer + integer2));
                return integer + integer2;
            }
        }).subscribe(System.out::println);
    }

    private static void count() {
        Observable.range(1, 10).count().subscribe(System.out::println);

        Observable.range(1, 100).countLong().subscribe(System.out::println);
    }

    private static void concat() {
        Observable
                .concat(Observable.interval(1, TimeUnit.SECONDS),
                        Observable.interval(1, TimeUnit.MILLISECONDS))
                .subscribe(System.out::println);
    }

    private static void sum() {
        MathObservable.sumInteger(Observable.range(1, 10)).subscribe(System.out::println);

        MathObservable.sumLong(Observable.interval(10, TimeUnit.MILLISECONDS).take(10))
                .subscribe(System.out::println);

        MathObservable.sumFloat(Observable.just(1.0F, 2.0f, 3.0f, 4.0f))
                .subscribe(System.out::println);

        MathObservable.sumDouble(Observable.just(1.0D, 2.0D, 3.0D, 4.0D, 5.0D))
                .subscribe(System.out::println);
    }

    private static void min() {
        MathObservable.min(Observable.just(1, 3, 6, 2, 5, 8, 3, 7, 1)).subscribe(System.out::println);
        MathObservable.from(Observable.just("HH", "CC", "BB", "II", "AA", "EE", "FF", "DD", "GG"))
                .min(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }).subscribe(System.out::println);
    }

    private static void max() {
        MathObservable.max(Observable.just(1, 3, 6, 2, 5, 8, 3, 7, 1)).subscribe(System.out::println);
        MathObservable.from(Observable.just("HH", "CC", "BB", "II", "AA", "EE", "FF", "DD", "GG"))
                .max(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }).subscribe(System.out::println);
    }

    private static void avg() {
        MathObservable.averageInteger(Observable.range(1, 10)).subscribe(System.out::println);

        MathObservable.averageLong(Observable.interval(10, TimeUnit.MILLISECONDS).take(10))
                .subscribe(System.out::println);

        MathObservable.averageFloat(Observable.just(1.0F, 2.0f, 3.0f, 4.0f))
                .subscribe(System.out::println);

        MathObservable.averageDouble(Observable.just(1.0D, 2.0D, 3.0D, 4.0D, 5.0D))
                .subscribe(System.out::println);
    }

}
