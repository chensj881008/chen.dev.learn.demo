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

## 4、自定义表单登录

通过上面的项目，可以看出登录(login请求)的操作全部都是有Spring Security来处理的，如何实现自定义表单登录？比如说添加个验证码

### 4.1 添加验证码

验证码的 Servlet 代码，大家无需关心其内部实现，我也是百度直接捞了一个，直接复制即可。

#### 4.1.1 验证码Servlet

```java
package org.chen.spring.security5.boot.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码 Servlet
 *
 * @author chensj
 * @date 2019-09-12 10:03
 */
public class VerificationCodeServlet extends HttpServlet {
    private static final long serialVersionUID = -5051097528828603895L;

    /**
     * 验证码图片的宽度。
     */
    private int width = 100;

    /**
     *  验证码图片的高度。
     */
    private int height = 30;

    /**
     * 验证码字符个数
     */
    private int codeCount = 4;

    /**
     * 字体高度
     */
    private int fontHeight;

    /**
     * 干扰线数量
     */
    private int interLine = 16;

    /**
     * 第一个字符的x轴值，因为后面的字符坐标依次递增，所以它们的x轴值是codeX的倍数
     */
    private int codeX;

    /**
     * codeY ,验证字符的y轴值，因为并行所以值一样
     */
    private int codeY;

    /**
     * codeSequence 表示字符允许出现的序列值
     */
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * 初始化验证图片属性
     */
    @Override
    public void init() throws ServletException {
        // 从web.xml中获取初始信息
        // 宽度
        String strWidth = this.getInitParameter("width");
        // 高度
        String strHeight = this.getInitParameter("height");
        // 字符个数
        String strCodeCount = this.getInitParameter("codeCount");
        // 将配置的信息转换成数值
        try {
            if (strWidth != null && strWidth.length() != 0) {
                width = Integer.parseInt(strWidth);
            }
            if (strHeight != null && strHeight.length() != 0) {
                height = Integer.parseInt(strHeight);
            }
            if (strCodeCount != null && strCodeCount.length() != 0) {
                codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //width-4 除去左右多余的位置，使验证码更加集中显示，减得越多越集中。
        //codeCount+1     //等比分配显示的宽度，包括左右两边的空格
        codeX = (width-4) / (codeCount+1);
        //height - 10 集中显示验证码
        fontHeight = height - 10;
        codeY = height - 7;
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.PLAIN, fontHeight);
        // 设置字体。
        gd.setFont(font);
        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);
        // 随机产生16条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.gray);
        for (int i = 0; i < interLine; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red,green,blue));
            gd.drawString(strRand, (i + 1) * codeX, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute("validateCode", randomCode.toString());
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
}
```

然后在 Application 中注入该 Servlet：

```java
   /**
    * 注入验证码servlet
    *
    * @return servlet
    */
    @Bean
    public ServletRegistrationBean<VerificationCodeServlet> verificationCodeServlet() {
        ServletRegistrationBean<VerificationCodeServlet> registrationBean =
        new ServletRegistrationBean<>(new VerificationCodeServlet());
        registrationBean.addUrlMappings("/getVerificationCode");
        return registrationBean;
    }
```

#### 4.1.2 修改 login.html

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
        <input type="text" class="form-control" name="verifyCode" required="required" placeholder="验证码">
        <img src="getVerificationCode" title="看不清，请点我" onclick="refresh(this)" onmouseover="mouseover(this)" />
    </div>
    <div>
        <label><input type="checkbox" name="remember-me"/>自动登录</label>
        <button type="submit">立即登陆</button>
    </div>
</form>
<script>
    function refresh(obj) { obj.src = "getVerificationCode?" + Math.random(); }

    function mouseover(obj) { obj.style.cursor = "pointer"; }
</script>
</body>
</html>
```

#### 4.1.3  添加匿名访问 Url

不要忘记在 WebSecurityConfig 中允许该 Url 的匿名访问，不然没有登录是没有办法访问的：

```java
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 任何请求 都需要认证
                // 如果存在不需要认证的url,可以使用 .antMatchers().permitAll()来设置
                // 如果有允许匿名的url，填在下面
                .antMatchers("/getVerificationCode").permitAll()
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

