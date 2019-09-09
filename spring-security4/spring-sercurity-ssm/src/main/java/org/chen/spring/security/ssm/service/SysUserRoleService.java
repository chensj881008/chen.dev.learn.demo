package org.chen.spring.security.ssm.service;

import java.util.List;  

import org.chen.spring.security.ssm.domain.SysUserRole;  


/**
* @author Dev Ops
* @title 用户角色表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service
* @date 2019-09-09 22:14:05
*/
public interface SysUserRoleService {
    /**
     * SysUserRole服务接口 数据新增
     * @param sysUserRole
     * @return int
     */
    public int createSysUserRole(SysUserRole sysUserRole);
    /**
     * SysUserRole服务接口 数据更新
     * @param sysUserRole
     * @return int
     */
    public int modifySysUserRole(SysUserRole sysUserRole);
    /**
     * SysUserRole服务接口 数据删除
     * @param sysUserRole
     * @return int
     */
    public int removeSysUserRole(SysUserRole sysUserRole);
    /**
     * SysUserRole服务接口 单条数据查询
     * @param sysUserRole
     * @return int
     */
    public SysUserRole getSysUserRole(SysUserRole sysUserRole);
    /**
     * SysUserRole服务接口 统计数据条数
     * @param sysUserRole
     * @return SysUserRole
     */
    public int getSysUserRoleCount(SysUserRole sysUserRole);
    /**
     * SysUserRole服务接口 查询List
     * @param sysUserRole
     * @return SysUserRoleList
     */
    public List<SysUserRole> getSysUserRoleList(SysUserRole sysUserRole);
    /**
     * SysUserRole服务接口 分页查询
     * @param sysUserRole
     * @return SysUserRoleList
     */
    public List<SysUserRole> getSysUserRolePageList(SysUserRole sysUserRole);
}