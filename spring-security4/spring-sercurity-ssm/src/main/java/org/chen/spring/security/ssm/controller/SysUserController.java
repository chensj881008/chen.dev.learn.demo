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
* @title 用户表控制类
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.controller;
* @date 2019-09-09 22:14:05
*/
@RestController
@RequestMapping(value = "sysUser")
@CrossOrigin
public class SysUserController  extends BaseController{
    /**
     * 数据分页查询
     * @param sysUser 实体类
     * @param row 分页参数
     * @return map
     */
    @GetMapping(value = "list")
    public Map<String, Object> list(SysUser sysUser, Row row) {
        sysUser.setRow(row);
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("total", getFacade().getSysUserService().getSysUserCount(sysUser));
        resultMap.put("rows", getFacade().getSysUserService().getSysUserPageList(sysUser));
        return resultMap;
    }
    /**
     * 数据新增
     * @param sysUser 实体类
     * @return map
     */
    @PostMapping(value="add")
    public Map<String, Object> add(SysUser sysUser){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserService().createSysUser(sysUser));
        return resultMap;
    }
    /**
     * 数据更新
     * @param sysUser 实体类
     * @return map
     */
    @PostMapping(value="update")
    public Map<String, Object> update(SysUser sysUser){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserService().modifySysUser(sysUser));
        return resultMap;
    }
    /**
     * 数据获取
     * @param sysUser 实体类
     * @return map
     */
    @PostMapping(value="get")
    public Map<String, Object> get(SysUser sysUser){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserService().getSysUser(sysUser));
        return resultMap;
    }
    /**
     * 数据删除
     * @param sysUser 实体类
     * @return map
     */
    @PostMapping(value="delete")
    public Map<String, Object> delete(SysUser sysUser){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysUserService().removeSysUser(sysUser));
        return resultMap;
    }
}
