package org.chen.spring.security.ssm.mapper;



import java.util.List;  

import org.springframework.dao.DataAccessException;  

import org.chen.spring.security.ssm.domain.SysRoleAuth;  



import org.springframework.stereotype.Repository;
/**
* @author Dev Ops
* @title 角色权限表DAO接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.mapper
* @date 2019-09-09 22:14:04
*/
@Repository
public interface SysRoleAuthDao {
    /**
     * SysRoleAuth 数据新增
     * @param sysRoleAuth
     * @return int
     * @throws DataAccessException
     */
    public int insertSysRoleAuth(SysRoleAuth sysRoleAuth) throws DataAccessException;
    /**
     * SysRoleAuth 数据更新
     * @param sysRoleAuth
     * @return int
     * @throws DataAccessException
     */
    public int updateSysRoleAuth(SysRoleAuth sysRoleAuth) throws DataAccessException;
    /**
     * SysRoleAuth 数据删除
     * @param sysRoleAuth
     * @return int
     * @throws DataAccessException
     */
    public int deleteSysRoleAuth(SysRoleAuth sysRoleAuth) throws DataAccessException;
    /**
     * SysRoleAuth 单条数据查询
     * @param sysRoleAuth
     * @return SysRoleAuth
     * @throws DataAccessException
     */
    public SysRoleAuth selectSysRoleAuth(SysRoleAuth sysRoleAuth) throws DataAccessException;
    /**
     * SysRoleAuth 统计数据条数
     * @param sysRoleAuth
     * @return Object
     * @throws DataAccessException
     */
    public int selectSysRoleAuthCount(SysRoleAuth sysRoleAuth) throws DataAccessException;
    /**
     * SysRoleAuth 查询List
     * @param sysRoleAuth
     * @return SysRoleAuthList
     * @throws DataAccessException
     */
    public List<SysRoleAuth> selectSysRoleAuthList(SysRoleAuth sysRoleAuth) throws DataAccessException;
    /**
     * SysRoleAuth 分页查询
     * @param sysRoleAuth
     * @return SysRoleAuthList
     * @throws DataAccessException
     */
    public List<SysRoleAuth> selectSysRoleAuthPageList(SysRoleAuth sysRoleAuth) throws DataAccessException;
}