# MicroService
#### 一、eureka-server
作为注册中心，暂时只提供服务注册功能

#### 二、config-server
作为配置中心，为各个模块提供统一配置文件服务，可以关联git，实现自动刷新。
目前只是本地启动，可通过命令触发配置文件刷新。

#### 三、commons-model
作为公共model模块，存放通用的bean对象，供所有对象使用。

#### 四、api-gateway-server
API网关，提供熔断、降级、路由等功能，是所有业务模块的前置服务

#### 五、app-provider
业务模块，服务提供者

#### 六、app-consumer
业务模块、消费者

#### 七、app-consumer-feign
业务模块、带有客户端负载均衡的消费者

