#### Sentinel 

#### 一、控制台的使用

下载发行版的jar执行如下命令：

java -Dserver.port=8858 -Dcsp.sentinel.dashboard.server=localhost:8858 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar

默认账密是sentinel/sentinel.可以通过启动参数调整

控制台的使用：https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0

鉴权参考：https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0#%E9%89%B4%E6%9D%83


#### 二、网关限流

https://github.com/alibaba/Sentinel/wiki/%E7%BD%91%E5%85%B3%E9%99%90%E6%B5%81

#### 三、熔断降级
https://github.com/alibaba/Sentinel/wiki/%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7

#### 四、生产环境使用

- 规则管理和推送

结合Nacos，实现文件的持久化存储和实时修改

- 监控

集成influxdb时序数据库，将监控指标持久化，再结合grafana出图，提升逼格

- 鉴权