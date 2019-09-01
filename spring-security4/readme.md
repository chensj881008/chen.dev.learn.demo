# Spring Security 

## Spring Security 执行原理

* 底层：核心springSecurityFilterChain
  由若干个过滤器组成的
  
比如

  UsernamePasswordAuthenticationFilter 用户密码校验
  
  BasicAuthenticationFilter 基于HttpBasic方式校验
  
  等等
  ExceptionTranslationFilter 异常拦截
  FilterSecurityInterceptor 总拦截器 判断结果  决定后面逻辑处理