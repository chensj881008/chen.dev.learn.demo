package org.chen.spring.security5.boot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.chen.spring.security5.boot.domain.SysUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chensj
 * @date 2019-09-11 15:40
 */
@Mapper
@Repository
public interface SysUserRoleMapper {
    /**
     * 根据用户获取角色ID信息
     *
     * @param userId 用户ID
     * @return list
     */
    @Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
    List<SysUserRole> listByUserId(Integer userId);
}
