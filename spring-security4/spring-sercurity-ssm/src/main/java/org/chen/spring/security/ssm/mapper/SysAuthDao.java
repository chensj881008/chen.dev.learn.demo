package org.chen.spring.security.ssm.mapper;



import java.util.List;  

import org.springframework.dao.DataAccessException;  

import org.chen.spring.security.ssm.domain.SysAuth;  



import org.springframework.stereotype.Repository;
/**
* @author Dev Ops
* @title 权限表DAO接口
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.mapper
* @date 2019-09-09 22:14:03
*/
@Repository
public interface SysAuthDao {
    /**
     * SysAuth 数据新增
     * @param sysAuth
     * @return int
     * @throws DataAccessException
     */
    public int insertSysAuth(SysAuth sysAuth) throws DataAccessException;
    /**
     * SysAuth 数据更新
     * @param sysAuth
     * @return int
     * @throws DataAccessException
     */
    public int updateSysAuth(SysAuth sysAuth) throws DataAccessException;
    /**
     * SysAuth 数据删除
     * @param sysAuth
     * @return int
     * @throws DataAccessException
     */
    public int deleteSysAuth(SysAuth sysAuth) throws DataAccessException;
    /**
     * SysAuth 单条数据查询
     * @param sysAuth
     * @return SysAuth
     * @throws DataAccessException
     */
    public SysAuth selectSysAuth(SysAuth sysAuth) throws DataAccessException;
    /**
     * SysAuth 统计数据条数
     * @param sysAuth
     * @return Object
     * @throws DataAccessException
     */
    public int selectSysAuthCount(SysAuth sysAuth) throws DataAccessException;
    /**
     * SysAuth 查询List
     * @param sysAuth
     * @return SysAuthList
     * @throws DataAccessException
     */
    public List<SysAuth> selectSysAuthList(SysAuth sysAuth) throws DataAccessException;
    /**
     * SysAuth 分页查询
     * @param sysAuth
     * @return SysAuthList
     * @throws DataAccessException
     */
    public List<SysAuth> selectSysAuthPageList(SysAuth sysAuth) throws DataAccessException;
}