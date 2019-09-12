package org.chen.spring.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色信息
 *
 * @author chensj
 * @date 2019-09-11 15:37
 */
@Data
public class SysUserRole implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;

}
