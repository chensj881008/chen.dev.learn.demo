package org.chen.spring.service;

import org.chen.spring.entity.SysRole;
import org.chen.spring.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色Service
 *
 * @author chensj
 * @date 2019-09-11 15:42
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public SysRole selectById(Integer id) {
        return sysRoleMapper.selectById(id);
    }

    public SysRole selectByName(String roleName) {
        return sysRoleMapper.selectByName(roleName);
    }
}
