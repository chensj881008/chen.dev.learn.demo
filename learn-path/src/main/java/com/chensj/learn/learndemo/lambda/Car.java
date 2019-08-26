package com.chensj.learn.learndemo.lambda;

import java.util.function.Supplier;

/**
 * @author chensj
 * @title 方法引用
 * @project learn-demo
 * @package com.chensj.learn.learndemo.lambda
 * @date: 2019-03-19 13:13
 */
public class Car {
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}
