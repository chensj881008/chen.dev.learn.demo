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
* @title 角色权限表控制类
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.controller;
* @date 2019-09-09 22:14:04
*/
@RestController
@RequestMapping(value = "sysRoleAuth")
@CrossOrigin
public class SysRoleAuthController  extends BaseController{
    /**
     * 数据分页查询
     * @param sysRoleAuth 实体类
     * @param row 分页参数
     * @return map
     */
    @GetMapping(value = "list")
    public Map<String, Object> list(SysRoleAuth sysRoleAuth, Row row) {
        sysRoleAuth.setRow(row);
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("total", getFacade().getSysRoleAuthService().getSysRoleAuthCount(sysRoleAuth));
        resultMap.put("rows", getFacade().getSysRoleAuthService().getSysRoleAuthPageList(sysRoleAuth));
        return resultMap;
    }
    /**
     * 数据新增
     * @param sysRoleAuth 实体类
     * @return map
     */
    @PostMapping(value="add")
    public Map<String, Object> add(SysRoleAuth sysRoleAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleAuthService().createSysRoleAuth(sysRoleAuth));
        return resultMap;
    }
    /**
     * 数据更新
     * @param sysRoleAuth 实体类
     * @return map
     */
    @PostMapping(value="update")
    public Map<String, Object> update(SysRoleAuth sysRoleAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleAuthService().modifySysRoleAuth(sysRoleAuth));
        return resultMap;
    }
    /**
     * 数据获取
     * @param sysRoleAuth 实体类
     * @return map
     */
    @PostMapping(value="get")
    public Map<String, Object> get(SysRoleAuth sysRoleAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleAuthService().getSysRoleAuth(sysRoleAuth));
        return resultMap;
    }
    /**
     * 数据删除
     * @param sysRoleAuth 实体类
     * @return map
     */
    @PostMapping(value="delete")
    public Map<String, Object> delete(SysRoleAuth sysRoleAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysRoleAuthService().removeSysRoleAuth(sysRoleAuth));
        return resultMap;
    }
}
