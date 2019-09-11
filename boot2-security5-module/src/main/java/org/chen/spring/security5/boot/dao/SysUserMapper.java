package org.chen.spring.security5.boot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.chen.spring.security5.boot.domain.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author chensj
 * @date 2019-09-11 15:38
 */
@Mapper
@Repository
public interface SysUserMapper {
    /**
     * 根据ID获取用户信息
     *
     * @param id id
     * @return user
     */
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Integer id);

    /**
     * 根据用户名称获取用户信息
     *
     * @param name 用户名称
     * @return user
     */
    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);

}
