package org.chen.spring.security.ssm.service;

import java.util.List;  

import org.chen.spring.security.ssm.domain.SysAuth;  


/**
* @author Dev Ops
* @title 权限表服务接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.service
* @date 2019-09-09 22:14:03
*/
public interface SysAuthService {
    /**
     * SysAuth服务接口 数据新增
     * @param sysAuth
     * @return int
     */
    public int createSysAuth(SysAuth sysAuth);
    /**
     * SysAuth服务接口 数据更新
     * @param sysAuth
     * @return int
     */
    public int modifySysAuth(SysAuth sysAuth);
    /**
     * SysAuth服务接口 数据删除
     * @param sysAuth
     * @return int
     */
    public int removeSysAuth(SysAuth sysAuth);
    /**
     * SysAuth服务接口 单条数据查询
     * @param sysAuth
     * @return int
     */
    public SysAuth getSysAuth(SysAuth sysAuth);
    /**
     * SysAuth服务接口 统计数据条数
     * @param sysAuth
     * @return SysAuth
     */
    public int getSysAuthCount(SysAuth sysAuth);
    /**
     * SysAuth服务接口 查询List
     * @param sysAuth
     * @return SysAuthList
     */
    public List<SysAuth> getSysAuthList(SysAuth sysAuth);
    /**
     * SysAuth服务接口 分页查询
     * @param sysAuth
     * @return SysAuthList
     */
    public List<SysAuth> getSysAuthPageList(SysAuth sysAuth);
}