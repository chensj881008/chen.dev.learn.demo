package org.chen.spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.chen.spring.entity.SysPermission;

import java.util.List;

/**
 * @author chensj
 * @date 2019-09-14 上午12:11
 */
@Mapper
public interface SysPermissionMapper {
    /**
     * 根据角色获取权限信息
     *
     * @param roleId 角色ID
     * @return list
     */
    @Select("SELECT * FROM sys_permission WHERE role_id=#{roleId}")
    List<SysPermission> listByRoleId(Integer roleId);
}
