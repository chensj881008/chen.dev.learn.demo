# Spring Security 

## 一、Spring Security 入门开发

### Spring Security 执行原理

* 底层：核心springSecurityFilterChain
  由若干个过滤器组成的
  

比如

* UsernamePasswordAuthenticationFilter 用户密码校验

* BasicAuthenticationFilter 基于HttpBasic方式校验

  等等

在处理的最后会通过如下的Filter

* ExceptionTranslationFilter 异常拦截
*  FilterSecurityInterceptor 总拦截器 判断结果  决定后面逻辑处理

在FilterSecurityInterceptor 中通过invoke方法中的

```java
InterceptorStatusToken token = super.beforeInvocation(fi);
```

来获取之前Filter处理的结果，从而判断是否可以登录、是否具有权限等

### Spring Security 学习

#### Spring Security配置文件

```xml
<security:http>
        <security:intercept-url pattern="/" access="permitAll()"/>
        <security:intercept-url pattern="/index" access="permitAll()"/>
        <security:intercept-url pattern="/index.html" access="permitAll()"/>
        <security:intercept-url pattern="/**" access="isFullyAuthenticated()"/>
        <!--指定认证方式 当前使用http-basic-->
       <security:http-basic/>
    </security:http>
 <security:authentication-manager>
        <!--配置用户权限提供信息-->
        <security:authentication-provider>
            <!--用户信息-->
            <security:user-service>
                <!--用户信息-->
                <security:user name="admin" password="1" authorities="ROLE_ADMIN"/>
                <security:user name="chensj" password="1" authorities="ROLE_USER"/>
            </security:user-service>
        </security:authentication-provider>
    </security:authentication-manager>
```

> 配置文件包含两个方面，一个是过滤器链配置，一个是认证管理器
>
> 前者主要负责定义认证授权的方式，后者则是定义用户认证、权限等的信息

#### 自定义登录页面

* 通过在spring-security.xml文件中设置，将过滤器链设置修改为form-login的方式

```xml
<security:form-login />
```

* 设置配置信息

```xml
<security:form-login  
                     <!--指定登录页面-->
                     login-page="/userLogin" 
                     <!--指定密码使用的字段-->
                     password-parameter="pass"
 					 <!--指定用户名使用的字段-->
                     username-parameter="user"
                     <!--指定登录请求处理的url-->
                     login-processing-url="/user/login"
                     <!--登录成功后访问页面-->
                     default-target-url="/"
                     />
<!--过滤url配置-->
<!--登录页面不拦截-->
<security:intercept-url pattern="/userLogin" access="permitAll()"/>
<!--登录请求URL不拦截-->
<security:intercept-url pattern="/user/login" access="permitAll()"/>
<!--首页不拦截-->
<security:intercept-url pattern="/" access="permitAll()"/>
<!--首页不拦截-->
<security:intercept-url pattern="/index" access="permitAll()"/>
<!--首页不拦截-->
<security:intercept-url pattern="/index.html" access="permitAll()"/>
<!--其他全部拦截-->
<security:intercept-url pattern="/**" access="isFullyAuthenticated()"/>
```

* 存在的问题

  在Spring Security4中默认启动了csrf，所以在自定义请求的时候就会出现关于csrf的报错解决方式有两种，设置csrf，关闭csrf

  * 设置csrf
  
    在登录页面增加如下内容，即可实现登录
  
  ```jsp
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  ```
  
  * 关闭csrf
  
    在Spring Security中关闭csrf验证
  
  ```xml
   <security:csrf disabled="true"/>
  ```

#### 认证与授权

在Spring Security的配置文件中提供两部分内容， 上面说的都是关于拦截器链的配置方面的的东西，下面来说一下认证方面的东西，有两种方式：

* 用户权限配置在配置文件中，指定好权限信息
* 用户权限配置在数据库中，从数据库中获取配置的权限信息

##### 认证与用户接口

在Spring Security中，用户认证与授权服务都是提供了相应的接口，比如在认证与授权的时候，获取用户信息就需要实现`UserDetailsService`接口，用户需要实现`UserDetails`接口。

* `UserDetailsService`接口

  这个接口只有一个方法`loadUserByUsername`，根据用户名获取用户信息，包含用户信息和权限信息

  ```java
  public interface UserDetailsService {
  	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
  }
  ```

