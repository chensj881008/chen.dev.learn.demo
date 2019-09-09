package org.chen.spring.security.ssm.service.impl;

import org.chen.spring.security.ssm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
* @author Dev Ops
* @title FacadeImpl
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service.impl;
* @date 2019-09-09 22:14:05
*/
@Component
public class FacadeImpl implements Facade{

    @Autowired
    SysAuthService sysAuthService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleAuthService sysRoleAuthService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleService sysUserRoleService;


    @Override
    public SysAuthService getSysAuthService(){
        return sysAuthService;
    }

    @Override
    public SysRoleService getSysRoleService(){
        return sysRoleService;
    }

    @Override
    public SysRoleAuthService getSysRoleAuthService(){
        return sysRoleAuthService;
    }

    @Override
    public SysUserService getSysUserService(){
        return sysUserService;
    }

    @Override
    public SysUserRoleService getSysUserRoleService(){
        return sysUserRoleService;
    }

}