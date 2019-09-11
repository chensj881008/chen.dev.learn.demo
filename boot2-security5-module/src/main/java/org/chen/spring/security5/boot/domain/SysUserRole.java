package org.chen.spring.security5.boot.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chensj
 * @date 2019-09-11 15:37
 */
@Data
public class SysUserRole implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;

}
