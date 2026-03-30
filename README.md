# 🚦 Sentinel 微服务流量治理 课程配套实战Demo
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Spring Cloud Alibaba](https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2023.0.1.2-blue)
![Sentinel](https://img.shields.io/badge/Sentinel-1.8.7-orange)
![JDK](https://img.shields.io/badge/JDK-17+-red)
![License](https://img.shields.io/badge/License-MIT-green)

本项目是 **Sentinel 微服务流量治理课程** 专属配套实战Demo，完整覆盖课程全量知识点，每节课的核心代码都有对应的提交记录与分支，全程可复现、可测试、可落地，适配IDEA+Docker环境，零基础也能跟着一步步上手。

---

## 📚 课程章节对应关系
| 课程章节 | 对应代码分支/提交记录 | 核心知识点 |
|----------|------------------------|------------|
| 第1课：Sentinel 入门与环境初始化 | main分支 初始化demo | 项目搭建、Sentinel 核心依赖引入、控制台接入 |
| 第12课：隔离与降级 - 线程隔离 | master分支 隔离和降级 - 线程隔离 | 信号量隔离、线程池隔离、服务熔断降级核心原理 |
| 后续课程更新 | 对应独立分支 | 限流全模式、热点参数限流、Feign整合、网关限流等 |

---

## 🎯 核心知识点全覆盖
- ✅ 流量控制：QPS限流、线程数限流、匀速排队、冷启动模式
- ✅ 熔断降级：慢调用比例、异常比例、异常数熔断策略
- ✅ 流量隔离：信号量隔离、线程池隔离、服务间调用隔离
- ✅ 热点防护：热点参数限流、参数例外项配置
- ✅ 服务整合：OpenFeign 服务调用限流熔断、RestTemplate 适配
- ✅ 可视化监控：Sentinel Dashboard 实时监控、规则动态配置
- ✅ 规则持久化：Nacos 配置中心规则持久化（扩展内容）

---

## 🛠️ 环境准备
| 依赖 | 版本要求 | 说明 |
|------|----------|------|
| JDK | 17+ | Spring Boot 3.x 强制要求 |
| Maven | 3.6.0+ | 项目依赖管理 |
| Sentinel Dashboard | 1.8.7+ | 可视化控制台，与客户端版本保持一致 |
| Docker | 20.0+ | 快速启动控制台、Nacos等组件（可选） |

---

## 🚀 快速启动（保姆级步骤）
### 1. 拉取项目代码
```bash
# 克隆项目
git clone https://github.com/kaiyuzhao116/Sentinel-demo.git

# 进入项目目录
cd Sentinel-demo

# 切换到对应课程分支（比如第12课线程隔离）
git checkout master
```

### 2. 启动 Sentinel 控制台
#### 方式一：Docker 一键启动（推荐）
```bash
docker run -d \
  --name sentinel-dashboard \
  -p 8858:8858 \
  -e JAVA_OPTS="-Dserver.port=8858 -Dsentinel.dashboard.auth.username=sentinel -Dsentinel.dashboard.auth.password=sentinel" \
  bladex/sentinel-dashboard:1.8.7
```

#### 方式二：本地Jar包启动
1.  下载 [sentinel-dashboard-1.8.7.jar](https://github.com/alibaba/Sentinel/releases/download/1.8.7/sentinel-dashboard-1.8.7.jar)
2.  执行启动命令：
    ```bash
    java -jar sentinel-dashboard-1.8.7.jar --server.port=8858
    ```

启动完成后，访问控制台：`http://localhost:8858`，账号密码均为 `sentinel`。

### 3. 项目配置修改
打开 `src/main/resources/application.yml`，修改核心配置：
```yaml
spring:
  application:
    name: sentinel-demo
  cloud:
    sentinel:
      transport:
        # 控制台地址，和你启动的端口保持一致
        dashboard: localhost:8858
        # 客户端通信端口，不冲突即可
        port: 8719
      # 开启懒加载关闭（项目启动直接注册到控制台）
      eager: true
```

### 4. 启动项目
1.  IDEA中打开项目，等待Maven依赖加载完成
2.  找到启动类 `SentinelDemoApplication`，点击启动
3.  项目启动成功后，刷新Sentinel控制台，即可看到 `sentinel-demo` 服务

### 5. 接口测试
访问测试接口触发流量，即可在控制台配置规则、查看监控：
```
# 基础测试接口
GET http://localhost:8080/test/hello

# 线程隔离测试接口（对应第12课）
GET http://localhost:8080/test/isolation/thread
```

---

## 📋 完整测试接口清单
| 接口地址 | 请求方式 | 对应知识点 | 测试说明 |
|----------|----------|------------|----------|
| `/test/limit/qps` | GET | QPS流量控制 | 配置QPS阈值后，超过阈值直接触发限流 |
| `/test/limit/thread` | GET | 线程数限流 | 限制并发线程数，超出排队等待 |
| `/test/isolation/thread` | GET | 线程池隔离 | 课程第12课核心示例，独立线程池隔离服务调用 |
| `/test/isolation/semaphore` | GET | 信号量隔离 | 轻量级隔离方式，限制并发请求数 |
| `/test/circuit/slow` | GET | 慢调用熔断 | 慢调用比例达到阈值后，触发服务熔断 |
| `/test/circuit/error` | GET | 异常比例熔断 | 业务异常占比过高时，触发熔断降级 |
| `/test/hotparam` | GET | 热点参数限流 | 对高频访问的参数单独限流，支持例外项 |

---

## ❌ 常见问题排查
1.  **Sentinel控制台看不到服务**
    - 检查控制台地址配置是否正确，端口是否匹配
    - 关闭本地防火墙/VPN，确保客户端和控制台网络互通
    - 访问一次项目接口，触发客户端注册
    - 检查 `eager: true` 配置是否开启

2.  **配置的限流规则不生效**
    - 检查资源名（@SentinelResource 的 value）是否和控制台配置的一致
    - 确认规则配置在正确的服务名下，没有选错服务
    - 检查是否有异常被捕获，导致Sentinel无法统计异常数

3.  **Feign整合Sentinel不生效**
    - 检查配置是否开启：`feign.sentinel.enabled=true`
    - 确认FallBack类实现了Feign接口，且被Spring容器管理

---

## 📖 学习资料参考
- [Sentinel 官方中文文档](https://sentinelguard.io/zh-cn/docs/introduction.html)
- [Spring Cloud Alibaba 官方文档](https://sca.aliyun.com/docs/2023/overview/what-is-sca/)
- [Sentinel Dashboard 操作指南](https://sentinelguard.io/zh-cn/docs/dashboard.html)

---

## 📄 License
本项目采用 MIT 开源协议，可自由用于学习、二次开发与商业项目。
