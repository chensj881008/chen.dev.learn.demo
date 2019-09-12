# Spring Security 5 学习笔记

## 1、入门项目

**Spring Boot 整合Spring Security 5 入门项目**

### 1.1 pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.8.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>org.chen.spring</groupId>
    <artifactId>boot2-security5-module</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>Spring Boot Security Maven Webapp</name>
    <description>Spring Boot Security Maven Webapp</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 1.2 建库与建表

#### 1.2.1  建库

```sql
-- 删除数据库
DROP DATABASE IF EXISTS security5;
-- 创建数据库
CREATE DATABASE security5 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

常见的权限模型是RBAC模型，权限控制有三层，即：用户<–>角色<–>权限，用户与角色是多对多，角色和权限也是多对多。这里我们先暂时不考虑权限，只考虑用户<–>角色。

#### 1.2.2 建表

* 创建用户表sys_user：
```sql
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

* 创建权限表sys_role：
```sql
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

* 创建用户-角色表sys_user_role：
```sql
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
* 初始化一下数据：

```sql
INSERT INTO `sys_role` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES ('2', 'ROLE_USER');

INSERT INTO `sys_user` VALUES ('1', 'admin', '123');
INSERT INTO `sys_user` VALUES ('2', 'chensj', '123');

INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
```

### 1.3 页面

#### 1.3.1 登录页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登陆</title>
</head>
<body>
<h1>登陆</h1>
<!--注意 action与method方法都是固定的，这是有spring security设定好的，可以在配置项目中修改-->
<form method="post" action="/login">
    <div>
        <label for="username">用户名：</label>
        <input id="username" type="text" name="username">
    </div>
    <div>
        <label for="password">密码：</label>
        <input id="password" type="password" name="password">
    </div>
    <div>
        <button type="submit">立即登陆</button>
    </div>
</form>
</body>
</html>
```

#### 1.3.2 主页

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>登陆成功</h1>
<a href="/admin">检测ROLE_ADMIN角色</a>
<a href="/user">检测ROLE_USER角色</a>
<button onclick="window.location.href='/logout'">退出登录</button>
</body>
</html>
```

### 1.4 application.yml

### 1.5 实体类、Mapper、Service、Controller

```java
// 角色表
@Data
public class SysRole implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}
```
```java
// 用户表
@Data
public class SysUser implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;

}
```
```java
// 用户角色
@Data
public class SysUserRole implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;

}
```

#### 1.5.1 Mapper

```java
@Mapper
@Repository
public interface SysRoleMapper {
    /**
     * 根据ID获取角色信息
     *
     * @param id 角色ID
     * @return role
     */
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(Integer id);
}
```
```java
@Mapper
@Repository
public interface SysUserMapper {
    /**
     * 根据ID获取用户信息
     *
     * @param id id
     * @return user
     */
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Integer id);

    /**
     * 根据用户名称获取用户信息
     *
     * @param name 用户名称
     * @return user
     */
    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);

}
```
```java
@Mapper
@Repository
public interface SysUserRoleMapper {
    /**
     * 根据用户获取角色ID信息
     *
     * @param userId 用户ID
     * @return list
     */
    @Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
    List<SysUserRole> listByUserId(Integer userId);
}
```

#### 1.5.2  Service

```java
/**
 * 角色Service
 *
 * @author chensj
 * @date 2019-09-11 15:42
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public SysRole selectById(Integer id) {
        return sysRoleMapper.selectById(id);
    }
}
```
```java
/**
 * 用户角色Service
 *
 * @author chensj
 * @date 2019-09-11 15:43
 */
@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public List<SysUserRole> listByUserId(Integer userId) {
        return sysUserRoleMapper.listByUserId(userId);
    }
}
```
```java
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser selectById(Integer id) {
        return sysUserMapper.selectById(id);
    }

    public SysUser selectByName(String name) {
        return sysUserMapper.selectByName(name);
    }

}
```


#### 1.5.3  Controller

```java
@Controller
public class LoginController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String showHome() {
        // 获取当前用户信息
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + name);

        return "home.html";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login.html";
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }
}
```

### 1.6 Spring Security配置

#### 1.6.1 UserDetailService

首先我们需要自定义 UserDetailsService ，将用户信息和权限注入进来。在Spring Security中，必须从UserDetailsService 中获取这些信息，所以我们需要重写 loadUserByUsername 方法，参数是用户输入的用户名。返回值是UserDetails，这是一个接口，一般使用它的子类org.springframework.security.core.userdetails.User，它有三个参数，分别是用户名、密码和权限集。

