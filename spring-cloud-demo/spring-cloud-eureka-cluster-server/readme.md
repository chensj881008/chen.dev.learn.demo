# 高可用注册中心
* 配置application.yml

集群Eureka注册中心 2
```yaml
<!--application-peer1.yml-->
### 服务端口号
server:
  port: 8100
spring:
  application:
    name: eureka-server
eureka:
  instance:
    ### 注册中心ip地址
    hostname: peer1
  client:
    serviceUrl:
      ### 注册地址
      defaultZone: http://peer2:8200/eureka/
    ### 本身为注册中心，是否需要在注册中心注册，默认true  集群设置为true
    register-with-eureka: false
    ### 本身为注册中心，不需要去检索服务信息
    fetch-registry: false
```
集群Eureka注册中心 2
```yaml
<!--application-peer2.yml-->
### 服务端口号
server:
  port: 8200
spring:
  application:
    name: eureka-server
eureka:
  instance:
    ### 注册中心ip地址
    hostname: peer2
  client:
    serviceUrl:
      ### 注册地址
      defaultZone: http://peer1:8100/eureka/
    ### 本身为注册中心，是否需要在注册中心注册，默认true  集群设置为true
    register-with-eureka: false
    ### 本身为注册中心，不需要去检索服务信息
    fetch-registry: false

```

## 单机测试
* 修改hosts文件
```bash
127.0.0.1 peer1
127.0.0.1 peer2
```

## 启动命令
```bash
java -jar spring-cloud-eureka-cluster-server-1.0.0.jar --spring.profile.peer1
java -jar spring-cloud-eureka-cluster-server-1.0.0.jar --spring.profile.peer2
```