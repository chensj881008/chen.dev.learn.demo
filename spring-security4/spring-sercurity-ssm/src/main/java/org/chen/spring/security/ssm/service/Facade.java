package org.chen.spring.security.ssm.service;

/**
* @author Dev Ops
* @title Facade
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service;
* @date 2019-09-09 22:14:05
*/
public interface Facade {

   /**
     * SysAuthService服务接口获取
     * @return SysAuthService
     */
    SysAuthService getSysAuthService();

   /**
     * SysRoleService服务接口获取
     * @return SysRoleService
     */
    SysRoleService getSysRoleService();

   /**
     * SysRoleAuthService服务接口获取
     * @return SysRoleAuthService
     */
    SysRoleAuthService getSysRoleAuthService();

   /**
     * SysUserService服务接口获取
     * @return SysUserService
     */
    SysUserService getSysUserService();

   /**
     * SysUserRoleService服务接口获取
     * @return SysUserRoleService
     */
    SysUserRoleService getSysUserRoleService();

}