package org.chen.spring.security.ssm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;  

import org.chen.spring.security.ssm.domain.SysRole;  

import org.chen.spring.security.ssm.mapper.SysRoleDao;  

import org.chen.spring.security.ssm.service.SysRoleService;  


/**
* @author Dev Ops
* @title 角色表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service.impl
* @date 2019-09-09 22:14:03
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl implements  SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Override
    public int createSysRole(SysRole sysRole){
        return this.sysRoleDao.insertSysRole(sysRole);
    }
    @Override
    public int modifySysRole(SysRole sysRole){
        return this.sysRoleDao.updateSysRole(sysRole);
    }
    @Override
    public int removeSysRole(SysRole sysRole){
        return this.sysRoleDao.deleteSysRole(sysRole);
    }
    @Override
    public SysRole getSysRole(SysRole sysRole){
        return this.sysRoleDao.selectSysRole(sysRole);
    }
    @Override
    public int getSysRoleCount(SysRole sysRole){
        return this.sysRoleDao.selectSysRoleCount(sysRole);
    }
    @Override
    public List<SysRole> getSysRoleList(SysRole sysRole){
        return this.sysRoleDao.selectSysRoleList(sysRole);
    }
    @Override
    public List<SysRole> getSysRolePageList(SysRole sysRole){
        return this.sysRoleDao.selectSysRolePageList(sysRole);
    }
}