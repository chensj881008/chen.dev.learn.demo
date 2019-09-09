package org.chen.spring.security.ssm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;  

import org.chen.spring.security.ssm.domain.SysAuth;  

import org.chen.spring.security.ssm.mapper.SysAuthDao;  

import org.chen.spring.security.ssm.service.SysAuthService;  


/**
* @author Dev Ops
* @title 权限表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service.impl
* @date 2019-09-09 22:14:03
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysAuthServiceImpl implements  SysAuthService {

    @Autowired
    private SysAuthDao sysAuthDao;
    @Override
    public int createSysAuth(SysAuth sysAuth){
        return this.sysAuthDao.insertSysAuth(sysAuth);
    }
    @Override
    public int modifySysAuth(SysAuth sysAuth){
        return this.sysAuthDao.updateSysAuth(sysAuth);
    }
    @Override
    public int removeSysAuth(SysAuth sysAuth){
        return this.sysAuthDao.deleteSysAuth(sysAuth);
    }
    @Override
    public SysAuth getSysAuth(SysAuth sysAuth){
        return this.sysAuthDao.selectSysAuth(sysAuth);
    }
    @Override
    public int getSysAuthCount(SysAuth sysAuth){
        return this.sysAuthDao.selectSysAuthCount(sysAuth);
    }
    @Override
    public List<SysAuth> getSysAuthList(SysAuth sysAuth){
        return this.sysAuthDao.selectSysAuthList(sysAuth);
    }
    @Override
    public List<SysAuth> getSysAuthPageList(SysAuth sysAuth){
        return this.sysAuthDao.selectSysAuthPageList(sysAuth);
    }
}