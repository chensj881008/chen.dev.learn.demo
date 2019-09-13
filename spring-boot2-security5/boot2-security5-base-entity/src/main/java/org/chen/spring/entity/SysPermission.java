package org.chen.spring.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author chensj
 * @date 2019-09-14 上午12:09
 */
@Data
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -7194099908542725845L;


    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;
    /**
     * 该字段将 permission 按逗号分割为了 list
     */
    private List permissions;

    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }

}
