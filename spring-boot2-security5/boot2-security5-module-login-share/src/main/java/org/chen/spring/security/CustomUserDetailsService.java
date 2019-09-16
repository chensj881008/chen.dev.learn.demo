package org.chen.spring.security;

import org.chen.spring.entity.SysRole;
import org.chen.spring.entity.SysUser;
import org.chen.spring.entity.SysUserRole;
import org.chen.spring.service.SysRoleService;
import org.chen.spring.service.SysUserRoleService;
import org.chen.spring.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chensj
 * @date 2019-09-12 14:49
 */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 获取用户信息
        SysUser user = sysUserService.selectByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 获取用户角色
        List<SysUserRole> roles = sysUserRoleService.listByUserId(user.getId());
        // 获取角色细腻
        for (SysUserRole userRole : roles) {
            SysRole role = sysRoleService.selectById(userRole.getRoleId());
            // 分配角色权限
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        // 返回UserDetails实现类
        return new User(user.getName(), user.getPassword(), authorities);
    }
}
