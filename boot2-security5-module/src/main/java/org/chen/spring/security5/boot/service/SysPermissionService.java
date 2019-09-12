package org.chen.spring.security5.boot.service;

import org.chen.spring.security5.boot.dao.SysPermissionMapper;
import org.chen.spring.security5.boot.domain.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chensj
 * @date 2019-09-12 11:49
 */
@Service
public class SysPermissionService {
    @Autowired
    private SysPermissionMapper permissionMapper;

    /**
     * 获取指定角色所有权限
     */
    public List<SysPermission> listByRoleId(Integer roleId) {
        return permissionMapper.listByRoleId(roleId);
    }
}
