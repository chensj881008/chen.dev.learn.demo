package com.winning.devops.mybatis.plus.model;

import lombok.Data;

/**
 * @author chensj
 * @title 实体类
 * @project mybatis-plus-demo
 * @package com.winning.devops.mybatis.plus.model
 * @date: 2019-02-01 15:04
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