完成上面的操作，验证码添加好了

![](https://img-blog.csdn.net/20180509110841240)

### 4.2 验证码验证分析

上面的验证码是添加完成了，但是如何验证这个验证码的正确性，通常有以下几种方式

* 登录表单提交前发送 AJAX 验证验证码
* 使用自定义过滤器(Filter)，在 Spring security 校验前验证验证码合法性
* 和用户名、密码一起发送到后台，在 Spring security 中进行验证

### 4.2.1 AJAX验证

使用 AJAX 方式验证和我们 Spring Security 框架就没有任何关系了，其实就是表单提交前先发个 HTTP 请求验证验证码，本篇不再赘述。

### 4.2.2 过滤器验证

使用过滤器的思路是：**在 Spring Security 处理登录验证请求前，验证验证码，如果正确，放行；如果不正确，调到异常**。

#### 4.2.2.1 编写过滤器

自定义一个过滤器，实现实现`OncePerRequestFilter`（该 Filter 保证每次请求一定会过滤），在 `isProtectedUrl() `方法中拦截了 POST 方式的 `/login`请求。

在逻辑处理中从`request`中取出验证码，并进行验证，如果验证成功，放行；验证失败，手动生成异常。

```java

/**
 * 验证码验证过滤器
 *
 * @author chensj
 * @date 2019-09-12 10:24
 */
public class VerificationCodeFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isProtectedUrl(request)) {
            String verifyCode = request.getParameter("verifyCode");
            if (!validateVerify(verifyCode)) {
                //手动设置异常
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new DisabledException(
                        "验证码输入错误"));
                // 转发到错误Url
                request.getRequestDispatcher("/login/error").forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean validateVerify(String inputVerify) {
        //获取当前线程绑定的request对象
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个validateCode是在servlet中存入session的名字
        String validateCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
        inputVerify = inputVerify.toLowerCase();

        System.out.println("验证码：" + validateCode + "用户输入：" + inputVerify);
        return validateCode.equals(inputVerify);
    }

    /**
     * 拦截 /login的POST请求
     *
     * @param request 请求
     * @return boolean
     */
    private boolean isProtectedUrl(HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && pathMatcher.match("/login", request.getServletPath());
    }
}
```

#### 4.2.2.2 注入过滤器

修改 WebSecurityConfig 的 configure 方法，添加一个 addFilterBefore() ，具有两个参数，作用是在参数二之前执行参数一设置的过滤器。

Spring Security 对于用户名/密码登录方式是通过 UsernamePasswordAuthenticationFilter 处理的，我们在它之前执行验证码过滤器即可。

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 任何请求 都需要认证
                // 如果存在不需要认证的url,可以使用 .antMatchers().permitAll()来设置
                // 如果有允许匿名的url，填在下面
                .antMatchers("/getVerificationCode").permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登录页
                .formLogin().loginPage("/login")
                // 设置登录成功也
                .defaultSuccessUrl("/").permitAll()
                // 登录失败Url
                .failureUrl("/login/error")
                // 自定义登陆用户名和密码参数，默认为username和password
                //.usernameParameter("username")
                //.passwordParameter("password")
                .and()
                // 在账户密码验证之前验证
                // 第二个参数就是在该filter之前验证
                .addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class)
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

现在来测试下，当验证码错误后：

