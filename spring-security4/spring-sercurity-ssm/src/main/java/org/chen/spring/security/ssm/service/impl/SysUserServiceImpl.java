package org.chen.spring.security.ssm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;  

import org.chen.spring.security.ssm.domain.SysUser;  

import org.chen.spring.security.ssm.mapper.SysUserDao;  

import org.chen.spring.security.ssm.service.SysUserService;  


/**
* @author Dev Ops
* @title 用户表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service.impl
* @date 2019-09-09 22:14:05
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements  SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Override
    public int createSysUser(SysUser sysUser){
        return this.sysUserDao.insertSysUser(sysUser);
    }
    @Override
    public int modifySysUser(SysUser sysUser){
        return this.sysUserDao.updateSysUser(sysUser);
    }
    @Override
    public int removeSysUser(SysUser sysUser){
        return this.sysUserDao.deleteSysUser(sysUser);
    }
    @Override
    public SysUser getSysUser(SysUser sysUser){
        return this.sysUserDao.selectSysUser(sysUser);
    }
    @Override
    public int getSysUserCount(SysUser sysUser){
        return this.sysUserDao.selectSysUserCount(sysUser);
    }
    @Override
    public List<SysUser> getSysUserList(SysUser sysUser){
        return this.sysUserDao.selectSysUserList(sysUser);
    }
    @Override
    public List<SysUser> getSysUserPageList(SysUser sysUser){
        return this.sysUserDao.selectSysUserPageList(sysUser);
    }
}