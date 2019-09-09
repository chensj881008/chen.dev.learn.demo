package org.chen.spring.security.ssm.mapper;



import java.util.List;  

import org.springframework.dao.DataAccessException;  

import org.chen.spring.security.ssm.domain.SysUserRole;  



import org.springframework.stereotype.Repository;
/**
* @author Dev Ops
* @title 用户角色表DAO接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.mapper
* @date 2019-09-09 22:14:05
*/
@Repository
public interface SysUserRoleDao {
    /**
     * SysUserRole 数据新增
     * @param sysUserRole
     * @return int
     * @throws DataAccessException
     */
    public int insertSysUserRole(SysUserRole sysUserRole) throws DataAccessException;
    /**
     * SysUserRole 数据更新
     * @param sysUserRole
     * @return int
     * @throws DataAccessException
     */
    public int updateSysUserRole(SysUserRole sysUserRole) throws DataAccessException;
    /**
     * SysUserRole 数据删除
     * @param sysUserRole
     * @return int
     * @throws DataAccessException
     */
    public int deleteSysUserRole(SysUserRole sysUserRole) throws DataAccessException;
    /**
     * SysUserRole 单条数据查询
     * @param sysUserRole
     * @return SysUserRole
     * @throws DataAccessException
     */
    public SysUserRole selectSysUserRole(SysUserRole sysUserRole) throws DataAccessException;
    /**
     * SysUserRole 统计数据条数
     * @param sysUserRole
     * @return Object
     * @throws DataAccessException
     */
    public int selectSysUserRoleCount(SysUserRole sysUserRole) throws DataAccessException;
    /**
     * SysUserRole 查询List
     * @param sysUserRole
     * @return SysUserRoleList
     * @throws DataAccessException
     */
    public List<SysUserRole> selectSysUserRoleList(SysUserRole sysUserRole) throws DataAccessException;
    /**
     * SysUserRole 分页查询
     * @param sysUserRole
     * @return SysUserRoleList
     * @throws DataAccessException
     */
    public List<SysUserRole> selectSysUserRolePageList(SysUserRole sysUserRole) throws DataAccessException;
}