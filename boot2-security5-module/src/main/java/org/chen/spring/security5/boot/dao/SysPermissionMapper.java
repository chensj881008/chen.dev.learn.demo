package org.chen.spring.security5.boot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.chen.spring.security5.boot.domain.SysPermission;

import java.util.List;

/**
 * @author chensj
 * @date 2019-09-12 11:28
 */
@Mapper
public interface SysPermissionMapper {
    /**
     * 根据角色ID获取全部权限
     *
     * @param roleId 角色ID
     * @return list
     */
    @Select("SELECT * FROM sys_permission WHERE role_id=#{roleId}")
    List<SysPermission> listByRoleId(Integer roleId);
}
