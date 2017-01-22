package com.example.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

/**
 * Created by Jax on 16/12/13 17:58
 * 邮箱:songyancheng@wanda.cn
 */
public class FilterOfTypeTest {
    public static void main(String argv[]) {

        /**
         * ofType()函数过滤掉由Observable发射的数据类型过多,你想要制定的类型数据
         */

        List list = new ArrayList();
        list.add("a");
        list.add(1);
        list.add(true);

        Observable<Long> observableString = Observable.from(list);

        observableString.ofType(String.class).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(String item) {
                System.out.println("*********>Item is " + item);
            }
        });

        while (true) ;
    }
}
