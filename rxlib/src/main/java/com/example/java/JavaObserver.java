package com.example.java;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jax on 16/12/13 13:53
 * 邮箱:songyancheng@wanda.cn
 */
public class JavaObserver implements Observer {

  @Override
  public void update(Observable o, Object arg) {
    System.out.println(o.toString() + arg);
  }
}
