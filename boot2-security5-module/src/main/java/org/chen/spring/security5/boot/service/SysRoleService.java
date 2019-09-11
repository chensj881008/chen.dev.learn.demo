package org.chen.spring.security5.boot.service;

import org.chen.spring.security5.boot.dao.SysRoleMapper;
import org.chen.spring.security5.boot.domain.SysRole;
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
}
