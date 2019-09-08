# Spring Security 

## Spring Security 执行原理

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

## Spring Security 学习

### Spring Security配置文件

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

### 自定义登录页面

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

### 认证与授权

在Spring Security的配置文件中提供两部分内容， 上面说的都是关于拦截器链的配置方面的的东西，下面来说一下认证方面的东西，如下内容即是一种使用硬编码的方式来指定用户登录的信息

#### 配置文件处理认证与权限信息

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

#### UserDetailService接口处理认证与权限信息

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

   

#### 权限不足处理

在<security:http>标签中指定全局权限不足时候的页面

```xml
<!--指定全局权限不足错误页面-->
<security:access-denied-handler error-page="/error"/>
```

#### 自定义登录成功与失败的处理逻辑

比如说在前后端分离架构中，登录失败或者授权失败的时候应该返回json数据，这个时候就是需要处理自定义登录成功与失败的处理逻辑

* **关键接口**
  1. 登录成功处理：AuthenticationSuccessHandler
  2. 登录失败处理：AuthenticationFailureHandler

##### 登录成功逻辑

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


##### 登录失败逻辑

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

   