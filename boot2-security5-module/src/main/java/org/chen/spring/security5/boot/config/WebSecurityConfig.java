package org.chen.spring.security5.boot.config;

import org.chen.spring.security5.boot.service.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Spring Security 的配置类，该类的三个注解分别是标识该类是配置类、开启 Security 服务、开
 * 启全局 Security 注解。
 * 首先将我们自定义的 userDetailsService 注入进来，在configure()方法中使用auth.userDetailsService()
 * 方法替换掉默认的 userDetailsService。
 * <p>
 * 这里我们还指定了密码的加密方式（5.0 版本强制要求设置），因为我们数据库是明文存储的，所以明文返回即可
 *
 * @author chensj
 * @date 2019-09-11 15:54
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailServiceImpl userDetailService;
    @Autowired
    private DataSource dataSource;

    /**
     * 注册一个bean 用于存储token数据
     *
     * @return tokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        // 直接返回明文密码
                        return charSequence.toString();
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        return s.equals(charSequence.toString());
                    }
                });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 任何请求 都需要认证
                // 如果存在不需要认证的url,可以使用 .antMatchers().permitAll()来设置
                .anyRequest().authenticated()
                .and()
                // 设置登录页
                .formLogin().loginPage("/login")
                // 设置登录成功也
                .defaultSuccessUrl("/").permitAll()
                // 登录失败Url
                .failureUrl("/login/error")
                // 自定义登陆用户名和密码参数，默认为username和password
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                // 退出
                .logout().permitAll()
                // 自动登录
                .and()
                // 自动登录
                // 只使用 rememberMe 则是保存在token中
                // 使用tokenRepository后，会产生一个token，与用户名信息进行对应存放在数据库persistent_logins中
                .rememberMe()
                // 指定token Repository
                .tokenRepository(persistentTokenRepository())
                // 有效时间：单位s
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailService);
        ;
        // 关闭CSRF跨域
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**", "/favicon.ico", "/static/**");
    }
}
