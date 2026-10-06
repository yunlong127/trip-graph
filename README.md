# Trip Graph - 旅行规划系统

一个基于 Vue 3 + Spring Boot 的旅行规划管理系统，支持用户注册/登录、旅行计划（Trip）的创建与管理、行程安排以及预订信息管理。

## 技术栈

- **前端**：Vue 3 + Vite + TypeScript + Vue Router + Axios
- **后端**：Spring Boot 3.2.0 + Spring Data JPA + Java 17
- **数据库**：MySQL 8

## 项目结构

```
.
├── travel-planner/            # 前端（Vue 3 + Vite）
│   └── src/
│       ├── views/             # 页面视图
│       ├── router/            # 路由配置
│       └── assets/            # 静态资源
├── travel-planner-backend/    # 后端（Spring Boot）
│   └── src/main/
│       ├── java/com/travel/   # Java 源码
│       │   ├── controller/    # 控制器
│       │   ├── service/       # 业务逻辑
│       │   ├── repository/    # 数据访问
│       │   └── entity/        # 实体
│       └── resources/         # 配置文件
├── create_database.sql        # 建库建表脚本
├── init_db.bat                # Windows 数据库初始化脚本
└── init_db.sql                # 数据库初始化脚本
```

## 快速开始

### 1. 初始化数据库

在 MySQL 中执行建库脚本：

```bash
mysql -u root -p < create_database.sql
```

或在 Windows 下直接运行：

```bash
init_db.bat
```

### 2. 配置数据库连接

编辑 `travel-planner-backend/src/main/resources/application.properties`，将数据库用户名和密码修改为你本地的配置：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/travel_planner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=你的密码
```

### 3. 启动后端

```bash
cd travel-planner-backend
mvn spring-boot:run
```

后端默认运行在 `http://localhost:8080`。

### 4. 启动前端

```bash
cd travel-planner
npm install
npm run dev
```

前端默认运行在 `http://localhost:5173`。
