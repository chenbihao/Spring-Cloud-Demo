## readme

此目录存放：修改nacos持久化版本 编译的jar包 以及 Dockerfile


## Dockerfile 用法






## 纯命令用法


### 普通命令

java -Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.8.jar

### 如果用的 powershell：

java '-Dserver.port=8718' '-Dcsp.sentinel.dashboard.server=localhost:8718' '-Dproject.name=sentinel-dashboard' -jar sentinel-dashboard-1.8.8.jar


### powershell+改造的包：

java '-Dserver.port=8718' '-Dcsp.sentinel.dashboard.server=localhost:8718' '-Dproject.name=sentinel-dashboard' '-Dnacos.serverAddr=192.168.31.112:8848' '-Dnacos.username=nacos' '-Dnacos.password=nacos112' '-Dsentinel.dashboard.auth.username=sentinel' '-Dsentinel.dashboard.auth.password=sentinel112' -jar sentinel-dashboard-1.8.8-nacos.jar





