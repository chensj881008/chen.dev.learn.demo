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