> 实际情况下，大多将 DAO 中的 User 类继承 `org.springframework.security.core.userdetails.User` 返回。

```java

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
```

#### 1.6.2 WebSecurityConfig

 Spring Security 的配置类，该类的三个注解分别是标识该类是配置类、开启 Security 服务、开启全局 Securtiy 注解。首先将我们自定义的 userDetailsService 注入进来，在 configure() 方法中使用 auth.userDetailsService() 方法替换掉默认的 userDetailsService。这里我们还指定了密码的加密方式（5.0 版本强制要求设置），因为我们数据库是明文存储的，所以明文返回即可，如下所示

```java
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
                // 自定义登陆用户名和密码参数，默认为username和password
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                // 退出
                .logout().permitAll();
        // 关闭CSRF跨域
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**","/favicon.ico","/static/**");
    }
}
```

## 2、自动登录

在上面的入门项目的基础上，增加自动登录的功能

自动登录的实现分为两种方式，一种是借助cookie的方式，一种是使用数据库方式

### 2.1 修改登录页面

在登陆页添加自动登录的选项，注意自动登录字段的 name 必须是 `remember-me` ：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登陆</title>
</head>
<body>
<h1>登陆</h1>
<!--注意 action与method方法都是固定的，这是有spring security设定好的，可以在配置项目中修改-->
<form method="post" action="/login">
    <div>
        <label for="username">用户名：</label>
        <input id="username" type="text" name="username">
    </div>
    <div>
        <label for="password">密码：</label>
        <input id="password" type="password" name="password">
    </div>
    <div>
        <label><input type="checkbox" name="remember-me"/>自动登录</label>
        <button type="submit">立即登陆</button>
    </div>
</form>
</body>
</html>
```

### 2.2 实现方式

#### 2.2.1 cookie方式存储

这种方式十分简单，只要在 WebSecurityConfig 中的 configure() 方法添加一个 `rememberMe()` 即可,如下所示：

```java
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
                // 自定义登陆用户名和密码参数，默认为username和password
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                // 退出
                .logout().permitAll()
                 // 自动登录
                 .and().rememberMe()
                 ;
        // 关闭CSRF跨域
        http.csrf().disable();
    }
```

当我们登陆时勾选自动登录时，会自动在 Cookie 中保存一个名为 `remember-me` 的cookie，默认有效期为2周，其值是一个加密字符串：

![](https://img-blog.csdn.net/20180509100451811)

#### 2.2.2 数据库方式存储

​		使用 Cookie 存储虽然很方便，但是大家都知道**Cookie 毕竟是保存在客户端的**，而且**Cookie 的值还与用户名、密码这些敏感数据相关**，虽然加密了，但是将敏感信息存在客户端，毕竟不太安全。

Spring security 还提供了另一种相对更安全的实现机制：在客户端的 Cookie 中，仅保存一个无意义的加密串（与用户名、密码等敏感数据无关），然后在数据库中保存该加密串-用户信息的对应关系，自动登录时，用 Cookie 中的加密串，到数据库中验证，如果通过，自动登录才算通过。

##### 2.2.2.1 实现原理

当浏览器发起表单登录请求时，当通过`UsernamePasswordAuthenticationFilter`认证成功后，会经过 `RememberMeService`，在其中有个`TokenRepository`，它会生成一个 token，首先将 token 写入到浏览器的 Cookie 中，然后将 token、认证成功的用户名写入到数据库中。

当浏览器下次请求时，会经过`RememberMeAuthenticationFilter`，它会读取 Cookie 中的 token，交给 `RememberMeService `从数据库中查询记录。如果存在记录，会读取用户名并去调用`UserDetailsService`，获取用户信息，并将用户信息放入Spring Security 中，实现自动登陆。
![](https://img-blog.csdnimg.cn/20181202143630639.png)

`RememberMeAuthenticationFilter`在整个过滤器链中是比较靠后的位置，也就是说在传统登录方式都无法登录的情况下才会使用自动登陆。

![](https://img-blog.csdnimg.cn/20181202144420871.png)

##### 2.2.2.2 代码实现

###### 2.2.2.2.1 Token表

首先需要创建一张表来存储 token 信息：

```sql
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

###### 2.2.2.2.2 WebSecurityConfir配置

在 WebSecurityConfig 中注入 `dataSource` ，创建一个 `PersistentTokenRepository` 的Bean：

```java
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
```

勾选自动登录后，Cookie 和数据库中均存储了 token 信息：

