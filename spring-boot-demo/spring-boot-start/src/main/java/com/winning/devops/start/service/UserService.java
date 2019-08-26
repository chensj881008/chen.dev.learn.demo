package com.winning.devops.start.service;

/**
 * @author chensj
 * @title 使用JdbcTemplate操作数据库
 * @project spring-boot-start
 * @package com.winning.devops.start.service
 * @date: 2019-02-13 15:52
 */
public interface UserService {
    /**
     * 新增一个用户
     *
     * @param name
     * @param age
     */
    void create(String name, Integer age);

    /**
     * 根据name删除一个用户高
     *
     * @param name
     */
    void deleteByName(String name);

    /**
     * 获取用户总量
     */
    Integer getAllUsers();

    /**
     * 删除所有用户
     */
    void deleteAllUsers();
}
