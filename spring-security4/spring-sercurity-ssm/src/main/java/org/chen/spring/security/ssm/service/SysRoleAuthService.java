package org.chen.spring.security.ssm.service;

import java.util.List;  

import org.chen.spring.security.ssm.domain.SysRoleAuth;  


/**
* @author Dev Ops
* @title 角色权限表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service
* @date 2019-09-09 22:14:04
*/
public interface SysRoleAuthService {
    /**
     * SysRoleAuth服务接口 数据新增
     * @param sysRoleAuth
     * @return int
     */
    public int createSysRoleAuth(SysRoleAuth sysRoleAuth);
    /**
     * SysRoleAuth服务接口 数据更新
     * @param sysRoleAuth
     * @return int
     */
    public int modifySysRoleAuth(SysRoleAuth sysRoleAuth);
    /**
     * SysRoleAuth服务接口 数据删除
     * @param sysRoleAuth
     * @return int
     */
    public int removeSysRoleAuth(SysRoleAuth sysRoleAuth);
    /**
     * SysRoleAuth服务接口 单条数据查询
     * @param sysRoleAuth
     * @return int
     */
    public SysRoleAuth getSysRoleAuth(SysRoleAuth sysRoleAuth);
    /**
     * SysRoleAuth服务接口 统计数据条数
     * @param sysRoleAuth
     * @return SysRoleAuth
     */
    public int getSysRoleAuthCount(SysRoleAuth sysRoleAuth);
    /**
     * SysRoleAuth服务接口 查询List
     * @param sysRoleAuth
     * @return SysRoleAuthList
     */
    public List<SysRoleAuth> getSysRoleAuthList(SysRoleAuth sysRoleAuth);
    /**
     * SysRoleAuth服务接口 分页查询
     * @param sysRoleAuth
     * @return SysRoleAuthList
     */
    public List<SysRoleAuth> getSysRoleAuthPageList(SysRoleAuth sysRoleAuth);
}