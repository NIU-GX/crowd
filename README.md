# 1. 项目依赖关系

![](O:\crowd\images\image-20200428113034507.jpg)

# 2. 项目构建

使用maven构建项目

1. 先搭建parent项目，打包方式为pom。

> 用在父级工程或聚合工程中。用来做jar包的版本控制。必须指明这个聚合工程的打包方式为pom

2. parent项目中创建webui模块，打包方式为war

> 这些工程都是要部署在服务器上的，所以要打包成war形式。这些工程有的是用户通过浏览器直接访问，有的是通过发布服务被别的工程调用。

3. parent项目中创建component和entity模块，打包方式为jar

> 就是存放一些其他工程都会使用的类，工具类。我们可以在其他工程的pom文件中去引用它，和引用别的jar包没什么区别。

4. 创建两个和parent项目同级的模块，util和reverse，打包方式为jar

# 3. 构建数据库和表

**需要满足**：原子性，完整性，直接相关性。 

满足三大范式的要求，实际开发中允许局部变通

## 1. 建库

```sql
CREATE DATABASE project_crowd CHARACTER SET utf8;
```

## 2. 建立管理员表

```sql
USE project_crowd;
DROP TABLE if EXISTS t_admin;

CREATE TABLE t_admin
(
	id INT NOT NULL auto_increment, # 主键
	login_acount VARCHAR(255) NOT NULL, # 登录账号
	user_password CHAR(32) NOT NULL, # 登陆密码
	user_name VARCHAR(255) NOT NULL, # 昵称
	email VARCHAR(255) NOT NULL, # 邮箱
	create_time CHAR(19), # 创建时间
	PRIMARY KEY (id)
);
```

# 4. 使用逆向工程生成代码

## 1. 在reverse模块的pom.xml中配置逆向工程插件

```xml
<dependencies>
        <!--mybatis 依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.3</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.5</version>
        </dependency>
        <!--数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.21</version>
        </dependency>
        <!--mysql 驱动-->


    </dependencies>
    <!--控制maven在构建过程中的相关配置-->
    <build>
        <!--构建过程用到的插件-->
        <plugins>
            <!--具体插件-->
            <plugin>
                <!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-maven-plugin -->

                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.5</version>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.11</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```

## 2. 在resource文件夹中写入generatorConfig.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="crowdTables" targetRuntime="MyBatis3">
        <!--自动去除注解-->
        <commentGenerator>
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!--配置数据库连接信息-->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/project_crowd"
                        userId="root"
                        password="password">
        </jdbcConnection>

        <!--默认为false，为true时，把 数据解析为Integer-->
        <javaTypeResolver >
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <!--生成entity的路径-->
        <javaModelGenerator targetPackage="com.example.com.com.example.entity" targetProject=".\src\main\java">
            <!--是否让schema作为包的后缀-->
            <property name="enableSubPackages" value="true" />
            <!--数据库返回额值清理前后的空格-->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <!--映射文件生成的路径-->
        <sqlMapGenerator targetPackage="mapper"  targetProject=".\src\main\resources">
            <!--是否让schema作为包的后缀-->
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>

        <!--Mapper接口生成的位置-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.example.com.example.mapper"  targetProject=".\src\main\java">
            <!--是否让schema作为包的后缀-->
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>

        <!--表和实体的对应-->
        <table tableName="t_admin" domainObjectName="Admin" >
        </table>

    </context>
