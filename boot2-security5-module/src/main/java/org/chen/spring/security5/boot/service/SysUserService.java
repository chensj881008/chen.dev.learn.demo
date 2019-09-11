package org.chen.spring.security5.boot.service;

import org.chen.spring.security5.boot.dao.SysUserMapper;
import org.chen.spring.security5.boot.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chensj
 * @date 2019-09-11 15:41
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser selectById(Integer id) {
        return sysUserMapper.selectById(id);
    }

    public SysUser selectByName(String name) {
        return sysUserMapper.selectByName(name);
    }

}
