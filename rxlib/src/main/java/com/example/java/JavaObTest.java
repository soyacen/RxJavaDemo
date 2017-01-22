package com.example.java;

/**
 * Created by Jax on 16/12/13 13:54
 * 邮箱:songyancheng@wanda.cn
 */
public class JavaObTest {
    public static void main(String argv[]){
        JavaObservable javaObservable=new JavaObservable();
        javaObservable.addObserver(new JavaObserver());
        javaObservable.addObserver(new JavaObserver());
        javaObservable.addObserver(new JavaObserver());
        javaObservable.addObserver(new JavaObserver());
        javaObservable.doSomeing();
    }

}