</generatorConfiguration>
```

## 3. 使用maven插件或者编辑启动代码自动生成

```java
public class Generator {
    public void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        /**指向逆向工程配置文件*/
        File configFile = new File("o:/reverse/src/main/resources/generatorConfig.xml");
        ConfigurationParser parser = new ConfigurationParser(warnings);
        Configuration config = parser.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback, warnings);
        myBatisGenerator.generate(null);
    }
    public static void main(String[] args) throws Exception {
        try {
            Generator generatorSqlmap = new Generator();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

# 4. 使用父工程管理项目依赖

**在父工程的pom文件中加入**：

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>

    <!--使用父工程管理项目依赖-->
    <spring.version>5.0.10.RELEASE</spring.version>
    <spring.security.version>5.0.10.RELEASE</spring.security.version>
</properties>
```



**然后导入需要使用的jar包的依赖**

。。。。



# 5. spring 整合 mybatis 步骤

1. 在子工程中添加具体的依赖
2. 创建spring配置文件，加载jdbc.properties文件
3. 配置数据源
4. 配置sqlsessionfactorybean
	1. 装配数据源
	2. 指定映射配置文件位置
	3. 指定mybatis全局配置文件位置，（可选）



在component子工程中加入依赖，



在webui子工程中整合spring 和 mybatis

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--加载配置文件-->
    <context:property-placeholder location="jdbc.properties"/>

    <!--配置数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="driverClassName" value="${jdbc.driver}"/>
    </bean>

    <!--配置SqlSessionFactoryBean-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--全局配置文件-->
        <property name="configLocation" value="classpath:SqlMapConfig.xml"/>
        <!--映射配置文件-->
        <property name="mapperLocations" value="classpath:/mybatis/*Mapper.xml"/>
    </bean>

    <!--配置MapperScannerConfigurer来扫描Mapper接口所在的包-->
    <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.example.com.example.mapper"></property>
    </bean>
</beans>
```

# 6. 整合日志系统 

spring自带了commons-loggin日志系统。

要转换成**slf4j**的日志系统需要先导入`jcl-over-slf4j.jar`将spring自带的日志系统转化成slf4j，然后在导入具体使用的日志框架的jar包

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.25</version>
</dependency>


<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.30</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
</dependency>
```



**使用**

```java
@Test
public void test() throws SQLException {
    System.out.println(dataSource.getConnection());
    // 传入一个class对象，打印的对象
    Logger logger = LoggerFactory.getLogger(crowdTest.class);
    logger.info(dataSource.getConnection().toString());
}
```



**日志配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">

    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] [%logger]- %msg%n</pattern>
        </encoder>
    </appender>


    <!-- 设置全局日志输出级别 ： debug ， info， warn， error-->
    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>

    <!--根据特殊需要指定局部日志级别-->
    <logger name="com.example.com.example.mapper" level="DEBUG"/>
</configuration>
```



# 6. 声明式事务控制

```java
public void test1() {
    Connection connection = null;
    try {
        connection = dataSource.getConnection();
        // 开启事务
        connection.setAutoCommit(false);

        // 核心操作
        adminSeriver.insert(admin);

        // 提交事务
        connection.commit();

    } catch (Exception e) {
        connection.rollBack();
    } finally {
        connection.close();
    }
}
```



**在框架中，通过一系列配置由spring来管理通用事务操作，然后我们只写核心代码**

通过aop实现事务

```java
@Service
public class AdminService {
    
    public void saveAdmin() {
        。。
    }
}
```



**在spring的配置文件中配置事务**

1. 首先配置包扫描的范围

	```xml
	<context:component-scan base-package="com.example"/>
	```

2. 配置事务管理器

	```xml
	<!--配置事务管理器-->
	<bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	    <property name="dataSource" ref="dataSource"></property>
	</bean>
	```

3. 配置通知

	

```xml
<!--配置事务的通知-->
<tx:advice id="txAdvice" transaction-manager="dataSourceTransactionManager">
    <!--配置事务的属性-->
    <tx:attributes>
        <!--查询方法 : 配置只读属性，让数据库知道这是查询操作，可以进行一定的优化-->
        <tx:method name="get*" read-only="true"/>
        <tx:method name="find*" read-only="true"/>

        <!--增删改方法：配置事务的传播行为，和回滚的异常-->
        <!--
            propagation事务传播行为，
                默认值REQUIRED,表示此方法必须工作在事务中，如果当前线程没有事务，自己开启事务。如果已经有事务，那么就使用已有的事务
                    弊端：使用别人的事务，可能在不需要回滚的时候被回滚。
                建议使用的值REQUIRES_NEW,表示此方法必须工作在事务中，如果当前线程没有事务，自己开启事务，如果已经有事务了，也自己开启新事务
                    优点：不会受到其他事务回滚的影响
        -->
        <!--
            rollback-for：配置根据什么样的异常会回滚
                默认：runtimeException
                建议：编译时异常和运行时异常都回滚
        -->
        <tx:method name="insert*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
        <tx:method name="update*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
        <tx:method name="remove*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
        <tx:method name="batch*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
    </tx:attributes>
</tx:advice>
```

**在基于xml的声明式事务中，事务属性tx:method 是必须配置的，如果某个方法没有配置对应的tx:method,那么事务对这个方法不生效**

# 7. 配置表示层SpringMVC

**tomcat服务器启动的时候会扫描每一个web工程的web.xml目录**，所以要在web.xml中配置三大组件。

1. listener

	ContextLoaderListener：IOC容器监听器，加载spring的配置文件

2. Filter

	CharacterEncodingFilter：配置字符集的过滤器

	HiddenHttpMethodFilter：实现RestFul风格的过滤器

3. servlet

	DispatcherServle：配置前端控制器--> 加载springmvc的配置文件，创建springmvc的ioc容器

**两个ioc容器的关系，spring的ioc容器是mvc的ioc容器的父容器，谁先创建谁就是父容器**

> controller指定的是类，handler指定的类中的一个方法  



```xml
<web-app>
    <display-name>Archetype Created Web Application</display-name>

    <!--配置contextLoaderListener-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:/applicationContext.xml</param-value>
    </context-param>

    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>


    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:/springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <!-- url 的配置方式
            1. /  表示拦截所有路径
            2. 配置请求扩展名，例如 *.html
                优点1.  css, js, 图片等静态资源不经过springmvc不需要特殊处理
                优点2. 可以实现伪静态效果，表面上看起来是访问一个HTML页面，
                    伪静态作用
                    1. 给黑客入侵增大难度
                    2. 有利于SEO优化（让搜索引擎更容易找到我们的项目）
                缺点：不符合RESTFUL风格
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

## 配置springmvc的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <context:component-scan base-package="com.example.controller"/>

    <!--配置springmvc的注解支持-->
    <mvc:annotation-driven/>

    <!--静态资源处理-->
    <mvc:default-servlet-handler></mvc:default-servlet-handler>
    
    <!--配置视图解析器-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>
</beans>
```



## 对ajax请求返回的结果进行规范



# 8.异常映射机制

使用异常映射机制将整个项目的异常和错误进行统一管理

## 1. 基于xml的异常映射，在springmvc的xml中配置

```xml
<!--异常映射-->
<bean id="simpleMappingExceptionResolver" class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
            <prop key="java.lang.Exception">system-error</prop>
        </props>
    </property>
</bean>
```

## 2. 基于注解的异常映射

1. 首先先创建一个判断请求的工具类

```java
public class CrowdUtil {
    /**
     * 判断请求是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");
        return ((accept!=null && accept.contains("application/json")) || (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest")));
    }
}
```

2. 创建异常处理器

```java
@ControllerAdvice
public class ExceptionResolver {

    // @ExceptionHandler 建立异常和类的映射关系
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolverNullPointerException(NullPointerException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 判断请求的类型
        boolean requestType = CrowdUtil.isAjaxRequest(request);

        // 如果是ajax请求
        if (requestType) {
            // 创建ResultEntity
            ResultEntity<Object> failed = ResultEntity.failed(e.getMessage());
            // 创建Gson
            Gson gson = new Gson();
            // 将ResultEntity转为json
            String json = gson.toJson(failed);
            // 将json数据返还给浏览器
            response.getWriter().write(json);
            // 因为使用了原生respones对象返回了响应，不提供ModelAndView对象
            return null;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("exception",e);
            modelAndView.setViewName("system-errer");
            return modelAndView;
        }
    }
}
```



# 9. 引入前端静态资源

引入layer弹框组件。

引入jquery

引入bootstrap





略过。。。

# 10. 管理员登录

1. 首先要对密码进行加密，采用hash中的MD5加密算法，中等强度的加密算法
2. 加密后的密码存入数据库，登录时将输入的密码再次使用加密算法比较加密后的结果是否相同

**创建MD5加密的工具类**

```java
public class MD5Util {
    public static String md5(String source) {
        // 1. 先判断source是否有效
        if (source == null || source.length() == 0) {
            throw new RuntimeException(ConstantUtil.MESSAGE_STRING_INVALIDATE);
        }

        // 2. 获取MessageDigest对象
        try {
            String algorithm = "md5";
            MessageDigest instance = MessageDigest.getInstance(algorithm);

            // 3. 获取明文密码队友的字节数组
            byte[] bytes = source.getBytes();
            // 4. 执行加密
            byte[] digest = instance.digest(bytes);
            // 5. 创建BigInteger
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, digest);
            // 6. 按照16进制转换为字符串
            int radix = 16;
            String result = bigInteger.toString(radix);

            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

**编写登录的controller，service，mapper和异常处理**

```java
@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    // 登录
    @RequestMapping(value = "/admin/login")
    public String adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Logger logger = LoggerFactory.getLogger(LoginController.class);
        logger.info(username + " " + password);

        Admin admin = adminService.selectByLoginAccount(username,password);
        session.setAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main";
    }

    // 登出
    @RequestMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return"redirect:/admin/to/logout";
    }
}
```

## 1. 防止表单重新提交

需要修改controller的返回路径，使用重定向，同时使用view-controller映射路径。

```java
@RequestMapping(value = "/admin/login")
public String adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    logger.info(username + " " + password);

    Admin admin = adminService.selectByLoginAccount(username,password);
    session.setAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN,admin);
    return "redirect:/admin/to/main";
}
```

## 2. 提取main.html的公共部分

```html
<%@include file="include-head.jsp" %>
<%@include file="include-nav.jsp" %>
<%@include file="include-sidebar.jsp"%>
```

# 11. 配置拦截器

拦截请求，查看请求是否是登录用户的请求，如果登录放行，没有登录转向登录页面。

不拦截访问公共资源的请求。

## 1. 创建拦截器

```java
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 先获取session对象
        HttpSession session = request.getSession();

        // 2. 尝试获取Admin对象
        Admin admin = (Admin) session.getAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN);

        // 3. 判断admin是否为空
        if (admin == null) {
            // 4. 抛出异常，访问被拒绝异常
            throw new AccessForbidenException(ConstantUtil.MESSAGE_ACCESS_FORBIDEN);
        }
        // 5. 如果admin不为空，返回true放行
        return true;
    }
}
```

## 2. 注册拦截器

**在springmvc配置文件中配置**

```xml
<!--注册拦截器-->
<mvc:interceptors>
    <mvc:interceptor>
        <!--配置拦截器要拦截的资源
            /** ： 对应多层路径        /aaa/bbb/ccc
            /*  ： 对应一层路径        /aaa
        -->
        <mvc:mapping path="/**"/>

        <!--配置不拦截的资源-->
        <mvc:exclude-mapping path="/admin/login"/>
        <mvc:exclude-mapping path="/admin/to/logout"/>
        <mvc:exclude-mapping path="/login.html"/>
        <bean class="com.example.interceptor.LoginInterceptor"></bean>
    </mvc:interceptor>
</mvc:interceptors>
```

# 12. 分页显示

## 1. 引入pageHelper

```xml
<!--mybatis 分页插件-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.1.11</version>
</dependency>
```

## 2. 在sqlsessionFactoryBean中配置pageHelper

```xml
<property name="plugins">
    <array>
        <bean class="com.github.pagehelper.PageInterceptor">
            <property name="properties">
                <value>
                    dialet = mysql
                    reasonable = true
                </value>
            </property>
        </bean>
    </array>
</property>
```

## 3. 在mapper.xml中编写sql语句

```xml
<select id="selectAdminByKeyWord" resultMap="BaseResultMap">
    select *
    from t_admin
    where login_acount like concat('%',#{keyWord},'%')
       or user_name like concat('%',#{keyWord},'%')
       or email like concat('%',#{keyWord},'%')
</select>
```

## 4. controller， service，mapper编写代码

```java
@RequestMapping("/admin/getpage")
public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "")
                          String keyword,
                          @RequestParam(value = "pageNum", defaultValue = "1") 
                          Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") 
                          Integer pageSize,
                          ModelMap modelMap) {
    // 1. 获取pageInfo
    PageInfo<Admin> adminPageInfo = adminService.selectAdminByKeyWord(keyword, pageNum, pageSize);
    // 2. 将pageInfo存入Model
    modelMap.addAttribute(ConstantUtil.ATTR_NAME_PAGEINFO,adminPageInfo);

    return "/admin-page";
}


@Override
    public PageInfo<Admin> selectAdminByKeyWord(String keyWord, Integer pageNum, Integer pageSize) {
        // 1. 开启pageHelper的静态方法，开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        // 2. 执行查询
        List<Admin> admins = adminMapper.selectAdminByKeyWord(keyWord);

        // 3. 疯转到pageInfo对象中
        PageInfo<Admin> pageInfo = PageInfo.of(admins);

        return pageInfo;
    }


```

# 13. 实现用户的增删改

## 1. 先给按钮添加超链接

```xml
<button type="button" class="btn btn-success btn-xs">
     <a class=" glyphicon glyphicon-check" href="amdin/"></a>
</button>
<button type="button" class="btn btn-primary btn-xs">
     <a class=" glyphicon glyphicon-pencil" href="admin/update/${admin.id}"></a>
</button>
<button type="button" class="btn btn-danger btn-xs">
     <a class=" glyphicon glyphicon-remove" href="admin/delete/${admin.id}"></a>
</button>
```

## 2. 编写controller

### 1. 删除

```java
@RequestMapping("/admin/delete/{id}/{pageNum}/{keyword}")
public String deleteAdmin(@PathVariable("id") Integer id,
                          @PathVariable("pageNum") Integer pageNum,
                          @PathVariable(value = "keyword") String keyword) {
    adminService.deleteAdminByPrimaryKey(id);

    return "redirect:/admin/getpage?pageNum=" + pageNum + "&keyword=" + keyword;
}

@RequestMapping("/admin/delete/{id}/{pageNum}")
public String deleteAdminWithoutKeyword(@PathVariable("id") Integer id,
                                        @PathVariable("pageNum") Integer pageNum) {
    adminService.deleteAdminByPrimaryKey(id);

    return "redirect:/admin/getpage?pageNum=" + pageNum;
}
```

### 2. 增加

增加用户要求登录账号不能重复，在数据库中给login_account字段添加unique索引。

```java
@RequestMapping("/admin/add")
public String saveAdmin(Admin admin) {
    adminService.saveAdmin(admin);
    // 为了让用户第一眼就看到新增加的用户，直接跳转到最后一页
    return "redirect:/admin/getpage?pageNum=" + Integer.MAX_VALUE;
}
```

添加账户重复异常处理

```java
@ExceptionHandler(LoginAccountAlreadlyInUse.class)
public ModelAndView resolverLoginAccountAlreadlyInUseException(LoginAccountAlreadlyInUse e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String viewName = "/add";
    ModelAndView modelAndView = common(viewName, e, request, response);
    return modelAndView;
}
```

## 3. 修改

```java
@RequestMapping("/admin/to/edit")
public String toAdminEdit(@RequestParam("adminId") Integer adminId, ModelMap map) {

    Admin admin = adminService.selectAdminById(adminId);
    map.addAttribute("admin", admin);
    return "/edit";
}

@RequestMapping("/admin/edit")
public String editAdmin(Admin admin,
                        @RequestParam("pageNum") Integer pageNum,
                        @RequestParam("keyword") String keyword,
                        ModelMap map) {

    adminService.updateAdmin(admin);
    return "redirect:/admin/getpage?pageNum=" + pageNum + "&keyword=" + keyword;
}
```

# 14. 角色控制（权限控制）

## 1. 如何进行权限控制

### 1. 定义资源

资源就是系统需要保护起来的功能。具体形式有很多种，URL地址，controller方法，service方法，页面等都可以定义为资源被权限控制系统保护起来 。

### 2. 创建权限

功能复杂的项目会有很多的资源，单个操作资源过于复杂，。为了简化操作，可以将多个资源封装在一起，打包成一个“权限”，分配给有需要的人。

### 3. 创建角色

角色就是用户的分组，先给角色分配权限，再把角色分配给用户

### 4. 管理用户

用户就是登录系统的账号密码

### 5. 建立关联关系

权限-----资源：单向多对多

​		单向： java类之间单向，从权限实体类可以获取到资源对象的集合，但是通过资源获取不到权限

​		多对多： 数据库表真间多对多

角色-----权限：单向多对多

用户-----角色：双向多对多

## 2. 数据库多对多实现

多对多的实现需要依靠中间表

## 3. RBAC权限模型（Role-Based Access Contro**l）**

**基于角色的访问控制**： 一个用户可以对应多个角色，一个角色拥有多个权限

## 4.创建角色数据表

```sql
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

**利用mybatis逆向工程生成实体，mapper，和映射配置文件**

# 15. 给Admin分配role

admin和role是多对多关系，要建立中间表。

```sql
CREATE TABLE `inner_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

然后编写  controller, service, mapper

# 16. 过渡到分布式

## **单一架构** 

一个工程 ->  一个war包 -> **运行在一个tomcat上**



## **1.为什么架构要发生转变？**

> 1. 高可扩
>
> 	现在项目在设计架构的时候要考虑到功能的持续更新
>
> 2. 高性能
>
> 	提高响应速度，降低用户的的等待时间
>
> 3. 高并发
>
> 	用户访问量非常大，会增加系统的并发压力



## **2.分布式架构**

 一个项目拆分成多个模块工程，每个模块都是一个war包，运行在各自的tomcat上，模块间通过网络相互调用。

### 1.方法远程调用

#### 1. 本地调用

​	在同一个项目内，不经过网络直接调用方法，不论自己声明的方法还是jar中的方法。

#### 2. 远程调用

![image-20200509112241536](O:\crowd\images\image-20200509112241536.jpg)

**远程调用的意义：**

 1.   对内： 可以实现分布式架构

 2.   对外： 让我们调用第三方接口

	 eg： 发短信 ， 支付， 天气预报等。

	

### 2. SOA  和  微服务



SOA： 面向服务架构。把相同的功能抽取出来作为一个服务，供系统的其他模块调用，提高代码的复用性

微服务： 独立，可部署。