* `UserDetails`接口

  ```java
  public interface UserDetails extends Serializable {
  	/**
  	 * 返回用户权限信息
  	 */
  	Collection<? extends GrantedAuthority> getAuthorities();
  
  	/**
  	 * 返回用户密码，用于做用户登录认证
  	 */
  	String getPassword();
  
  	/**
  	 * 返回用户名，用于做用户登录认证
  	 */
  	String getUsername();
  
  	/**
  	 *用户的帐户是否已过期。过期的帐户不能登录
  	 */
  	boolean isAccountNonExpired();
  
  	/**
  	 * 用户是锁定还是解锁。锁定的帐户不能登录
  	 */
  	boolean isAccountNonLocked();
  
  	/**
  	 * 用户的凭据（密码）是否已过期。过期的密码不能登录
  	 */
  	boolean isCredentialsNonExpired();
  
  	/**
  	 * 用户是否可用，禁用的用户不能登录
  	 */
  	boolean isEnabled();
      // 上面这四个方法在返回false的时候，都会抛出对应的错误
  }
  ```

##### 配置文件处理认证与权限信息

* 权限管理器配置

```xml
	<security:authentication-manager>
        <!--配置用户权限提供信息-->
        <security:authentication-provider>
            <!--用户信息-->
            <security:user-service>
                <!--用户信息 包含认证信息、权限信息-->
                <security:user name="admin" password="1" authorities="ROLE_ADMIN"/>
                <security:user name="chensj" password="1" authorities="ROLE_USER"/>
            </security:user-service>
        </security:authentication-provider>
    </security:authentication-manager>
```

* 拦截器链配置

```xml
<security:http>
        <security:intercept-url pattern="/userLogin" access="permitAll()"/>
        <security:intercept-url pattern="/user/login" access="permitAll()"/>
        <security:intercept-url pattern="/" access="permitAll()"/>
        <security:intercept-url pattern="/index" access="permitAll()"/>
        <security:intercept-url pattern="/index.html" access="permitAll()"/>
        <!--访问权限控制配置-->
        <security:intercept-url pattern="/product/add" access="hasRole('ROLE_ADMIN')"/>
        <security:intercept-url pattern="/product/update" access="hasRole('ROLE_ADMIN')"/>
        <security:intercept-url pattern="/product/list" access="hasRole('ROLE_USER')"/>
        <security:intercept-url pattern="/product/delete" access="hasRole('ROLE_USER')"/>
        <security:intercept-url pattern="/**" access="isFullyAuthenticated()"/>
    </security:http>
```

> 产品查看与修改使用具有ROLE_ADMIN角色的人才可以使用
>
> 产品列表与删除使用具有ROLE_USER角色的人才可以使用
>
> 这种方式处理处于硬编码状态，实际开发中应该是采用数据库模式来实现上述处理

##### UserDetailService接口处理认证与权限信息

另一种方式即通过实现接口的方式，在方法中通过代码的方式来判断用户的登录

关键点：使用UserDetailService接口来实现访问权限控制

1. 实现接口

   ```java
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
   ```

2. 注入UserDetailService

   ```xml
   <!--注入UserDetailService-->
       <bean id="springSecurityUserDetailService" class="org.chen.spring.security4.service.SpringSecurityUserDetailService"/>
   ```

3. 替换

   ```xml
    <security:authentication-manager>
           <!--配置用户权限提供信息-->
           <security:authentication-provider user-service-ref="springSecurityUserDetailService">
               <!--用户信息-->
   <!--            <security:user-service>-->
   <!--                &lt;!&ndash;用户信息&ndash;&gt;-->
   <!--                <security:user name="admin" password="1" authorities="ROLE_ADMIN"/>-->
   <!--                <security:user name="chensj" password="1" authorities="ROLE_USER"/>-->
   <!--            </security:user-service>-->
           </security:authentication-provider>
       </security:authentication-manager>
   ```

   

##### 权限不足处理

在<security:http>标签中指定全局权限不足时候的页面

```xml
<!--指定全局权限不足错误页面-->
<security:access-denied-handler error-page="/error"/>
```

##### 自定义登录成功与失败的处理逻辑

比如说在前后端分离架构中，登录失败或者授权失败的时候应该返回json数据，这个时候就是需要处理自定义登录成功与失败的处理逻辑

* **关键接口**
  1. 登录成功处理：AuthenticationSuccessHandler
  2. 登录失败处理：AuthenticationFailureHandler

