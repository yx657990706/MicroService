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


### 新微服务设计思路
- 1.提供一个基础jar服务，实现一些可以通用的工具类和公共模块
- 2.各个微服务有自己完整的业务。需要跨服务调用时可以考虑使用RPC框架。暂时使用Spring的restful风格调用
- 3.看业务需要。例如需要一个会员端和一个后台管理系统。需要2套独立的认证。则可以使用多网关BFF模式。demo暂时简单一些。采用单一网关。
- 4.采用nacos作为服务的注册及配置中心（要考虑好是否需要动态更新配置还是部分动态更新）
- 5.网关使用sentinel来做流控及路由（sentinel时间窗口是5秒。如果要监控，需要采用时序数据库来做持久化）
- 6.定时任务采用xxl-job来实现

### 分析
#### 1.数据存储及中间件
1.mysql     几乎所有应用都要使用。各个服务的库可以不同 (可以通过相同的key，不同value的配置文件实现)
2.redis     几乎所有应用都要使用。各个服务的库可以不同(可以通过相同的key，不同value的配置文件实现，通过配置文件读取的方式)
3.mongo     根据需要使用。各个服务的库可以不同(可以通过相同的key，不同value的配置文件实现)
4.ES        指定服务调用。所有ES操作同意提供
5.RabbitMQ  根据需要使用。各个服务的队列不同


  
