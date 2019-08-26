package com.chensj.learn.learndemo.lambda;

import java.util.Arrays;

/**
 * @author chensj
 * @title
 * @project learn-demo
 * @package com.chensj.learn.learndemo.lambda
 * @date: 2019-03-19 11:08
 */
public class LambdaPath {
    public static void main(String[] args) {
        Arrays.asList("a", "b", "c").forEach((e) -> {
            System.out.println(e);
        });
    }

}
