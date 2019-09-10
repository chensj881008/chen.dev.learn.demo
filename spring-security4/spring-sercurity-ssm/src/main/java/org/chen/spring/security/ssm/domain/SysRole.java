package org.chen.spring.security.ssm.domain;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import org.chen.spring.security.ssm.domain.BaseDomain;

import org.apache.ibatis.type.Alias; 

import lombok.Data;

/**
 * @author Dev Ops
 * @title 角色表
 * @email Winning Devops Apollo Client
 * @package org.chen.spring.security.ssm.domain
 * @date 2019-09-09 22:14:03
 */
@Alias("sysRole")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 字段名：ROLE_ID
     * 备注: 
     * 默认值：无
     */
    private Integer roleId;
    /**
     * 字段名：ROLE_NAME
     * 备注: 
     * 默认值：无
     */
    private String roleName;
    /**
     * 字段名：ROLE_DESC
     * 备注: 
     * 默认值：无
     */
    private String roleDesc;

}