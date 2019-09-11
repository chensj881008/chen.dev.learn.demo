package org.chen.spring.security5.boot.service;

import org.chen.spring.security5.boot.domain.SysRole;
import org.chen.spring.security5.boot.domain.SysUser;
import org.chen.spring.security5.boot.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义UserDetailsService，将用户信息和权限信息注入进来
 * 接口由spring security提供，我们需要重写loadUserByUsername方法，
 * 然后返回用户信息{@link UserDetails},这个类中包含用户名、密码和权限信息三块
 * 多数情况下，都是User直接实现UserDetail返回
 *
 * @author chensj
 * @date 2019-09-11 15:49
 */
@Service(value = "userDetailsService")
public class CustomUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        SysUser user = userService.selectByName(username);

        // 判断用户是否存在
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.selectById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        // 返回UserDetails实现类
        return new User(user.getName(), user.getPassword(), authorities);
    }
}
