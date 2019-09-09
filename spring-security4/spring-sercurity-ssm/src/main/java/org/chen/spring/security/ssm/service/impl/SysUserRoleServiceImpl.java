package org.chen.spring.security.ssm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;  

import org.chen.spring.security.ssm.domain.SysUserRole;  

import org.chen.spring.security.ssm.mapper.SysUserRoleDao;  

import org.chen.spring.security.ssm.service.SysUserRoleService;  


/**
* @author Dev Ops
* @title 用户角色表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service.impl
* @date 2019-09-09 22:14:05
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl implements  SysUserRoleService {

    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Override
    public int createSysUserRole(SysUserRole sysUserRole){
        return this.sysUserRoleDao.insertSysUserRole(sysUserRole);
    }
    @Override
    public int modifySysUserRole(SysUserRole sysUserRole){
        return this.sysUserRoleDao.updateSysUserRole(sysUserRole);
    }
    @Override
    public int removeSysUserRole(SysUserRole sysUserRole){
        return this.sysUserRoleDao.deleteSysUserRole(sysUserRole);
    }
    @Override
    public SysUserRole getSysUserRole(SysUserRole sysUserRole){
        return this.sysUserRoleDao.selectSysUserRole(sysUserRole);
    }
    @Override
    public int getSysUserRoleCount(SysUserRole sysUserRole){
        return this.sysUserRoleDao.selectSysUserRoleCount(sysUserRole);
    }
    @Override
    public List<SysUserRole> getSysUserRoleList(SysUserRole sysUserRole){
        return this.sysUserRoleDao.selectSysUserRoleList(sysUserRole);
    }
    @Override
    public List<SysUserRole> getSysUserRolePageList(SysUserRole sysUserRole){
        return this.sysUserRoleDao.selectSysUserRolePageList(sysUserRole);
    }
}