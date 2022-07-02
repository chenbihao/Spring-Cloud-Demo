[TOC]

# 练手项目：优惠券模板服务

来自极客时间的 Spring Cloud 微服务项目实战





## Spring Boot 搭建（V1）



目录结构：

```
coupon-center
├── coupon-calculation-serv		// 计算服务
│   ├── coupon-calculation-api
│   ├── coupon-calculation-impl
│   └── target
├── coupon-customer-serv		// 用户接口
│   ├── src
│   └── target
├── coupon-template-serv		// 优惠券模板服务
│   ├── coupon-template-api			// 存公共类或对外接口，提供一个“干净”的接口包给其它服务引用
│   ├── coupon-template-dao			// 实体与DAO
│   └── coupon-template-impl		// 核心业务逻辑
├── middleware					// 平台类
│   ├── src
│   └── target
└── 资源文件
```

































