package com.chensj.learn.learndemo.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * @author chensj
 * @title JDK8 方法引用
 * @project learn-demo
 * @package com.chensj.learn.learndemo.lambda
 * @date: 2019-03-19 13:14
 */
public class CarTest {
    public static void main(String[] args) {
        // 构造器引用
        // 语法是**Class::new**，或者更一般的形式：**Class<T>::new**
        Car car = Car.create(Car::new);
        List<Car> cars = Arrays.asList(car);
        // 静态方法引用
        // 注意：这个方法接受一个Car类型的参数
        cars.forEach(Car::collide);
        // 某个类的成员方法的引用
        // 这个方法没有定义入参
        cars.forEach(Car::repair);
        // 引用的类型是*某个实例对象的成员方法的引用*
        // 这个方法接受一个Car类型的参数
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
    }

}