###### 登录成功逻辑

1. 实现接口

   ```java
   /**
    * 登录成功后的自定义处理逻辑
    * 实现{@link org.springframework.security.web.authentication.AuthenticationSuccessHandler} 接口
    *
    * @author chensj
    * @date 2019-9-8 17:17:07
    */
   public class SpringAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
       /**
        * jackson 提供工具类，用于转换对象到json字符串
        */
       private ObjectMapper objectMapper = new ObjectMapper();
   
       /**
        * 登录成功后的处理逻辑
        *
        * @param request        请求
        * @param response       响应
        * @param authentication 代表认证成功后的信息
        * @throws IOException      异常
        * @throws ServletException 异常
        */
       @Override
       public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
           // 返回登录成功json给前端
           Map map = new HashMap(8);
           map.put("success", true);
           String json = objectMapper.writeValueAsString(map);
           response.setContentType("text/json;charset=utf-8");
           response.getWriter().write(json);
       }
   }
   ```

2. 注册bean

   ```xml
    <!--自定义登录成功处理类-->
   <bean id="springAuthenticationSuccessHandler"
             class="org.chen.spring.security4.handler.SpringAuthenticationSuccessHandler"/>
   ```

3. 配置登录成功handler

   在<security:form-login>标签上指定authentication-success-handler-ref

   ```xml
   <security:form-login login-page="/userLogin"
                                password-parameter="pass"
                                username-parameter="user"
                                login-processing-url="/user/login"
                                default-target-url="/index"
                                authentication-success-handler-ref="springAuthenticationSuccessHandler"/>
   ```

4. 登录成功返回值为：

   ```json
   {"success":true}
   ```


###### 登录失败逻辑

1. 实现接口

   ```java
   public class SpringAuthenticationFailureHandler implements AuthenticationFailureHandler {
       /**
        * jackson 提供工具类，用于转换对象到json字符串
        */
       private ObjectMapper objectMapper = new ObjectMapper();
   
       /**
        * 登录失败处理逻辑
        *
        * @param request   请求
        * @param response  响应
        * @param exception 异常
        * @throws IOException      异常
        * @throws ServletException 异常
        */
       @Override
       public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
           // 前端登录失败处，返回json给前端
           Map map = new HashMap();
           map.put("success", false);
           String json = objectMapper.writeValueAsString(map);
           response.getWriter().write(json);
       }
   }
   ```

2. 注册bean

   ```xml
   <!--自定义登录失败处理类-->
   <bean id="springAuthenticationFailureHandler"
             class="org.chen.spring.security4.handler.SpringAuthenticationFailureHandler"/>
   ```

3. 配置登录失败handler

   ```xml
    <security:form-login login-page="/userLogin"
                                password-parameter="pass"
                                username-parameter="user"
                                login-processing-url="/user/login"
                                default-target-url="/index"
                                authentication-success-handler-ref="springAuthenticationSuccessHandler"
                                authentication-failure-handler-ref="springAuthenticationFailureHandler"/>
   ```

4. 登录失败返回值为：

   ```json
   {"success":false}
   ```


## 二、Spring Security4 + SSM权限管理

### RBAC模型

​		基于角色的权限访问控制(Role-Based Access Control)，在RBAC中，权限与角色相关联，用户通过成为适当角色的成员而得到这些角色的权限。这就极大地简化了权限的管理。在一个组织中，角色是为了完成各种工作而创造，用户则依据它的责任和资格来被指派相应的角色，用户可以很容易地从一个角色被指派到另一个角色。角色可依新的需求和系统的合并而赋予新的权限，而权限也可根据需要而从某角色中回收。角色与角色的关系可以建立起来以囊括更广泛的客观情况。

### 数据库表结构设计与创建

基于RBAC权限模型，设计权限相关表：

1. 用户
2. 角色
3. 权限

>  用户 和 角色   多对多的关系
>
>  角色 和 权限   多对多的关系

