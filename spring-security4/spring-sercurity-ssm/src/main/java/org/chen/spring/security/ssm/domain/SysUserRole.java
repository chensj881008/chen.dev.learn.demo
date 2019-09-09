package org.chen.spring.security.ssm.domain;

import java.io.Serializable; 

import org.chen.spring.security.ssm.domain.BaseDomain;

import org.apache.ibatis.type.Alias; 

import lombok.Data;

/**
 * @author Dev Ops
 * @title 用户角色表
 * @email Winning Devops Apollo Client
 * @package org.chen.spring.security.ssm.domain
 * @date 2019-09-09 22:14:05
 */
@Alias("sysUserRole")
@Data
public class SysUserRole extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 字段名：USER_ID
     * 备注: 
     * 默认值：无
     */
    private Integer userId;
    /**
     * 字段名：ROLE_ID
     * 备注: 
     * 默认值：无
     */
    private Integer roleId;

}