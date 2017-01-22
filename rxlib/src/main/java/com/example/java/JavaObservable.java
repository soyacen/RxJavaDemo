package com.example.java;

import java.util.Observable;

/**
 * Created by Jax on 16/12/13 13:52
 * 邮箱:songyancheng@wanda.cn
 */
public class JavaObservable extends Observable {

    public void doSomeing(){
        setChanged();
        //notifyObservers();
        notifyObservers("这里可以传参");
    }

    @Override
    public String toString() {
        return "被观察者";
    }
}
