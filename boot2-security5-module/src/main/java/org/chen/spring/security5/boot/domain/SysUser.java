package org.chen.spring.security5.boot.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chensj
 * @date 2019-09-11 15:36
 */
@Data
public class SysUser implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;

}
