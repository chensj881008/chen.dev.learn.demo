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
* @title 角色表控制类
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.controller;
* @date 2019-09-09 22:14:03
*/
@RestController
@RequestMapping(value = "sysRole")
@CrossOrigin
public class SysRoleController  extends BaseController{
    /**
     * 数据分页查询
     * @param sysRole 实体类
     * @param row 分页参数
     * @return map
     */
    @GetMapping(value = "list")
    public Map<String, Object> list(SysRole sysRole, Row row) {
        sysRole.setRow(row);
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("total", getFacade().getSysRoleService().getSysRoleCount(sysRole));
        resultMap.put("rows", getFacade().getSysRoleService().getSysRolePageList(sysRole));
        return resultMap;
    }
    /**
     * 数据新增
     * @param sysRole 实体类
     * @return map
     */
    @PostMapping(value="add")
    public Map<String, Object> add(SysRole sysRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleService().createSysRole(sysRole));
        return resultMap;
    }
    /**
     * 数据更新
     * @param sysRole 实体类
     * @return map
     */
    @PostMapping(value="update")
    public Map<String, Object> update(SysRole sysRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleService().modifySysRole(sysRole));
        return resultMap;
    }
    /**
     * 数据获取
     * @param sysRole 实体类
     * @return map
     */
    @PostMapping(value="get")
    public Map<String, Object> get(SysRole sysRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleService().getSysRole(sysRole));
        return resultMap;
    }
    /**
     * 数据删除
     * @param sysRole 实体类
     * @return map
     */
    @PostMapping(value="delete")
    public Map<String, Object> delete(SysRole sysRole){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleService().removeSysRole(sysRole));
        return resultMap;
    }
}