![验证码错误](https://img-blog.csdn.net/20180509145853658)

### 4.2.3 Spring Security验证

使用过滤器就已经实现了验证码功能，但其实它和`AJAX`验证差别不大。

* `AJAX`是在提交前发一个请求，请求返回成功就提交，否则不提交；
* 过滤器是先验证验证码，验证成功就让`Spring Security`验证用户名和密码；验证失败，则产生异常·。

如果我们要做的需求是用户登录是需要多个验证字段，不单单是用户名和密码，那么使用过滤器会让逻辑变得复杂，这时候可以考虑自定义`Spring Security`的验证逻辑了…

#### 4.2.3.1 WebAuthenticationDetails

我们知道`Spring security`默认只会处理用户名和密码信息。这时候就要请出我们的主角——**`WebAuthenticationDetails`**。

> WebAuthenticationDetails: 该类提供了获取用户登录时携带的额外信息的功能，默认提供了 remoteAddress 与 sessionId 信息。

我们需要实现自定义的 WebAuthenticationDetails，并在其中加入我们的验证码：

```java
/**
 * 获取用户登录时携带的额外信息
 *
 * @author chensj
 * @date 2019-09-12 10:43
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {


    private static final long serialVersionUID = 8451867313411066177L;
    /**
     * 前台传入验证码
     */
    private final String verifyCode;

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // verifyCode为页面中验证码的name
        verifyCode = request.getParameter("verifyCode");
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 将从表单中获取的验证码信息添加到参数中
        sb.append(super.toString()).append("; VerifyCode: ").append(this.getVerifyCode());
        return sb.toString();
    }
}
```

#### 4.2.3.2 AuthenticationDetailsSource

自定义了`WebAuthenticationDetails`后，我们还需要将其放入`AuthenticationDetailsSource`中来替换原本的`WebAuthenticationDetails` ，因此还得实现自定义`AuthenticationDetailsSource`：

```java
/**
 * 该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充
 *
 * @author chensj
 * @date 2019-09-12 10:54
 */
@Component("authenticationDetailsSource")
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest,
        WebAuthenticationDetails> {
    /**
     * 该类内容将原本的 WebAuthenticationDetails 替换为了我们的 CustomWebAuthenticationDetails
     *
     * @param request 请求
     * @return d
     */
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new CustomWebAuthenticationDetails(request);
    }
}
```

该类内容将原本的`WebAuthenticationDetails`替换为了我们的 `CustomWebAuthenticationDetails`。

然后我们将`CustomAuthenticationDetailsSource`注入`Spring Security`中，替换掉默认的 `AuthenticationDetailsSource`。

修改`WebSecurityConfig`，将其注入，然后在`config()`中使用 `authenticationDetailsSource(authenticationDetailsSource)`方法来指定它。

```java
@Autowired
private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 任何请求 都需要认证
                // 如果存在不需要认证的url,可以使用 .antMatchers().permitAll()来设置
                // 如果有允许匿名的url，填在下面
                .antMatchers("/getVerificationCode").permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登录页
                .formLogin().loginPage("/login")
                // 设置登录成功也
                .defaultSuccessUrl("/").permitAll()
                // 登录失败Url
                .failureUrl("/login/error")
                // 指定authenticationDetailsSource
                .authenticationDetailsSource(authenticationDetailsSource)
                // 自定义登陆用户名和密码参数，默认为username和password
                //.usernameParameter("username")
                //.passwordParameter("password")
                .and()
                // 自定义过滤器验证验证码
                // 在账户密码验证之前验证
                // 第二个参数就是在该filter之前验证
                //.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class)
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

#### 4.2.3.3 AuthenticationProvider

至此我们通过自定义`WebAuthenticationDetails`和`AuthenticationDetailsSource`将验证码和用户名、密码一起带入了`Spring Security`中，下面我们需要将它取出来。

这里需要我们自定义`AuthenticationProvider`，需要注意的是，如果是我们自己实现`AuthenticationProvider`，那么我们就需要自己做密码校验了。

```java
/**
 * 自定义认证器
 *
 * @author chensj
 * @date 2019-09-12 11:05
 */
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();

        String verifyCode = details.getVerifyCode();
        if (!validateVerify(verifyCode)) {
            throw new DisabledException("验证码输入错误");
        }

        // userDetails为数据库中查询到的用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(inputName);

        // 如果是自定义AuthenticationProvider，需要手动密码校验
        if (!userDetails.getPassword().equals(inputPassword)) {
            throw new BadCredentialsException("密码错误");
        }

        return new UsernamePasswordAuthenticationToken(inputName, inputPassword, userDetails.getAuthorities());
    }

    private boolean validateVerify(String inputVerify) {
        //获取当前线程绑定的request对象
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个validateCode是在servlet中存入session的名字
        String validateCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
        inputVerify = inputVerify.toLowerCase();

        log.info("验证码:[{}] 用户输入:[{}]", validateCode, inputVerify);

        return validateCode.equals(inputVerify);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 这里不要忘记，和UsernamePasswordAuthenticationToken比较
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
```

最后在 `WebSecurityConfig` 中将其注入，并在 config 方法中通过 `auth.authenticationProvider()` 指定使用。

```java
@Autowired
private CustomAuthenticationProvider customAuthenticationProvider;
 @Override
 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     // 用户只需要认证账号与密码的时候使用
     //auth.userDetailsService(userDetailService)
     //        .passwordEncoder(new PasswordEncoder() {
     //            @Override
     //            public String encode(CharSequence charSequence) {
     //                // 直接返回明文密码
     //                return charSequence.toString();
     //            }
     //
     //            @Override
     //            public boolean matches(CharSequence charSequence, String s) {
     //                return s.equals(charSequence.toString());
     //            }
     //        });
     // 使用自定义认证器
     auth.authenticationProvider(customAuthenticationProvider);
 }
