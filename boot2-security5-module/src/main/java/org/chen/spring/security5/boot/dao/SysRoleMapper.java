package org.chen.spring.security5.boot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.chen.spring.security5.boot.domain.SysRole;
import org.springframework.stereotype.Repository;

/**
 * 角色DAO层
 *
 * @author chensj
 * @date 2019-09-11 15:39
 */
@Mapper
@Repository
public interface SysRoleMapper {
    /**
     * 根据ID获取角色信息
     *
     * @param id 角色ID
     * @return role
     */
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(Integer id);

    /**
     * 根据角色名称获取角色信息
     *
     * @param roleName 角色名称
     * @return role
     */
    @Select("SELECT * FROM sys_role where name = #{roleName}")
    SysRole selectByName(String roleName);
}
