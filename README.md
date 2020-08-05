<div align=center>博安智联</div>

---
### 相关工程

###### 采用前后端分离架构，前端为独立工程。
###### dna-cloud 是后台工程，核心框架：Spring Cloud

### 核心服务
###### bazl-dna-eureka 注册中心 - 8700
###### bazl-dna-config 配置中心 - 8702
###### bazl-dna-zuul   网关前置 - 8704
###### bazl-dna-turbine 监控服务 - 8703
###### bazl-dna-zipkin 链路跟踪 - 8701 && 9411

---
### 基础服务组件
###### bazl-dna-common 工具包
###### bazl-dna-system 认证中心 - 8707
###### bazl-dna-files 文件中心 - 8710

### 混合库
###### bazl-dna-nation-connector 国家库 - 8705
###### bazl-dna-mix 混合库 - 8706

### dna库
###### bazl-dna-database-service dna库工具包

### 服务组件
###### bazl-dna-database-core dna库 - 8711
###### bazl-dna-database-compare 比对服务 - 8708
###### bazl-dna-database-nation-converter 数据转换 - 8715
###### bazl-dna-database-sync 数据同步到redis - 8712
###### bazl-dna-database-transfer  - 8713
###### bazl-dna-database-lims-converter  - 8717
###### bazl-dna-dpznsy 毒品溯源  - 8090
###### bazl-dna-caseinfo-accept 案件受理  - 8718
###### bazl-dna-caseinfo-reg 案件委托  - 8719

### 测试组件
###### bazl-dna-test 测试项目 -8787
###### bazl-dna-elasticsearch 测试 Elasticsearch - 8709
---

### turbine 监控服务地址
##### http://localhost:8703/hystrix
##### http://localhost:8704/actuator/hystrix.stream

#### spring cloud bus 消息总线
##### http://ip:port/actuator/bus-refresh 刷新配置文件
##### http://ip:port/actuator/loggers 更新日志级别

