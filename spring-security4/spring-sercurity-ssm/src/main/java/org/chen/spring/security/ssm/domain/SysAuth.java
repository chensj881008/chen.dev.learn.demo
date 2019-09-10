package org.chen.spring.security.ssm.domain;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import org.chen.spring.security.ssm.domain.BaseDomain;

import org.apache.ibatis.type.Alias; 

import lombok.Data;

/**
 * @author Dev Ops
 * @title 权限表
 * @email Winning Devops Apollo Client
 * @package org.chen.spring.security.ssm.domain
 * @date 2019-09-09 22:14:03
 */
@Alias("sysAuth")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAuth extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 字段名：AUTH_ID
     * 备注: 
     * 默认值：无
     */
    private Integer authId;
    /**
     * 字段名：AUTH_NAME
     * 备注: 
     * 默认值：无
     */
    private String authName;
    /**
     * 字段名：AUTH_FLAG
     * 备注: 
     * 默认值：无
     */
    private String authFlag;

}