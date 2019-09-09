package org.chen.spring.security.ssm.service;

import java.util.List;  

import org.chen.spring.security.ssm.domain.SysUser;  


/**
* @author Dev Ops
* @title 用户表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service
* @date 2019-09-09 22:14:05
*/
public interface SysUserService {
    /**
     * SysUser服务接口 数据新增
     * @param sysUser
     * @return int
     */
    public int createSysUser(SysUser sysUser);
    /**
     * SysUser服务接口 数据更新
     * @param sysUser
     * @return int
     */
    public int modifySysUser(SysUser sysUser);
    /**
     * SysUser服务接口 数据删除
     * @param sysUser
     * @return int
     */
    public int removeSysUser(SysUser sysUser);
    /**
     * SysUser服务接口 单条数据查询
     * @param sysUser
     * @return int
     */
    public SysUser getSysUser(SysUser sysUser);
    /**
     * SysUser服务接口 统计数据条数
     * @param sysUser
     * @return SysUser
     */
    public int getSysUserCount(SysUser sysUser);
    /**
     * SysUser服务接口 查询List
     * @param sysUser
     * @return SysUserList
     */
    public List<SysUser> getSysUserList(SysUser sysUser);
    /**
     * SysUser服务接口 分页查询
     * @param sysUser
     * @return SysUserList
     */
    public List<SysUser> getSysUserPageList(SysUser sysUser);
}