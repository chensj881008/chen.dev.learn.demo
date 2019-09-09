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
* @title 权限表控制类
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.controller;
* @date 2019-09-09 22:14:03
*/
@RestController
@RequestMapping(value = "sysAuth")
@CrossOrigin
public class SysAuthController  extends BaseController{
    /**
     * 数据分页查询
     * @param sysAuth 实体类
     * @param row 分页参数
     * @return map
     */
    @GetMapping(value = "list")
    public Map<String, Object> list(SysAuth sysAuth, Row row) {
        sysAuth.setRow(row);
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("total", getFacade().getSysAuthService().getSysAuthCount(sysAuth));
        resultMap.put("rows", getFacade().getSysAuthService().getSysAuthPageList(sysAuth));
        return resultMap;
    }
    /**
     * 数据新增
     * @param sysAuth 实体类
     * @return map
     */
    @PostMapping(value="add")
    public Map<String, Object> add(SysAuth sysAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysAuthService().createSysAuth(sysAuth));
        return resultMap;
    }
    /**
     * 数据更新
     * @param sysAuth 实体类
     * @return map
     */
    @PostMapping(value="update")
    public Map<String, Object> update(SysAuth sysAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysAuthService().modifySysAuth(sysAuth));
        return resultMap;
    }
    /**
     * 数据获取
     * @param sysAuth 实体类
     * @return map
     */
    @PostMapping(value="get")
    public Map<String, Object> get(SysAuth sysAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysAuthService().getSysAuth(sysAuth));
        return resultMap;
    }
    /**
     * 数据删除
     * @param sysAuth 实体类
     * @return map
     */
    @PostMapping(value="delete")
    public Map<String, Object> delete(SysAuth sysAuth){
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("data", getFacade().getSysAuthService().removeSysAuth(sysAuth));
        return resultMap;
    }
}
