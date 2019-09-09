package org.chen.spring.security.ssm.controller;

import org.chen.spring.security.ssm.domain.*;
import org.chen.spring.security.ssm.domain.support.Row;
import org.chen.spring.security.ssm.controller.BaseController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
* @author Dev Ops
* @title 用户角色表控制类
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.controller;
* @date 2019-09-09 22:14:05
*/
@RestController
@RequestMapping(value = "sysUserRole")
@CrossOrigin
public class SysUserRoleController  extends BaseController{
    /**
     * 数据分页查询
     * @param sysUserRole 实体类
     * @param row 分页参数
     * @return map
     */
    @GetMapping(value = "list")
    public Map<String, Object> list(SysUserRole sysUserRole, Row row) {
        sysUserRole.setRow(row);
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("total", getFacade().getSysUserRoleService().getSysUserRoleCount(sysUserRole));
        resultMap.put("rows", getFacade().getSysUserRoleService().getSysUserRolePageList(sysUserRole));
        return resultMap;
    }
    /**
     * 数据新增
     * @param sysUserRole 实体类
     * @return map
     */
    @PostMapping(value="add")
    public Map<String, Object> add(SysUserRole sysUserRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserRoleService().createSysUserRole(sysUserRole));
        return resultMap;
    }
    /**
     * 数据更新
     * @param sysUserRole 实体类
     * @return map
     */
    @PostMapping(value="update")
    public Map<String, Object> update(SysUserRole sysUserRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserRoleService().modifySysUserRole(sysUserRole));
        return resultMap;
    }
    /**
     * 数据获取
     * @param sysUserRole 实体类
     * @return map
     */
    @PostMapping(value="get")
    public Map<String, Object> get(SysUserRole sysUserRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserRoleService().getSysUserRole(sysUserRole));
        return resultMap;
    }
    /**
     * 数据删除
     * @param sysUserRole 实体类
     * @return map
     */
    @PostMapping(value="delete")
    public Map<String, Object> delete(SysUserRole sysUserRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserRoleService().removeSysUserRole(sysUserRole));
        return resultMap;
    }
}
