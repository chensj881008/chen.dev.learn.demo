package com.winning.devops.jpa.dao;

import com.winning.devops.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author chensj
 * @title
 * @project spring-boot-jpa
 * @package com.winning.devops.jpa.dao
 * @date: 2019-02-13 9:42
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据名称获取用户
     * 解析方法名来创建查询
     * @param name
     * @return
     */
    User findByName(String name);

    /**
     * 根据名称和年龄获取用户
     * 解析方法名来创建查询
     * @param name
     * @param age
     * @return
     */
    User findByNameAndAge(String name,Integer age);

    /**
     * HQL查询
     * 使用@Query 注解来创建查询，您只需要编写JPQL语句，并通过类似“:name”来映射@Param指定的参数
     * @param name
     * @return
     */
    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);
}
