package org.chen.spring.security5.boot.service;

import org.chen.spring.security5.boot.dao.SysUserRoleMapper;
import org.chen.spring.security5.boot.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色Service
 *
 * @author chensj
 * @date 2019-09-11 15:43
 */
@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public List<SysUserRole> listByUserId(Integer userId) {
        return sysUserRoleMapper.listByUserId(userId);
    }
}
