package org.chen.spring.security4.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户登录后权限信息数据
 *
 * @author chensj
 */
public class SpringSecurityUserDetailService implements UserDetailsService {
    /**
     * 通过用户名获取用户信息
     *
     * @param username 用户名
     * @return userDetail信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // UserDetails 封装用户数据的接口
        // 获取用户信息
        User user = null;
        if ("admin".equals(username)) {
            /*
             * 下面这句话等同于配置文件中的
             * <security:user name="admin" password="1" authorities="ROLE_ADMIN"/>
             * AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN")
             * 是Spring Security封装的一种权限信息转换的工具类方法
             */
            user = new User("admin", "1",
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        } else if ("chensj".equals(username)) {
            user = new User("chensj", "1",
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        }
        return user;
    }
}
