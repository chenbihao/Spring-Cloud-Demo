## readme

此目录存放：修改nacos持久化版本编译的jar包 以及自用的 Dockerfile


## Dockerfile 用法


```shell
# cd 到你存放 Dockerfile 的目录
docker build -t sentinel-nacos:1.8.8 .

docker run -d --name sentinel-nacos -p 8718:8718 -e JAVA_OPTS="-Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718 -Dproject.name=sentinel-dashboard -Dnacos.serverAddr=192.168.31.112:8848 -Dnacos.username=nacos -Dnacos.password=nacos112 -Dsentinel.dashboard.auth.username=sentinel -Dsentinel.dashboard.auth.password=sentinel112" sentinel-nacos:1.8.8
```



## 纯命令用法


### 普通命令

java -Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.8.jar

### 如果用powershell：

java '-Dserver.port=8718' '-Dcsp.sentinel.dashboard.server=localhost:8718' '-Dproject.name=sentinel-dashboard' -jar sentinel-dashboard-1.8.8.jar


### 如果用powershell + 改造nacos持久化 + 鉴权：

java '-Dserver.port=8718' '-Dcsp.sentinel.dashboard.server=localhost:8718' '-Dproject.name=sentinel-dashboard' '-Dnacos.serverAddr=192.168.31.112:8848' '-Dnacos.username=nacos' '-Dnacos.password=nacos112' '-Dsentinel.dashboard.auth.username=sentinel' '-Dsentinel.dashboard.auth.password=sentinel112' -jar sentinel-dashboard-1.8.8-nacos.jar





