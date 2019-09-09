package org.chen.spring.security.ssm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;  

import org.chen.spring.security.ssm.domain.SysRoleAuth;  

import org.chen.spring.security.ssm.mapper.SysRoleAuthDao;  

import org.chen.spring.security.ssm.service.SysRoleAuthService;  


/**
* @author Dev Ops
* @title 角色权限表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service.impl
* @date 2019-09-09 22:14:04
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleAuthServiceImpl implements  SysRoleAuthService {

    @Autowired
    private SysRoleAuthDao sysRoleAuthDao;
    @Override
    public int createSysRoleAuth(SysRoleAuth sysRoleAuth){
        return this.sysRoleAuthDao.insertSysRoleAuth(sysRoleAuth);
    }
    @Override
    public int modifySysRoleAuth(SysRoleAuth sysRoleAuth){
        return this.sysRoleAuthDao.updateSysRoleAuth(sysRoleAuth);
    }
    @Override
    public int removeSysRoleAuth(SysRoleAuth sysRoleAuth){
        return this.sysRoleAuthDao.deleteSysRoleAuth(sysRoleAuth);
    }
    @Override
    public SysRoleAuth getSysRoleAuth(SysRoleAuth sysRoleAuth){
        return this.sysRoleAuthDao.selectSysRoleAuth(sysRoleAuth);
    }
    @Override
    public int getSysRoleAuthCount(SysRoleAuth sysRoleAuth){
        return this.sysRoleAuthDao.selectSysRoleAuthCount(sysRoleAuth);
    }
    @Override
    public List<SysRoleAuth> getSysRoleAuthList(SysRoleAuth sysRoleAuth){
        return this.sysRoleAuthDao.selectSysRoleAuthList(sysRoleAuth);
    }
    @Override
    public List<SysRoleAuth> getSysRoleAuthPageList(SysRoleAuth sysRoleAuth){
        return this.sysRoleAuthDao.selectSysRoleAuthPageList(sysRoleAuth);
    }
}