![](https://img-blog.csdn.net/20180509102031410)

## 3、异常处理

知道你有没有注意到，当我们登陆失败时候，Spring security 帮我们跳转到了`/login?error`Url，奇怪的是不管是控制台还是网页上都没有打印错误信息。

![](https://img-blog.csdn.net/20180509103952703)

这是因为首先`/login?error`是 Spring security 默认的失败 Url，其次如果你不手动处理这个异常，这个异常是不会被处理的。

### 3.1 常见异常

我们先来列举下一些 Spring Security 中常见的异常：

* UsernameNotFoundException（用户不存在）

* DisabledException（用户已被禁用）

* BadCredentialsException（坏的凭据）

* LockedException（账户锁定）

* AccountExpiredException （账户过期）

* CredentialsExpiredException（证书过期）

* …

  以上列出的这些异常都是 AuthenticationException 的子类，然后我们来看看 Spring security 如何处理 AuthenticationException 异常的。

### 3.2 源码分析

我们知道异常处理一般在过滤器中处理，我们在`AbstractAuthenticationProcessingFilter`中找到了对 `AuthenticationException`的处理：

1. 在`doFilter()`中，捕捉了`AuthenticationException`异常，并交给了`unsuccessfulAuthentication() `处理

```java
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult;

		try {
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				return;
			}
			sessionStrategy.onAuthentication(authResult, request, response);
		}
		catch (InternalAuthenticationServiceException failed) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
             // 异常处理
			unsuccessfulAuthentication(request, response, failed);
			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed 
            // 异常处理
			unsuccessfulAuthentication(request, response, failed);
			return;
		}

		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}

		successfulAuthentication(request, response, chain, authResult);
	}
```

2. 在 `unsuccessfulAuthentication()` 中，转交给了 `SimpleUrlAuthenticationFailureHandler` 类的 `onAuthenticationFailure()` 处理。

```java
protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication request failed: " + failed.toString(), failed);
			logger.debug("Updated SecurityContextHolder to contain null Authentication");
			logger.debug("Delegating to authentication failure handler " + failureHandler);
		}

		rememberMeServices.loginFail(request, response);

		failureHandler.onAuthenticationFailure(request, response, failed);
	}
```

3. 在onAuthenticationFailure()中，首先判断有没有设置defaultFailureUrl。
   * 如果没有设置，直接返回 401 错误，即 HttpStatus.UNAUTHORIZED 的值。
   * 如果设置了，首先执行 saveException() 方法。然后判断 forwardToDestination ，即是否是服务器跳转，默认使用重定向即客户端跳转。

```java
public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		// 判断defaultFailureUrl是否设置
        // 没有设置则返回HttpStatus.UNAUTHORIZED的值 401
		if (defaultFailureUrl == null) {
			logger.debug("No failure URL set, sending 401 Unauthorized error");

			response.sendError(HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}
		else {
            // 保存异常
			saveException(request, exception);
			// 判断是否服务器跳转
			if (forwardToDestination) {
				logger.debug("Forwarding to " + defaultFailureUrl);

				request.getRequestDispatcher(defaultFailureUrl)
						.forward(request, response);
			}
			else {
                // 默认情况下是重定向，即客户端跳转
				logger.debug("Redirecting to " + defaultFailureUrl);
				redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
			}
		}
	}
```

4. 在`saveException()`方法中，首先判断`forwardToDestination`，

   * 使用服务器跳转则写入`Request`
   * 使用客户端跳转则写入`Session`
   * 写入名为`SPRING_SECURITY_LAST_EXCEPTION` ，值为`AuthenticationException`

   ```java
   protected final void saveException(HttpServletRequest request,
   			AuthenticationException exception) {
           // 判断服务端跳转
   		if (forwardToDestination) {
   			request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
   		}
   		else {
   			HttpSession session = request.getSession(false);
   
   			if (session != null || allowSessionCreation) {
   				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
   						exception);
   			}
   		}
   }
   ```


### 3.3 异常处理

   上面源码说了那么多，真正处理起来很简单，我们只需要指定错误的url，然后再该方法中对异常进行处理即可。

（1）指定错误Url，`WebSecurityConfig`中添加`.failureUrl("/login/error")`

```java
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
```

(2)在Controller中处理异常

```java
   /**
     * 登录异常
     * 从session获取异常信息，直接输出到页面上面
     *
     * @param request  请求
     * @param response 响应
     */
    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        //  session 中的 SPRING_SECURITY_LAST_EXCEPTION
        AuthenticationException exception =
                (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        try {
            response.getWriter().write(exception.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

运行程序，当我们输入错误密码时：

![](https://img-blog.csdn.net/20180403145530517)