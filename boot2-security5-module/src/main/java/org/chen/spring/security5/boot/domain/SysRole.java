package org.chen.spring.security5.boot.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 *
 * @author chensj
 * @date 2019-09-11 15:37
 */
@Data
public class SysRole implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}