```

是不是比较复杂，为了实现该需求自定义了 `WebAuthenticationDetails`、`AuthenticationDetailsSource`、`AuthenticationProvider`，让我们运行一下程序，当输入错误验证码时：

![验证码错误](https://img-blog.csdn.net/20180509145853658)

## 5.权限控制

### 5.1 数据准备

#### 5.1.1 建表和初始化数据

* 创建`sys_permission`权限表

  ```sql
  CREATE TABLE `sys_permission` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `url` varchar(255) DEFAULT NULL,
    `role_id` int(11) DEFAULT NULL,
    `permission` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_roleId` (`role_id`),
    CONSTRAINT `fk_roleId` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
  ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
  ```

* 初始化数据

  内容就是两条数据，`url`+`role_id`+`permission` 唯一标识了一个角色访问某一 url 时的权限，其中权限暂定为 c、r、u、d，即增删改查。

  ```sql
  INSERT INTO `security5`.`sys_permission` (`url`, `role_id`, `permission`) VALUES ( '/admin', '1', 'c,r,u,d');
  INSERT INTO `security5`.`sys_permission` (`url`, `role_id`, `permission`) VALUES ( '/admin', '2', 'c');
  
  ```

#### 5.2 创建POJO、Mapper、Service

1. pojo

```java 
/**
 * @author chensj
 * @date 2019-09-12 11:25
 */
@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = -7103488938939776567L;

    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;

    private List permissions;

    // 省略除permissions外的getter/setter

    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }

}
```

这里需要注意的时相比于数据库，多了一个 `permissions` 属性，该字段将 `permission` **按逗号分割**为了 list

2. mapper

```java
/**
 * @author chensj
 * @date 2019-09-12 11:28
 */
@Mapper
public interface SysPermissionMapper {
    /**
     * 根据角色ID获取全部权限
     *
     * @param roleId 角色ID
     * @return list
     */
    @Select("SELECT * FROM sys_permission WHERE role_id=#{roleId}")
    List<SysPermission> listByRoleId(Integer roleId);
}

```

3. service

```java
@Service
public class SysPermissionService {
    @Autowired
    private SysPermissionMapper permissionMapper;

    /**
     * 获取指定角色所有权限
     */
    public List<SysPermission> listByRoleId(Integer roleId) {
        return permissionMapper.listByRoleId(roleId);
    }
}
```
Service中有一个方法，根据`roleId`获取所有的`SysPermission`

4. controller

```java
 @RequestMapping("/admin")
@ResponseBody
@PreAuthorize("hasPermission('/admin','r')")
public String printAdminRead() {
    return "如果你看见这句话，说明你访问/admin路径具有r权限";
}

@RequestMapping("/admin/c")
@ResponseBody
@PreAuthorize("hasPermission('/admin','c')")
public String printAdminCreate() {
    return "如果你看见这句话，说明你访问/admin路径具有c权限";
}
```
让我们修改下我们要访问的接口，`@PreAuthorize("hasPermission('/admin','r')")`是关键，参数1指明了访问该接口需要的url，参数2指明了访问该接口需要的权限。
### 5.2  PermissionEvaluator

我们需要自定义对`hasPermission()`方法的处理，就需要自定义`PermissionEvaluator`，创建类`CustomPermissionEvaluator`，实现 `PermissionEvaluator`接口。

