package org.chen.spring.security5.boot.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author chensj
 * @date 2019-09-12 11:25
 */
@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = -7103488938939776567L;

    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;

    private List permissions;

    // 省略除permissions外的getter/setter

    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }

}
