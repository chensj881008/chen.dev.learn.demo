package org.chen.spring.security.ssm.mapper;



import java.util.List;  

import org.springframework.dao.DataAccessException;  

import org.chen.spring.security.ssm.domain.SysRole;  



import org.springframework.stereotype.Repository;
/**
* @author Dev Ops
* @title 角色表DAO接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.mapper
* @date 2019-09-09 22:14:03
*/
@Repository
public interface SysRoleDao {
    /**
     * SysRole 数据新增
     * @param sysRole
     * @return int
     * @throws DataAccessException
     */
    public int insertSysRole(SysRole sysRole) throws DataAccessException;
    /**
     * SysRole 数据更新
     * @param sysRole
     * @return int
     * @throws DataAccessException
     */
    public int updateSysRole(SysRole sysRole) throws DataAccessException;
    /**
     * SysRole 数据删除
     * @param sysRole
     * @return int
     * @throws DataAccessException
     */
    public int deleteSysRole(SysRole sysRole) throws DataAccessException;
    /**
     * SysRole 单条数据查询
     * @param sysRole
     * @return SysRole
     * @throws DataAccessException
     */
    public SysRole selectSysRole(SysRole sysRole) throws DataAccessException;
    /**
     * SysRole 统计数据条数
     * @param sysRole
     * @return Object
     * @throws DataAccessException
     */
    public int selectSysRoleCount(SysRole sysRole) throws DataAccessException;
    /**
     * SysRole 查询List
     * @param sysRole
     * @return SysRoleList
     * @throws DataAccessException
     */
    public List<SysRole> selectSysRoleList(SysRole sysRole) throws DataAccessException;
    /**
     * SysRole 分页查询
     * @param sysRole
     * @return SysRoleList
     * @throws DataAccessException
     */
    public List<SysRole> selectSysRolePageList(SysRole sysRole) throws DataAccessException;
}