```java
@Slf4j
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private CustomUserDetailServiceImpl userDetailService;

    /**
     * 通过 Authentication 取出登录用户的所有 Role
     * 遍历每一个 Role，获取到每个Role的所有 Permission
     * 遍历每一个 Permission，只要有一个 Permission 的 url 和传入的url相同，且该 Permission 中包含传入的权限，返回 true
     * 如果遍历都结束，还没有找到，返回false
     *
     * @param authentication   认证信息
     * @param targetUrl        访问 url
     * @param targetPermission 访问 url 权限
     * @return boolean
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        String username =  authentication.getPrincipal().toString();
        // 获得loadUserByUsername()方法的结果
        User user = (User) userDetailService.loadUserByUsername(username);
        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        log.info("targetUrl:[{}] , permission: [{}]", targetUrl, targetPermission);
        // 遍历用户所有角色
        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Integer roleId = roleService.selectByName(roleName).getId();
            // 得到角色所有的权限
            List<SysPermission> permissionList = permissionService.listByRoleId(roleId);

            // 遍历permissionList
            for (SysPermission sysPermission : permissionList) {
                // 获取权限集
                List permissions = sysPermission.getPermissions();
                // 如果访问的Url和权限用户符合的话，返回true
                if (targetUrl.equals(sysPermission.getUrl())
                        && permissions.contains(targetPermission)) {
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
```

在 hasPermission() 方法中，参数 1 代表用户的权限身份，参数 2 参数 3 分别和 @PreAuthorize("hasPermission('/admin','r')") 中的参数对应，即访问 url 和权限。

思路如下：

* 通过 Authentication 取出登录用户的所有 Role
* 遍历每一个 Role，获取到每个Role的所有 Permission
* 遍历每一个 Permission，只要有一个 Permission 的 url 和传入的url相同，且该 Permission 中包含传入的权限，返回 true
* 如果遍历都结束，还没有找到，返回false

下面就是在 `WebSecurityConfig` 中注册 `CustomPermissionEvaluator`：

```java
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
```

当我使用角色为 `ROLE_USER` 的用户仍然能访问，因为该用户访问 `/admin` 路径具有 `r` 权限：

