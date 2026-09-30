# 项目基石说明
knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案,前身是swagger-bootstrap-ui,取名knife4j是希望她能像一把匕首一样小巧,轻量,并且功能强悍!

knife4j的前身是`swagger-bootstrap-ui`，为了契合微服务的架构发展,由于原来`swagger-bootstrap-ui`采用的是后端Java代码+前端Ui混合打包的方式,在微服务架构下显的很臃肿,因此项目正式更名为`knife4j`

# 特别声明
一、主旨  
本项目（knife4j simple）基于knife4j（4.5.0），作为其简易版本发布，主要为仅需查看文档，且需要适配Spring boot各版本的用户。
若需要其它高级功能定制请移步[knife4j官网](https://doc.xiaominfo.com/)，或使用[knife4jnext](https://knife4jnext.com/guide/getting-started)。  

二、构建目的  
项目仅引用knife4j ui包（knife4j-openapi3-ui），针对knife4j java源代码
进行复制，并改造以适配Spring boot各版本。改造过程中，会去除项目
中大部分第三方依赖及代码警告。同时统一maven坐标，方便使用。


# 使用帮助
访问：http://ip:port/doc.html

即可查看文档  

# 模块说明
_[knife4j-openapi3-spring-boot2-starter](knife4j-openapi3-spring-boot2-starter)_ 适配Spring boot 2  
_[knife4j-openapi3-spring-boot3-starter](knife4j-openapi3-spring-boot3-starter)_ 适配Spring boot 3  
_[knife4j-openapi3-spring-boot4-starter](knife4j-openapi3-spring-boot4-starter)_ 适配Spring boot 4  
_[knife4j-openapi3-spring-boot2-webflux-starter](knife4j-openapi3-spring-boot2-webflux-starter)_ 适配Spring boot 2 webflux  

## 依赖版本
### maven坐标（以knife4j-openapi3-spring-boot2-starter为例）
    <dependency>
        <groupId>io.github.zbeboy</groupId>
        <artifactId>knife4j-openapi3-spring-boot2-starter</artifactId>
        <version>${version}</version>
    </dependency>`

### knife4j-openapi3-spring-boot2-starter
| 版本          | Spring doc | Spring boot |JDK|
|-------------|------------|-------------|-------------|
| 1.1-RELEASE | 1.8.0        | 2.7.18         |8|
| 1.0-RELEASE | 1.8.0        | 2.7.18         |8|

### knife4j-openapi3-spring-boot3-starter
| 版本          | Spring doc | Spring boot | JDK |
|-------------|------------|-------------|-----|
| 1.1-RELEASE | 2.9.1      | 3.5.16         | 17  |
| 1.0-RELEASE | 2.9.1      | 3.5.16         | 17  |

### knife4j-openapi3-spring-boot4-starter
| 版本          | Spring doc | Spring boot | JDK |
|-------------|------------|-------------|-----|
| 1.1-RELEASE | 3.1.1      | 4.1.1         | 17  |

### knife4j-openapi3-spring-boot2-webflux-starter
| 版本          | Spring doc | Spring boot | JDK |
|-------------|------------|-------------|-----|
| 1.1-RELEASE | 1.8.0      | 2.7.18         | 8   |

# 发布说明
## 1.0-RELEASE
1. 基础功能适合发布