```sql
-- 删除数据库
drop database if exists security4;
-- 创建数据库
CREATE DATABASE security4 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
/*
Navicat MySQL Data Transfer

Source Server         : 192.168.31.217
Source Server Version : 80016
Source Host           : 192.168.31.217:3306
Source Database       : security4

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2019-09-08 22:42:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for SYS_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTH`;
CREATE TABLE `SYS_AUTH` (
  `AUTH_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `AUTH_NAME` varchar(64) DEFAULT NULL COMMENT '权限名称',
  `AUTH_FLAG` varchar(64) DEFAULT NULL COMMENT '权限标识符',
  PRIMARY KEY (`AUTH_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of SYS_AUTH
-- ----------------------------
INSERT INTO `SYS_AUTH` VALUES ('1', '产品查询', 'ROLE_PRODUCT_LIST');
INSERT INTO `SYS_AUTH` VALUES ('2', '产品新增', 'ROLE_PRODUCT_ADD');
INSERT INTO `SYS_AUTH` VALUES ('3', '产品修改', 'ROLE_PRODUCT_UPDATE');
INSERT INTO `SYS_AUTH` VALUES ('4', '产品删除', 'ROLE_PRODUCT_DELETE');

-- ----------------------------
-- Table structure for SYS_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(64) DEFAULT NULL COMMENT '角色名',
  `ROLE_DESC` varchar(64) DEFAULT NULL COMMENT '角色说明',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of SYS_ROLE
-- ----------------------------
INSERT INTO `SYS_ROLE` VALUES ('1', '普通用户', '普通用户');
INSERT INTO `SYS_ROLE` VALUES ('2', '管理员', '管理员');

-- ----------------------------
-- Table structure for SYS_ROLE_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_AUTH`;
CREATE TABLE `SYS_ROLE_AUTH` (
  `ROLE_ID` int(11) NOT NULL COMMENT '角色ID',
  `AUTH_ID` int(11) NOT NULL COMMENT '权限ID',
  KEY `FK_ROLE_AUTH_ROLE` (`ROLE_ID`),
  KEY `FK_ROLE_AUTH_AUTH` (`AUTH_ID`),
  CONSTRAINT `FK_ROLE_AUTH_AUTH` FOREIGN KEY (`AUTH_ID`) REFERENCES `SYS_AUTH` (`AUTH_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_ROLE_AUTH_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `SYS_ROLE` (`ROLE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of SYS_ROLE_AUTH
-- ----------------------------
INSERT INTO `SYS_ROLE_AUTH` VALUES ('2', '1');
INSERT INTO `SYS_ROLE_AUTH` VALUES ('2', '2');
INSERT INTO `SYS_ROLE_AUTH` VALUES ('1', '3');
INSERT INTO `SYS_ROLE_AUTH` VALUES ('1', '4');

-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USER_NAME` varchar(64) DEFAULT NULL COMMENT '用户名',
  `REAL_NAME` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '密码',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `LAST_LOGIN_DATE` date DEFAULT NULL COMMENT '最后登录日期',
  `ACCOUNT_ENABLED` int(11) DEFAULT NULL COMMENT '是否可用',
  `ACCOUNT_EXPIRED` int(11) DEFAULT NULL COMMENT '是否过期',
  `ACCOUNT_LOCKED` int(11) DEFAULT NULL COMMENT '是否锁定',
  `PASSWORD_EXPIRED` int(11) DEFAULT NULL COMMENT '证书是否过期',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of SYS_USER
-- ----------------------------
INSERT INTO `SYS_USER` VALUES ('1', 'admin', 'admin', '123456', '2019-09-08', '2019-09-08', '0', '0', '0', '0');
INSERT INTO `SYS_USER` VALUES ('2', 'chensj', '陈世杰', '123456', '2019-09-08', '2019-09-08', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for SYS_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_ROLE`;
CREATE TABLE `SYS_USER_ROLE` (
  `USER_ID` int(11) DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` int(11) DEFAULT NULL COMMENT '角色ID',
  KEY `FK_USER_ROLE_USER` (`USER_ID`),
  KEY `FK_USER_ROLE_ROLE` (`ROLE_ID`),
  CONSTRAINT `FK_USER_ROLE_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `SYS_ROLE` (`ROLE_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_USER_ROLE_USER` FOREIGN KEY (`USER_ID`) REFERENCES `SYS_USER` (`USER_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of SYS_USER_ROLE
-- ----------------------------
INSERT INTO `SYS_USER_ROLE` VALUES ('1', '2');
INSERT INTO `SYS_USER_ROLE` VALUES ('2', '1');
```



### Spring Security4 + SSM 环境搭建

### 用户查询与权限查询持久层方法

### 自定义UserDetailService实现动态数据访问

### PasswordEncoder密码加密

### 自定义图形验证码

## 三、Spring Security4 + Spring Boot权限管理