![运行结果](https://img-blog.csdn.net/2018051519070954)

## 6、登录管理

### 6.1 自定义认证成功与失败处理

项目无需增加配置，只需要采用入门的那个项目即可

在上面的项目中，我们关于认证成功和失败后的处理是如下配置的

```java
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 指定所有访问都是需要登录才尅操作
                .anyRequest().authenticated()
                // 指定采用formLogin方式认证，，登录成功页面 / 并且/login页面不需要验证登录
                .and().formLogin()
                // 设置登录页面为/login
                .loginPage("/login")
                // 设置认证成功后Url
                .defaultSuccessUrl("/")
                // 认证失败后的页面
                .failureForwardUrl("/login/error")
                .permitAll()
                // 指定退出也不需要验证登录
                .and().logout().permitAll();
        // 关闭csrf 跨域
        http.csrf().disable();
    }
```

>  `failureUrl()` 指定认证失败后Url，
>
> `defaultSuccessUrl()` 指定认证成功后Url。
>
> 我们可以通过设置 `successHandler()` 和 `failureHandler()` 来实现自定义认证成功、失败处理。
>
> 同样，当我们指定上面的两个handler的时候，之前设置的 `failureUrl()`和`defaultSuccessUrl()`需要去除，否则将无法使用我们自定义的handler。**即上述两种配置只能存在一个，不能同时存在**

#### 6.1.1 CustomAuthenticationSuccessHandler

```java
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功处理
     *
     * @param authentication 认证后该用户的认证信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功{}", authentication);
        // 重定向到了首页
        response.sendRedirect("/");
    }
}
```

#### 6.1.2 CustomAuthenticationFailureHandler

```java
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 登录失败后的处理
     *
     * @param exception 认证失败所产生的异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("登录失败,失败原因{}", exception.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
    }
}
```

#### 6.1.3 WebSecurityConfig

```java
@Autowired
private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
@Autowired
private CustomAuthenticationFailureHandler authenticationFailureHandler;
 @Override
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // 指定所有访问都是需要登录才尅操作
        .anyRequest().authenticated()
        // 指定采用formLogin方式认证，，登录成功页面 / 并且/login页面不需要验证登录
        .and().formLogin()
        // 设置登录页面为/login
        .loginPage("/login")
        //// 设置认证成功后Url
        //.defaultSuccessUrl("/")
        //// 认证失败后的页面
        //.failureForwardUrl("/login/error")
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .permitAll()
        // 指定退出也不需要验证登录
        .and().logout().permitAll();
    // 关闭csrf 跨域
    http.csrf().disable();
}
```

1. 首先将 customAuthenticationSuccessHandler 和 customAuthenticationFailureHandler注入进来

2. 配置 successHandler() 和 failureHandler()

3. 注释 failureUrl() 和 defaultSuccessUrl()1.4 运行程序

   运行程序，当我们成功登陆后，发现日志信息被打印出来，页面被重定向到了首页：

   ![img](https://img-blog.csdnimg.cn/20190110174809434.png)

   当我们认证失败后，发现日志中“登陆失败”被打印出来，页面展示了认证失败的异常消息：

   ![img](https://img-blog.csdnimg.cn/20190110174827988.png)

### 6.2 Session超时

当用户登录后，我们可以设置 session 的超时时间，当达到超时时间后，自动将用户退出登录。

Session 超时的配置是 SpringBoot 原生支持的，我们只需要在 application.properties 配置文件中配置：

```properties
# session 过期时间，单位：秒
server.servlet.session.timeout=60
```

> Tip：
> 从用户最后一次操作开始计算过期时间。
> 过期时间最小值为 60 秒，如果你设置的值小于 60 秒，也会被更改为 60 秒。

我们可以在 Spring Security 中配置处理逻辑，在 session 过期退出时调用。修改 WebSecurityConfig 的 `configure()` 方法，添加：

```java
.sessionManagement()
    // 以下二选一
    //.invalidSessionStrategy()
    //.invalidSessionUrl();
```

Spring Security 提供了两种处理配置，一个是 invalidSessionStrategy()，另外一个是 invalidSessionUrl()。

这两个的区别就是一个是前者是在一个类中进行处理，后者是直接跳转到一个 Url。简单起见，我就直接用 invalidSessionUrl()了，跳转到 /login/invalid，我们需要把该 Url 设置为免授权访问， 配置如下：

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
   http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/login/invalid").permitAll()
                // 指定所有访问都是需要登录才尅操作
                .anyRequest().authenticated()
                // 指定采用formLogin方式认证，，登录成功页面 / 并且/login页面不需要验证登录
                .and().formLogin()
                // 设置登录页面为/login
                .loginPage("/login")
                // 设置认证成功后Url， 认证失败后的页面
                //.defaultSuccessUrl("/").failureForwardUrl("/login/error")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                // 指定退出也不需要验证登录
                .and().logout().permitAll()
                .and()
                // 使用session管理 并指定session过期后跳转页面
                .sessionManagement().invalidSessionUrl("/login/invalid");
        // 关闭csrf 跨域
        http.csrf().disable();
}
```

> 如果使用session失效时间的话，那么就不能使用自定义的登录成功与失败的handler，即6.1中的代码，需要注释上述代码才能够实现

在 controller 中写一个接口进行处理：

```java
@RequestMapping("/login/invalid")
@ResponseStatus(HttpStatus.UNAUTHORIZED)
@ResponseBody
public String invalid() {
    return "Session 已过期，请重新登录";
}
```


运行程序，登陆成功后等待一分钟（或者重启服务器），刷新页面：

![](https://img-blog.csdnimg.cn/20190110171026663.png)

### 6.3 限制最大登录数

限制最大登陆数，原理就是限制单个用户能够存在的最大 session 数。

实现方式代码如下：

```java
  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/login/invalid").permitAll()
                // 指定所有访问都是需要登录才尅操作
                .anyRequest().authenticated()
                // 指定采用formLogin方式认证，，登录成功页面 / 并且/login页面不需要验证登录
                .and().formLogin()
                // 设置登录页面为/login
                .loginPage("/login")
                // 设置认证成功后Url， 认证失败后的页面
                //.defaultSuccessUrl("/").failureForwardUrl("/login/error")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                // 指定退出也不需要验证登录
                .and().logout().permitAll()
                .and()
                // 使用session管理 并指定session过期后跳转页面
                .sessionManagement().invalidSessionUrl("/login/invalid")
                // 限制同时登录的人数，指定最大登录人数
                .maximumSessions(1)
                // 当达到最大值时，是否保留已经登录的用户
                .maxSessionsPreventsLogin(false)
                // 当达到最大值时，旧用户被踢出后的操作
                .expiredSessionStrategy(new CustomExpiredSessionStrategy());
        // 关闭csrf 跨域
        http.csrf().disable();
    }
```

增加了下面三行代码，其中：

* `maximumSessions(int)`：指定最大登录数
* `maxSessionsPreventsLogin(boolean)`：是否保留已经登录的用户；为true，新用户无法登录；为 false，旧用户被踢出
* `expiredSessionStrategy(SessionInformationExpiredStrategy)`：旧用户被踢出后处理方法
  maxSessionsPreventsLogin()可能不太好理解，这里我们先设为 false，效果和 QQ 登录是一样的，登陆后之前登录的账户被踢出。

`CustomExpiredSessionStrategy` 处理逻辑

```java
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    /**
     * Jackson处理bean的utils类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 0);
        map.put("msg", "已经另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest());
        // Map -> Json
        String json = objectMapper.writeValueAsString(map);

        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(json);

        // 如果是跳转html页面，url代表跳转的地址
        // redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "url");
    }
}
```

在`onExpiredSessionDetected() `方法中，处理相关逻辑，我这里只是简单的返回一句话。

执行程序，打开两个浏览器，登录同一个账户。因为我设置了 maximumSessions(1)，也就是单个用户只能存在一个 session，因此当你刷新先登录的那个浏览器时，被提示踢出了。

测试1：`maxSessionsPreventsLogin` 参数为false

执行程序，打开两个浏览器，登录同一个账户。因为我设置了 maximumSessions(1)，也就是单个用户只能存在一个 session，因此当你刷新先登录的那个浏览器时，被提示踢出了。

![](https://img-blog.csdnimg.cn/2019011017515758.png)

测试2：`maxSessionsPreventsLogin` 参数为true

![](https://img-blog.csdnimg.cn/20190110175325653.png)

### 6.4 踢出用户

首先在容器中注入一个名为 `SessionRegistry` 的 Bean，这里我就简单的写在 WebSecurityConfig 中：

```java
@Bean
public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
}
```

修改WebSecurityConfig配置

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/login/invalid").permitAll()
                // 指定所有访问都是需要登录才尅操作
                .anyRequest().authenticated()
                // 指定采用formLogin方式认证，，登录成功页面 / 并且/login页面不需要验证登录
                .and().formLogin()
                // 设置登录页面为/login
                .loginPage("/login")
                // 设置认证成功后Url， 认证失败后的页面
                //.defaultSuccessUrl("/").failureForwardUrl("/login/error")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                // 指定退出也不需要验证登录
                .and().logout().permitAll()
                .and()
                // 使用session管理 并指定session过期后跳转页面
                .sessionManagement().invalidSessionUrl("/login/invalid")
                // 限制同时登录的人数，指定最大登录人数
                .maximumSessions(1)
                // 当达到最大值时，是否保留已经登录的用户
                .maxSessionsPreventsLogin(true)
                // 当达到最大值时，旧用户被踢出后的操作
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
                //
                .sessionRegistry(sessionRegistry());
        // 关闭csrf 跨域
        http.csrf().disable();
    }
```

编写一个接口用于测试踢出用户：

```java
@Autowired
private SessionRegistry sessionRegistry;
@GetMapping("/kick")
    @ResponseBody
    public String removeUserSessionByUsername(@RequestParam String username) {
        int count = 0;

        // 获取session中所有的用户信息
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object principal : users) {
            if (principal instanceof User) {
                String principalName = ((User)principal).getUsername();
                if (principalName.equals(username)) {
                    // 参数二：是否包含过期的Session
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                    if (null != sessionsInfo && sessionsInfo.size() > 0) {
                        for (SessionInformation sessionInformation : sessionsInfo) {
                            sessionInformation.expireNow();
                            count++;
                        }
                    }
                }
            }
        }
        return "操作成功，清理session共" + count + "个";
    }
}
```

1. `sessionRegistry.getAllPrincipals()`; 获取所有 principal 信息
2. 通过 `principal.getUsername` 是否等于输入值，获取到指定用户的 principal
3. `sessionRegistry.getAllSessions(principal, false)`获取该 principal 上的所有 session
4. 通过` sessionInformation.expireNow() `使得 session 过期

运行程序，分别使用 admin 和 chensj账户登录，admin 访问 `/kick?username=chensj`来踢出用户 chensj，chensj刷新页面，发现被踢出。

### 6.5 用户退出

退出登录的内容，在之前，我们直接在 WebSecurityConfig 的 configure() 方法中，配置了：

```java
http.logout();
```


这就是 Spring Security 的默认退出配置，Spring Security 在退出时候做了这样几件事：

1. 使当前的 session 失效

2. 清除与当前用户有关的 remember-me 记录

3. 清空当前的 SecurityContext

4. 重定向到登录页

Spring Security 默认的退出Url是/logout，我们可以修改默认的退出 Url，例如修改为 /signout：

```java
http.logout()
	.logoutUrl("/signout");
```

我们也可以配置当退出时清除浏览器的 Cookie，例如清除 名为 JSESSIONID 的 cookie：
```java
http.logout()
	.logoutUrl("/signout")
	.deleteCookies("JSESSIONID");
```

我们也可以配置退出后处理的逻辑，方便做一些别的操作：
```java
http.logout()
	.logoutUrl("/signout")
	.deleteCookies("JSESSIONID")
	.logoutSuccessHandler(logoutSuccessHandler);
```

创建类 DefaultLogoutSuccessHandler：
```java
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = ((User) authentication.getPrincipal()).getUsername();
        log.info("退出成功，用户名：{}", username);
    	
    	// 重定向到登录页
        response.sendRedirect("/login");
    }
}
```

最后把它注入到 WebSecurityConfig 即可：
```java
@Autowired
private CustomLogoutSuccessHandler logoutSuccessHandler;
```

### 6.6 Session 共享

关于 Session 共享的知识点，一般情况下，一个程序为了保证稳定至少要部署两个，构成集群。那么就牵扯到了 Session 共享的问题，不然用户在 8080 登录成功后，后续访问了 8060 服务器，结果又提示没有登录。

这里就简单实现下 Session 共享，采用 Redis 来存储。

#### 6.6.1 配置Redis

为了方便起见，我直接使用 Docker 快速部署，如果你需要传统方式安装，可以参考文章[《Redis初探（1）——Redis的安装》](https://blog.csdn.net/yuanlaijike/article/details/79383242)。

```bash
docker pull redis
docker run --name myredis -p 6379:6379 -d redis
docker exec -it myredis redis-cli
```

这样就启动了 redis，并且进入到 redis 命令行中。

#### 6.6.2 配置Session

首先需要导入依赖，因为我们采用 Redis 方式实现，因此导入：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

在application.yml中配置redis和session的信息

```yaml
spring:
  redis:
    host: 172.17.1.242
    port: 6000
  session:
    store-type: redis
```

在主类上添加注解开启配置

```java
@EnableRedisHttpSession
@SpringBootApplication
public class LoginShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginShareApplication.class, args);
    }
}
```

先访问 `localhost:8080`，登录成功后，再访问 `localhost:8060`，发现无需登录。

![Session 共享运行结果](https://img-blog.csdnimg.cn/20190118110952599.png)

然后我们进入 Redis 查看下 key：

![img](https://img-blog.csdnimg.cn/20190118111044647.png)

最后再测试下之前配置的 session 设置是否还有效，使用其他浏览器登陆，登陆成功后发现原浏览器用户的确被踢出。

![img](https://img-blog.csdnimg.cn/20190118111234720.png)

