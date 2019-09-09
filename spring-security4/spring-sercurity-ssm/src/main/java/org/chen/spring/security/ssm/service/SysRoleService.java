package org.chen.spring.security.ssm.service;

import java.util.List;  

import org.chen.spring.security.ssm.domain.SysRole;  


/**
* @author Dev Ops
* @title 角色表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service
* @date 2019-09-09 22:14:03
*/
public interface SysRoleService {
    /**
     * SysRole服务接口 数据新增
     * @param sysRole
     * @return int
     */
    public int createSysRole(SysRole sysRole);
    /**
     * SysRole服务接口 数据更新
     * @param sysRole
     * @return int
     */
    public int modifySysRole(SysRole sysRole);
    /**
     * SysRole服务接口 数据删除
     * @param sysRole
     * @return int
     */
    public int removeSysRole(SysRole sysRole);
    /**
     * SysRole服务接口 单条数据查询
     * @param sysRole
     * @return int
     */
    public SysRole getSysRole(SysRole sysRole);
    /**
     * SysRole服务接口 统计数据条数
     * @param sysRole
     * @return SysRole
     */
    public int getSysRoleCount(SysRole sysRole);
    /**
     * SysRole服务接口 查询List
     * @param sysRole
     * @return SysRoleList
     */
    public List<SysRole> getSysRoleList(SysRole sysRole);
    /**
     * SysRole服务接口 分页查询
     * @param sysRole
     * @return SysRoleList
     */
    public List<SysRole> getSysRolePageList(SysRole sysRole);
}