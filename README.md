# MicroService
#### 一、eureka-server
作为注册中心，暂时只提供服务注册功能

#### 二、config-server
作为配置中心，为各个模块提供统一配置文件服务，可以关联git，实现自动刷新。
目前只是本地启动，可通过命令触发配置文件刷新。

#### 三、app-core-server
作为基础模块，提供通用工具类和bean服务，作为jar被各业务模块使用使用。
redis、MQ、数据库以及日志框架，由各业务模块自行实现

- 工具类盖含：时间转换、加解密（aes，md5，rsa等）、数字转换、json处理、IP处理、重试机制、JWT和公共响应处理

#### 四、api-gateway-server
API网关，提供熔断、降级、路由等功能，是所有业务模块的前置服务

#### 五、app-provider
业务模块，服务提供者

#### 六、app-consumer
业务模块、消费者

#### 七、app-consumer-feign
业务模块、带有客户端负载均衡的消费者

### 注意事项
- 1、进行docker编译前，保证docker已经运行登录
- 2、mvn clean install dockerfile:build -DskipTests=true设置maven编译

### 问题及处理方式
- 1.spring-boot-maven-plugin打包出来的jar是不可依赖的

方式1：https://blog.csdn.net/guduyishuai/article/details/60968728


方式2：移除parent的pom中的spring-boot-maven-plugin插件，移除被依赖的jar中的spring-boot-maven-plugin插件，
即不使用spring-boot-maven-plugin打包可避免该问题。其余模块自己添加spring-boot-maven-plugin即可