package org.chen.spring.security.ssm.mapper;



import java.util.List;  

import org.springframework.dao.DataAccessException;  

import org.chen.spring.security.ssm.domain.SysUser;  



import org.springframework.stereotype.Repository;
/**
* @author Dev Ops
* @title 用户表DAO接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.mapper
* @date 2019-09-09 22:14:05
*/
@Repository
public interface SysUserDao {
    /**
     * SysUser 数据新增
     * @param sysUser
     * @return int
     * @throws DataAccessException
     */
    public int insertSysUser(SysUser sysUser) throws DataAccessException;
    /**
     * SysUser 数据更新
     * @param sysUser
     * @return int
     * @throws DataAccessException
     */
    public int updateSysUser(SysUser sysUser) throws DataAccessException;
    /**
     * SysUser 数据删除
     * @param sysUser
     * @return int
     * @throws DataAccessException
     */
    public int deleteSysUser(SysUser sysUser) throws DataAccessException;
    /**
     * SysUser 单条数据查询
     * @param sysUser
     * @return SysUser
     * @throws DataAccessException
     */
    public SysUser selectSysUser(SysUser sysUser) throws DataAccessException;
    /**
     * SysUser 统计数据条数
     * @param sysUser
     * @return Object
     * @throws DataAccessException
     */
    public int selectSysUserCount(SysUser sysUser) throws DataAccessException;
    /**
     * SysUser 查询List
     * @param sysUser
     * @return SysUserList
     * @throws DataAccessException
     */
    public List<SysUser> selectSysUserList(SysUser sysUser) throws DataAccessException;
    /**
     * SysUser 分页查询
     * @param sysUser
     * @return SysUserList
     * @throws DataAccessException
     */
    public List<SysUser> selectSysUserPageList(SysUser sysUser) throws DataAccessException;
}