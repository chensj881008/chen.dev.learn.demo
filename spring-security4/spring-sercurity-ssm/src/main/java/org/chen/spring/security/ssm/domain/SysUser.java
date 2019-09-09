package org.chen.spring.security.ssm.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

import lombok.EqualsAndHashCode;
import org.chen.spring.security.ssm.domain.BaseDomain;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Dev Ops
 * @title 用户表
 * @email Winning Devops Apollo Client
 * @package org.chen.spring.security.ssm.domain
 * @date 2019-09-09 22:14:05
 */
@EqualsAndHashCode(callSuper = true)
@Alias("sysUser")
@Data
public class SysUser extends BaseDomain implements UserDetails {

    private static final long serialVersionUID = -1L;

    /**
     * 字段名：USER_ID
     * 备注:
     * 默认值：无
     */
    private Integer userId;
    /**
     * 字段名：USER_NAME
     * 备注:
     * 默认值：无
     */
    private String userName;
    /**
     * 字段名：REAL_NAME
     * 备注:
     * 默认值：无
     */
    private String realName;
    /**
     * 字段名：PASSWORD
     * 备注:
     * 默认值：无
     */
    private String password;
    /**
     * 字段名：CREATE_DATE
     * 备注:
     * 默认值：无
     */
    private Date createDate;
    /**
     * 字段名：LAST_LOGIN_DATE
     * 备注:
     * 默认值：无
     */
    private Date lastLoginDate;
    /**
     * 字段名：ACCOUNT_ENABLED
     * 备注:
     * 默认值：无
     */
    private Integer accountEnabled;
    /**
     * 字段名：ACCOUNT_EXPIRED
     * 备注:
     * 默认值：无
     */
    private Integer accountExpired;
    /**
     * 字段名：ACCOUNT_LOCKED
     * 备注:
     * 默认值：无
     */
    private Integer accountLocked;
    /**
     * 字段名：PASSWORD_EXPIRED
     * 备注:
     * 默认值：无
     */
    private Integer passwordExpired;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpired == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountLocked == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return passwordExpired == 1;
    }

    @Override
    public boolean isEnabled() {
        return accountEnabled == 1;
    }
}
