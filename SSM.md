# Spring

## Spring程序开发步骤

1. 导入Spring开发的基本包坐标
2. 编写Dao接口和实现类
3. 创建Spring核心配置文件(applicationContext.xml)
4. 在Spring配置文件中配置类
5. 使用Spring的API获取Bean实例
   * ApplicationContext接口
   * ClassPathXmlApplicationContext **implements** ApplicationContext

` ApplicationContext ctsx = new ClassPathXmlApplicationContext("applicationContext.xml") `

* javaBean：标准的java类

  * 类必须被public修饰
  * 必须提供空参构造
  * 成员变量必须使用private修饰
  * 必须提供pubilc的get，set方法

## Bean标签基本配置

  * 用于配置对象交由Spring来创建。默认情况下它调用的是类中的无参构造函数，如果没有无参构造函数则不能创建成功
  * 基本属性

    * id：Bean实例在Spring容器中的唯一标识
    * class：Bean的全限定名称
    * scope：指对象的作用范围
      * singleton：默认的，单例的
        * **当应用加载，创建容器时，对象就被创建了**
        * **只要容器还在，对象就一直存在**
        * **当应用卸载，销毁容器时，对象就被销毁了**

      * prototype：多例的
        * **当使用对象时，创建新的对象实例**
        * **只要对象在使用中，就一直存在**
        * **当对象长时间不使用时，会被java的垃圾回收器回收掉**

      * request：WEB项目中，Spring创建一个Bean的对象，将对象存入request域中
      * session：WEB项目中，Spring创建一个Bean的对象，将对象存入session域中
      * global session：WEB项目中，应用在Portlet环境，如果没有Portlet环境那么globalSession相当于session
  * Bean生命周期配置

    * init-method：初始化方法
    * destroy-method：销毁方法
      * 使用ClassPathXmlApplicationContext对象手动close
    * 简化：通过实现InitializingBean和DisposableBean接口并重写对应方法即可
  * Bean实例化方式

    * 无参构造方法实例化
      * \<bean id="唯一标识 " class="全类名">\</bean>
      * 标签中写property标签可以改变参数

    * 工厂静态方法实例化
      * \<bean id="唯一标识 " class="工厂类全类名" factory-method="方法名">\</bean>

    * 工厂实例方法实例化
      * 先配置工厂类以获得工厂类对象

      * \<bean id="唯一标识 " factory-bean="工厂类全类名" factory-method="方法名">\</bean>

    * 使用FactoryBean的方式实例化
      * 配置?FactoryBean类实现FactoryBean<?>接口并重写getObject(想要实例化的对象)和getObjectType(对象的class类型，返回.class文件就行)方法，如果想要多例的，重写最后一个方法即可
      * \<bean id="唯一标识" class="FactoryBean全类名">

## 注解开发

* ```
  ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class)
  ```

* 使用注解进行开发时，需要在applicationContext.xml中配置组件扫描，作用时指定哪个包及其子包下的Bean需要进行扫描以便识别使用注解配置的类、字段和方法

  ```xml
  <!--注解的组件扫描-->
  <context:component-scan base-package="包名"></context:component-scan>
  ```

* Spring原始注解

  * @Component：使用在类上用于实例化Bean

    ```java
    @Component("唯一标识")
    ```

  * @Controller：使用在web层类上用于实例化Bean

  * @Service：使用在Service层类上用于实例化Bean

  * @Respository：使用在dao层类上用于实例化Bean

  * @Autowired：使用在字段上用于根据类型依赖注入

    * 按照数据类型从Spring容器中进行匹配的

  * @Qualifier：结合@Autowired一起使用用于根据名称进行依赖注入

    * 按照id值从容器中进行匹配的，但是需要结合Autowired一起使用

    ```java
    @Autowired
    @Qualifier("唯一标识")
    ```

  * @Resource：相当于@Autowired + @Qualifier，按照名称进行注入

    ```java
    @Resource("name=唯一标识")
    ```

  * @Value：注入普通属性

    ```java
    @Value(value)
    @Value("${key}")
    ```

  * **@Scope：标注Bean对象的作用范围（单例多例等）***

  * @PostConstruct：使用在方法上标注该方法是Bean的初始化方法

  * @PreDestroy：使用在方法上标注该方法时Bean的销毁方法

* 新注解

  * @Configuration：用于指定当前类是一个Spring类，当创建容器时会从类上加载注解

  * @ComponentScan：用于指定Spring在初始化容器时要扫描的包

  * @Bean：使用在方法上，标注将该方法的返回值存储到Spring容器中

    * Spring会将当前方法的返回值以指定名称存储到Spring容器中

    ```java
    @Bean("唯一标识")
    ```

  * @PropertySource：用于加载.Properties文件中的配置

    * 再使用value注解将值取出以调用

    ```java
    ProperetySource("classpath:文件名")不支持'*'
    ```

  * @Import：用于导入其他配置类

    ```java
    @import({配置类1.class, 配置类2.class})
    ```

## Spring配置数据源

* 提高程序性能

* 数据源的开发步骤

  1. 导入数据源的坐标和数据库驱动坐标
  2. 创建数据源对象
  3. 设置数据源的基本连接数据
  4. 使用数据源获取连接资源和归还资源

  ```java
  public class JdbcConfig {
      @Value("${jdbc.driver}")
      private String driverClassName;
      @Value("${jdbc.url}")
      private String url;
      @Value("${jdbc.username}")
      private String username;
      @Value("${jdbc.password}")
      private String password;
  
      @Bean
      public DataSource dataSource(){
          DruidDataSource ds = new DruidDataSource();
          ds.setDriverClassName(driverClassName);
          ds.setUrl(url);
          ds.setUsername(username);
          ds.setPassword(password);
          return ds;
      }
  }
  ```

- Spring引入Properties文件：在配置类下使用`@PropertySource("classpath:jdbc.properties")`

## 整合Mybatis

```java
public class MybatisConfig {
   //创建数据库连接池工厂，以数据源为参数
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource ds){
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(ds);
        return ssfb;
    }
   //Mapper映射
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.yin.mapper");
        return msc;
    }
}
```

## AOP注解实现

* 步骤

  1. 导入AOP相关坐标（spring-context和aspectjweaver）
  2. 创建目标接口和目标类（内部有切点）
  3. 创建切面类（内部有增强方法）
  4. 将目标类和切面类的对象创建权交给spring
  5. 在切面类中使用注解配置织入关系

     * @Component：用在类上表示，用于被配置类扫出
     * @Pointcut：切点表达式抽取
     * @Aspect：用在类上，标注当前类是一个切面类
     * @Before：前置通知，参数为切点表达式
  6. 在配置类（SpringConfig）上加入@EnableAspectJAutoProxy告诉程序我们要用注解开发AOP

* 注解通知类型

  * @Before：前置通知
  * @AfterReturning：后置通知
  * @Around：环绕通知

  ```java
  @Around("pt()")
      public Object around(ProceedingJoinPoint pjp) throws Throwable {
         //获取执行的签名对象
          Signature signature = pjp.getSignature();
          String className = signature.getDeclaringTypeName();
          String methodName = signature.getName();
         
         //获取执行方法的参数
         Object[] args = pjp.getArgs();
         
          System.out.println("around before advice ...");
          //表示对原始操作的调用
          Object ret = pjp.proceed();
          System.out.println("around after advice ...");
          return ret;
      }
  ```

  * @AfterThrowing：异常抛出通知
  * @After：最终通知

* 切点表达式抽取

  * 在切面类内部中定义方法，在该方法上使用 @Ponintcut注解定义切点表达式，然后再增强注解中进行调用

    ```java
    @Pointcut("execution(* com.itheima.dao.BookDao.*d*(..))")通常描述到接口
    private void pt(){}
    @Around("pt()")
    public * method(){想要切入的代码}
    ```

  - 切面表达式：execution(~~权限修饰符~~ 返回值类型 包名.类名.方法名(参数) ~~异常名~~)

    一个"*"代表一个任意东西（单个独立的任意符号），".."代表多个任意（比如任意个参数），"+"自行了解

## Spring事务管理

- 保证一系列数据库操作同时成功同时失败

- Spring为我们提供了事务管理器接口PlatformTransactionManager和实现类DataSourceTransactionManager

- 开发步骤：

  - 在jdbc配置类里添加PlatformTransactionManager的Bean
  - 创建实现类DataSourceTransactionManager并将其返回
  - 将数据源注入实现类
  - 在Spring配置类中加@EnableTransactionManagement开启Spring事务
  - 在业务层接口类上或者方法上加@Transactional开启事务

  ```java
  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource){
      DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
      transactionManager.setDataSource(dataSource);
      return transactionManager;
  }
  ```

- 只有sqlSession对象和PlatformTransactionManager对象用的同一个数据源才能被Spring管理事务（通过相同数据源管理）

- @Transactional属性（参数）：

  - readOnly：设置是否为只读

  * timerout：设置事务超时时间，timeout = -1永不超时

  * rollbackFor（==重要==）：设置事务回滚异常（class），通常只有ERROR和运行期异常会造成Spring事务的回滚，将想要回滚的异常的class文件传进去，Spring事务管理就会对对应的异常发生时控制回滚

  * propagation：事务传播行为，默认Propagation.REQUIRED，会自动加入事务，改为Propagation.REQUIRES_NEW开启新事务

# SpringMVC注解开发

## SpringMVC开发步骤

1. 导入SpringMVC和Servlet坐标
2. 创建SpringMVC控制类（等同于servlet），开发表现层的Bean用@Controller注解
3. 创建方法执行servlet操作，用==@RequestMapping("路径")==配置访问路径（==@ResponseBody==设置当前控制器响应内容为当前返回值）

==@RequestMapping可以写在类上，也可以写在操作方法上，写在类上统一设置控制器方法的访问路径前缀==

1. 初始化SpringMVC环境（同Spring配置类）加载对应Bean

2. 定义一个servlet启动的配置类，加载Spring的配置，让Tomcat启动的时候加载SpringMVC的配置

   1. 定义ServletContainersInitConfig配置类继承AbstractDispatcherServletInitializer
   2. 重写三个方法：
      - createServletApplicationContext()：加载SpringMVC容器配置
      - getServletMappings()设置哪些请求归属SpringMVC处理
      - createRootApplicationContext()：加载spring容器配置

   ```java
   public class ServletContainersInitConfig extends AbstractDispatcherServletInitializer {
       @Override
       protected WebApplicationContext createServletApplicationContext() {
           AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
           ctx.register(SpringMVCConfig.class);
           return ctx;
       }
       @Override
       protected String[] getServletMappings() {
           return new String[]{"/"};//所有请求归Spring处理
       }
       @Override
       protected WebApplicationContext createRootApplicationContext() {
           return null;
       }
   }
   ```

3. 为了在ssm整合的时候避免Spring的配置类加载了SpringMVC的bean，我们可以在Spring的扫包注解下过滤相关Bean，最好使用精准匹配

## Bean加载控制

简化操作：

```java
public class ServletContainersInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer{
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringMVCConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
}
```

使用这个实现类就只需要传递配置类的class文件

## 过滤器（乱码问题）

- 在Post请求中会出现的中文乱码问题可以通过过滤器解决

  - 在servlet启动的配置类中重写getServletFilters()方法
  - SpringMVC提供了专用的字符过滤器CharacterEncodingFilter，直接创建使用并返回
  - String setEncoding();设置字符集

  ```
  @Override
  protected Filter[] getServletFilters() {
      CharacterEncodingFilter Filter = new CharacterEncodingFilter();
      Filter.setEncoding("UTF-8");
      return new Filter[]{Filter};
  }
  ```

## 参数传递

- 普通的参数
  - 直接在方法里头用形参接收，保证请求参数名和形参名一致就可以接受
  - 如果不一致，@RequestParam ("请求参数名")写在形参的前面能够绑定请求参数
- POJO类
  - 也直接在方法里头用形参接收，SpringMVC会自动帮你装配
  - 嵌套的POJO类参数也能自动装配，但是请求参数命名要按格式比如"address.province"="beijing"
- 数组参数：与数组相同名称的请求参数都会被接收进数组
- 集合参数：直接用形参List接收的话SpringMVC会把请求参数当作集合类参数传递，即把List当POJO类处理

所以需要在形参List<>前加入@RequestParam告诉SpringMVC就行

- json

  - 先导入json坐标
  - 在SpringMVC配置类中加入@EnableWebMvc开启json数据转化对象功能
  - 用形参接受时在形参前加@RequestBody，因为json数据在请求体里
  - 注解分析
    - @RequestBody在每个方法中只能使用一次
    - @RequestParam主要用于url地址传参，@RequestBody主要用于json格式的接收==（后期主要）==

- 日期参数传递

  - 根据不同日期格式设置不同的接收方式
  - 格式1："yyyy/MM/dd"可以直接用形参Date接收（自动装配）
  - 其他格式：在形参Date前加入@DateTimeFormat(pattern = " ")指定日期格式

  ```
  @RequestMapping("/save")
      @ResponseBody
      public String dataParam(Date date,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                              @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date2){
          return "{ '' = '' }";
      }
  ```

- 类型转换器：Converter<S, T>接口能够帮我们转换很多类型，比如请求参数年龄(String ->Integer )和日期，但是有些类型转换需要用@EnableWebMvc这个注解开启功能

## 响应

- 直接return一个字符串表示跳转页面
- ==@ResponseBody==设置当前控制器返回值为响应体
  - 方法返回值为字符串，响应就是文本数据
  - 方法返回值为对象，则自动解析成json格式的字符串响应
  - 方法返回值为集合，也是自动解析成json格式的字符串
- Spring专门提供的HttpMessageConverter接口帮我们把对象转换成json格式的字符串

## REST风格

- REST风格是一种对资源的访问形式，根据这种风格对资源进行访问成为RESTful

- 它能隐藏资源的访问行为，和简化访问路径的书写

- 通过对同一路径执行不同的请求方式会对资源执行不同的操作，这就是REST风格

- 入门：

  - 在@RequestMapping()中，除了value参数指定路径外，还有method参数指定请求方式

  ```java
  @RequestMapping(value = "/save",method = RequestMethod.GET)
  ```

  - 请求参数（路径参数）：在路径后用大括号表示请求参数，在方法所需的参数前加@PathVariable表示参数在路径中

```
   @RequestMapping(value = "/save/{id}",method = RequestMethod.DELETE)
   public String delete(@PathVariable Integer id)
```

后期参数传递如果只有一个就用@PathVariable，多个参数就用RequestBody（json）

- 简化：

  - @RestController：写在类上，代替@Controller并且给每个类中的控制方法默认一个@ResponseBody
  - @RequestMapping("url")：写在类上，代替一些不用参数的方法写value = "url"的参数
  - @PostMapping、@DeleteMapping、@GetMapping、@PutMapping：写在方法上代替method参数

  ```
  @RestController
  @RequestMapping("/rest")
  public class Rest {
      @GetMapping("/{id}")
      public String get(@PathVariable Integer id){
          return null;
      }
      @PostMapping
      public String post(@RequestBody Date date){
          return null;
      }
      @PutMapping
      public String put(@RequestBody Date date){
          return null;
      }
      @DeleteMapping("/{id}")
      public String delete(@PathVariable Integer id){
          return null;
      }
  }
  ```

## 静态资源的处理

- 在servlet容器配置类中，将所有的请求都归SpringMVC管，所以一些静态方法的访问就被SpringMVC拦截了

- 为了让客户端访问服务器时能正常加载出静态资源，我们需要识别客户端访问的路径让SpringMVC放行

- 方法：

  - 创建一个配置类继承WebMvcConfigurationSupport
  - 重写addResourceHandlers(ResourceHandlerRegistry registry)方法
  - 在SpringMVC配置类中扫描出该配置类

  ```java
  @Configuration
  public class SpringMvcSupport extends WebMvcConfigurationSupport {
      @Override
      protected void addResourceHandlers(ResourceHandlerRegistry registry) {
          //当访问/js/??的时候，提示SpringMVC走/js目录下的内容，而不是找xiang'g
          registry.addResourceHandler("/js/**").addResourceLocations("/js/");
      }
  }
  ```

## 拦截器（权限访问）

- 在控制器方法前做一些增强，是一种动态拦截方法调用的机制，只能在SpringMVC中拦截，底层是AOP

- 与Filter不同的是Filter属于Servlet技术，能对所有访问做增强，而Interceptor属于SpringMVC技术，只针对SpringMVC做增强

- 开发步骤：

  - 创建ProjectInterceptor类实现HandlerInterceptor接口，加上@Component并确认能被SpringMVC扫描到
  - 重写三个方法

  - 在SpringMvcSupport配置类里头，自动注入成员变量拦截器，重写addInterceptors方法将拦截器添加

```
@Override
protected void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(projectInterceptor).addPathPatterns("/books","/books/*");
}
```

拦截器链：当一个拦截器运行中断，前面拦截器依然运行afterCompletion操作

# Mybatis

==默认事务不提交==

## MyBatis开发步骤

### 初步配置

1. 添加MyBatis坐标(jdbc和mybatis)
2. 创建数据表

3. 创建实体类==（POJO）==

4. 编写映射文件UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mapper.StudentMapper">
     <!-- resultType是结果类型，parameterType是参数类型-->
    <select id="selectAll" resultType="com.example.pojo.Student">
        select * from student8
    </select>
    <insert id="add" parameterType="com.example.pojo.Student">
        insert into student8 values(#{sid},#{sname},#{score})
    </insert>
    <delete id="delete" parameterType="Integer">
        delete from student8 where sid = #{sid}
    </delete>
    <update id="update" parameterType="com.example.pojo.Student">
        update student8 set sname=#{sname},score=#{score} where sid=#{sid}
    </update>
</mapper>
```

5. 编写核心配置文件mybatis-config.xml

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development"> 指定默认环境
        <environment id="development"> 指定当前环境名称
            <transactionManager type="JDBC"/>指定事务管理类型是JDBC/MANAGED
            <dataSource type="POOLED">指定当前数据源类型是连接池 POOLED/UNPOOLED/JNDI
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/dp?"/>
                <property name="username" value="root"/>
                <property name="password" value="155357ytr"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="mapper/UserMapper.xml"/> 引入映射文件
    </mappers>
</configuration>
```

### mapper

- 创建Mapper包编写Mapper接口

  `User select(@Param("username") String username, @Param("password") String password);`

==注意==：在resource目录下创建新目录用 ' / ' 隔开能有效地将Mapper接口和映射文件在Maven编译后放在同一包下

- 如果你的Mapper.xml文件放到了非resourse目录下，会出现`xxxMapper is not known to the MapperRegistry.`，配置maven

```
<!--在build中配置resources，来防止我们资源导出失败的问题-->
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>

```

* 接口规范：
  * Mapper.xml文件中的namespace与mapper**接口的全限定名**相同
  * Mapper接口方法名和Mapper.xml中定义的每个statement的id相同
  * Mapper接口方法的输入参数类型和Mapper.xml中定义的每个sql的parameterType的类型相同
  * Mapper接口方法的输出参数类型和Mapper.xml中定义的每个sql的resultType的类型相同

### util

编写工具类util方便创建数据库连接工厂

```java
public class SqlSessionFactoryUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);//加载核心配置文件
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);//获得SqlSession工厂对象
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;//返回工厂对象
    }
}
```

### service

编写接口和实现类用于数据库的一系列真正操作

```java
public class UserServiceImpl implements UserService {
   SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();//获得SqlSession工厂对象
    @Override
    public List<User> selectAll(){
        SqlSession sqlSession = factory.openSession(); //从工厂获得SqlSession对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class); //传入映射文件
        List<User> users = mapper.selectAll();//执行sql语句
        sqlSession.close();//提交事务
        return users;
    }
```

## 动态sql

- <if>:用于判断条件是否成立。使用test属性进行条件判断，如果条件为true，则拼接SQL。

- <where>: where元素只会在子元素有内容的情况下才插入where子句。而且会自动去除子句的开头的AND或OR。

- <set>: 动态地在行首插入SET 关键字，并会删掉额外的逗号。(用在update语句中)

- <for each>：collection=集合名称，item=遍历出来的元素，separator=分隔符，open=起始符，close=结束符

  ```xml
  <delete id="deleteByIds">
          delete from emp where id in
          <foreach collection="ids" item="id" separator="," open="(" close=")">
              #{id}
          </foreach>
   </delete>
  ```

- <sql>：定义可重用的sql语句，定义id

- <include>：通过属性refid，引入这个id包含的sql片段

# SSM整合

## 整合配置

1. 导入坐标和properties文件

- Spring：spring-webmvc（包含spring-context）、spring-jdbc、spring-test（可选）
- Mybatis：mysql-connector-java、druid、mybatis、mybatis-spring、jdbc.properties
- SpringMVC：javax.servlet-api（provided）、jackson-databind、junit（可选）

2. config：编写配置类

- SpringConfig：@Configuration、@ComponentScan("业务层")、@PropertySource、@Import、@EnableTransactionManagement（开启事务管理）
- JdbcConfig：@Value("${jdbc.driver}")、DataSource、PlatformTransactionManager（事务管理器）
- Mybatis：SqlSessionFactoryBean、MapperScannerConfigurer（加载映射文件）
- ServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer：重写三个方法加载配置
- SpringMvcConfig：@Configuration、@ComponentScan("表现层")、@EnableWebMvc
- SpringMvcSupport：@Configuration、重写addResourceHandlers方法，将静态资源放行

# Maven高级

## **分模块开发**

意义：将原始模块按功能拆分成若干个子模块，方便模块之间的相互调用，接口共享，每一层的功能都能复用，方便给别人使用和维护

步骤：

- 创建Maven模块，每个模块做对应的功能
- 书写Maven模块代码，各层架构
- 通过Maven指令安装进本地仓库（install）
- 每个模块需要其他模块时，在pom文件导包即可

## 依赖传递与依赖冲突

**依赖传递**

- 直接依赖：在当前项目中通过依赖配置建立的关系
- 间接依赖：被依赖的资源如果依赖其他资源，则当前项目间接依赖其他资源，并且不用重复导包

**依赖冲突**

- 路径优先:当依赖中出现相同的资源时，层级越深，优先级越低，层级越浅，优先级越高
- 声明优先:当资源在相同层级被依赖时，配置顺序靠前的覆盖配置顺序靠后的
- 特殊优先:当同级配置了相同资源的不同版本，后配置的覆盖先配置的

**可选依赖**：可选依赖是隐藏当前项目所依赖的资源，隐藏后对应资源将不具有依赖传递性(<optional>true</optional>)

**排除依赖**：隐藏当前资源对应的依赖关系，（用别人的资源时不想要别人的间接依赖资源）

```
<exclusions>
    <exclusion>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
    </exclusion>
</exclusions>
```

## 聚合与继承

**聚合**

- 聚合：将多个模块组织成一个整体，同时进行项目构建的过程就叫聚合
- 聚合工程：通常是一个不具有功能的空工程（有且仅有一个pom文件），能将多个工程编组，实施同步构建，当工程中的一个模块发生更新时，其他关联工程会同步更新同步构建
- 打包方式：<packaging>pom</packaging> ；web打包方式为war，正常的是jar
- 将要管理的模块写进pom文件

```
<modules>
    <module>../SSM_2</module>//顺序随意
</modules>
```

**继承**

- 概念：跟java中的继承相似，子工程可以继承父工程的配置信息（多用于依赖关系），通常与聚合一起用
- 作用：简化配置，减少版本冲突

```xml
<parent>
    <groupId>com.yin</groupId>
    <artifactId>Maven_1</artifactId>
    <version>1.0-SNAPSHOT</version>
    <relativePath>../Maven_1/pom.xml</relativePath>
</parent>
```

<dependencyManagement>

定义依赖管理，在这个区域里头写的依赖为可选择继承的依赖，子工程引入此类依赖时不需要指定版本，由父工程统一指定

</dependencyManagement>

## **属性**

```
<properties>//定义自定义属性
    <spring.version>5.3.24</spring.version>
</properties>
```

`${spring.version}`引用属性

由于自定义属性只能在pom文件中使用，为了广大maven的控制范围，让配置文件能加载到pom中的自定义属性，需要开启资源目录加载属性的过滤器，让指定目录中的文件能通过maven解析${ }里的值

```
<build>
    <resources>
        <resource>
            <directory>${project.basedir}/src/main/resources</directory>//${project.basedir}当前项目所在目录
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```

## **版本管理**

- 工程版本：
  - SNAPSHOT（快照版本)
    - 项目开发过程中临时输出的版本，称为快照版本
    - 快照版本会随着开发的进展不断更新
  - RELEASE(发布版本)
    - 项目开发到进入阶段里程碑后，向团队外部发布较为稳定的版本，这种版本所对应的构件文件是稳定的，即便进行功能的后续开发，也不会改变当前发布版本内容，这种版本称为发布版本

- 发布版本
  - alpha版：经过alpha测试
  - beta版：经过beta测试
  - 纯数字版

## **多环境开发**

```xml
<!--定义多环境-->
<profiles>
<!--定义具体环境-->
    <profile>
        <!--环境唯一id-->
        <id>env_dep</id>
        <!--环境专用的属性值-->
        <properties>
            <jdbc.url>jdbc:mysql//localhost:3306/dp</jdbc.url>
        </properties>
        <!--设置默认环境启动-->
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
</profiles>
```

使用多环境构建（maven指令指定环境构建）：mvn 指令 -P 环境id

## **跳过测试**（了解）

指令：mvn 指令 -D skipTests

细粒度控制跳过测试：

```
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.12.4/version>
    <configuration>
        <skipTests>false</skipTests>//是否自动跳过测试
        <includes ><include>**/User*Test.java</include></includes>//需要测试
        <excludes><exclude>**/User*Test.java</exclude></excludes>//不需要
    </configuration>
</plugin>
```

# SpringBoot

## 快速入门

- 快速入门
  - 创建新模块，选择spring初始化，配置相关信息，选择web（SpringWeb）
  - ==SpringBoot创建项目时采用jar打包方式==
  - 直接开发控制器类Controller
  - 运行自动生成的SpringBootApplication类

- 使用SpringBoot需要联网，因为idea创建SpringBoot的底层也是上网下载，可以直接官网配置SpringBoot
- 快速启动
  - 对SpringBoot程序打包（package）
  - 将target目录下的jar包发送给前端人员
  - 在jar包的目录下执行启动命令(cmd)：java -jar springboot.jar(jar包名字)（需要pom文件中有maven插件）

**起步依赖**：

- starter：SpringBoot常见的项目名称，定义了当前项目使用的所有项目坐标，以达到减少版本冲突的目的
- parent：所有SpringBoot项目都要继承的项目，定义了很多坐标版本号（依赖管理），以达到减少依赖冲突的目的

**更改服务器**：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
   <!--排除tomcat依赖-->
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!--添加Jetty起步依赖，版本由SpringBoot的starter控制-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

## 配置文件

- SpringBoot提供了多种属性配置的方式

  - application.properties	第一生效  `server.port=80`

  - application.yml	第二生效也是主要配置文件

  - application.yaml	第三生效

  - ```
    server:
      port: 80
    ```

- 配置文件分类：

​    SpringBoot中4级配置文件：

​        1级： file ：config/application.yml  ——最高级

​        2级： file ：application.yml

​        3级：classpath：config/application.yml

​        4级：classpath：application.yml  ——最低级

​    作用：

​        1级与2级留做系统打包后设置通用属性

​        3级与4级用于系统开发阶段设置通用属性

## yaml

- 语法规则：
  - 大小写敏感
  - 属性层级关系使用多行描述
  - 使用缩进表示层级关系，同层级的属性左对齐，只允许使用空格，不允许tab键
  - 属性值前加空格（属性名和属性值用冒号+空格分隔）
  - #表注释
  - 数组格式的数据用减号加空格加数据值，每行一个数据

```
enterprise:
  name: yin
  age: 20
  likes:
    - game
    - Java
```

- 数据读取：

  - 单个数据：`@Value("${enterprise.likes[0]}")`

  - 全部封装读取

    ```java
    @Autowired
    private Environment env;
    String id = env.getProperty("enterprise.id");
    ```

  - 封装成自定义对象

    ```java
    @Component
    @Data //成员变量必须有set方法
    @ConfigurationProperties(prefix = "enterprise")
    public class enterprise {
        private String name;
        private Integer age;
        private String[] likes;
    }
    ```
    
    - ==警告：SpringBoot Configuration Annotation Processor not configured==的解决方案：
    
    - 在pom文件中导入
    
    - ```xml
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-configuration-processor</artifactId>
          <optional>true</optional>
      </dependency>
      ```

## 多环境开发

```yml
spring:
  profiles:
    active: dev
---
spring:
  profiles: dev
server:
  port: 80
---
spring:
  config:
    activate:
      on-profile: pro
server:
  port: 8080
```

- 带参数启动boot工程：`java -jar springboot.jar --spring.profiles.active=test`

- 打包之前执行clean防止上一次的结果影响下一次的操作

- 配置文件中写中文打包失败解决方案
  - idea打开settings
  - 搜索File Encodings
  - 将各种编码方式改成utf-8

- Maven与boot多环境兼容问题：

  - maven为主，boot为辅
  - 在maven中多环境写自定义属性，在boot中用占位符读取
  - boot配置文件无法解析占位符需要添加maven插件

  ```xml
  <plugin>            
      <artifactId>maven-resources-plugin</artifactId>
      <configuration>                
          <encoding>utf-8</encoding>
          <useDefaultDelimiters>true</useDefaultDelimiters>
      </configuration>        
  </plugin>
  ```

## 整合

- 创建boot工程时选择mybatis、mysql（范围是runtime但是wei'shen'm）、web的技术集（依赖）

- 用yml配置数据源信息

  - 如果需要druid数据源则需要在pom文件中导入依赖
  - SpringBoot版本低于2.4.3，mysql驱动版本大于8.0时，需要在url连接串中配置数据

  ```yml
  spring:
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/User?serverTimezone=UTC
      username: root
      password:
      type: com.alibaba.druid.pool.DruidDataSource
  ```

- 定义数据层接口与映射配置
  - 在Dao层接口上加上@Mapper注解
- 静态页面放进resources里面的static包下

## 过滤器

- 在启动类上加@ServletComponentScan注解开启对过滤器的扫描

- 创建自定义过滤器对象实现Filter

- 重写doFilter方法

- 路径匹配器:

  ```java
  public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
  boolean b = PATH_MATCHER.match(url,requesturl)
  ```

## 拦截器

```
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    CORSInterceptor corsInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor);
    }
}
```

## 定时任务

- 计时器StopWatch

  - stopWatch是org.springframework.util包下的一个工具类，使用它可直观的输出代码执行耗时，以及执行时间百分比

  - ```
    // 构造方法的参数表示的是StopWatch实例的id，标志着实例的身份，可以为空
    StopWatch watch = new StopWatch("StopWatch-Learning");
    //开始任务，任务名也可以为空
    watch.start("test1");
    //任务是否正在运行
    boolean running = watch.isRunning();
    // 任务停止
    watch.stop();
    // 获取任务的名字
    String lastTaskName = watch.getLastTaskName();
    long lastTaskTimeMillis = watch.getLastTaskTimeMillis(); // 任务运行时间 微妙
    long totalTimeNanos = watch.getTotalTimeMillis(); //所有任务运行时间的总和
    ```

- SpringTask（Schedule定时框架）

  - 启动配置类中添加注解`@EnableScheduling`
  - 根据需要选择@Schedule (fixedRate、fixedDelay、cron表达式)三种任务调度器
    - `fixedRate=3000`：固定频率任务，每隔3秒执行一次，如果设置了并发执行任务，任务执行时间就不会影响频率但可能重复
    - `@Async`：并发执行
    - `fixedDelay= 3000`：上次任务结束后再计时3秒后再次开启新的一次调用
    -  `@Scheduled(cron = "0/3 * * * * *")`是根据cron表达式执行定时任务的。
    - 在线corn表达式生成器，可以图形化生成corn表达式：https://cron.qqe2.com/

# MybatisPlus

## 开发步骤

- 创建SpringBoot项目，添加mysql技术集
- 在pom文件中导入mybatis-plus-boot-starter和druid数据源依赖，==baomidou==
- 在配置文件中书写jdbc参数
- 创表和实体类
- 定义数据（dao层）接口，继承BaseMapper<实体类>，==记得加@Mapper注释==
- 直接在测试类中注入dao接口使用
- ==实体类简化开发==
  - pom文件中注入lombok依赖，==scope范围选择provided==（也许是个代码生成的包，只需要在编译期起作用就行）
  - 在实体类上使用注解开发
    - @Setter
    - @Getter
    - @toString
    - @NoArgsConstructor
    - @AllArgsConstructor
    - @EqualsAndHashCode
    - ==@Data==：除了构造方法之外其他的全加上
- ==业务层快速开发==：
  - 使用mybatisplus提供的业务层通用接口IService<T>和通用实现类ServiceImpl<M(数据层接口),T>
  - 记得实现类不仅要继承通用实现类还要实现接口
  - 在功能追加时不要不小心重写了原始方法

- 开启日志：

```yml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## 特性

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询

## 查询

- **分页查询**

  - 设置分页查询器作为Spring管理的Bean

  ```java
  @Configuration
  public class MpConfig {
      @Bean
      public MybatisPlusInterceptor pageInterceptor(){
          MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
          interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
          return interceptor;
      }
  }
  ```

  - 创建IPage对象，本身是接口，实现类为Page(int 第几页,int 多少条数据)
  - 用dao层接口调用selectPage（service层是page方法），将page对象传进去
  - IPage类的方法：
    - getCurrent（）返回当前页码
    - getSize（）返回每页数据总量
    - getPages（）返回总页数
    - getTotal（）数据总量
    - getRecords（）当前页数据
  
- **条件查询**

  - 条件设置

    * 方式一

    ```java
    QueryWrapper qw = new QueryWrapper();
    qw.lt("age", 18); // age < 18;
    qw.gt("grade", 80); // grade > 80;
    ```

    * 方式二

    ```java
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.lambda().lt(User::getAge, 18); //User中的age属性 < 18 防止字段名写错
    ```

    * **方式三**

    ```java
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.lt(User::getAge, 18).gt(User::getAge, 10); //User中的age属性 < 18 && > 10
    lqw.lt(User::getAge, 10).or().gt(User::getAge, 18); //User中的age属性 < 10 || > 18
    ```

  - null值处理

    * 创建实体类的查询类继承该实体类，设置新的属性，该属性为实体类中可能出现上下限的属性
    * 在连接条件前加入condition参数，当condition为true时才连接条件，否则不连接

- **查询投影**

  * 设置select的字段

    * lambda写法

    ```java
    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
    lqw.select(User::getId, User:: getName); // 相当于select id, name from table
    ```

    * 常规写法

    ```java
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.select("id", "name");
    ```

  * 聚合函数

    ```java
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.select("count(*) as count");
    List<Map<String, Object>> userList = userDao.selectMaps(qw);
    //分组统计
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.select("count(*) as count, class");
    qw.groupBy("class");
    List<Map<String, Object>> userList = userDao.selectMaps(qw);
    ```

- **查询条件设定**

  * 等于

  ```java
  LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
  lqw.eq(User::getName, "123456") //name="123456"
  User user = userDao.selectOne(lqw);
  ```

  * lt gt < >  le ge <= >=  eq =
  * between 范围 前（下限） 后（上限）
  * 模糊匹配

  ```java
  lqw.like(StringUtils.isEmpty(name),String.User::getName, name); // name
  lqw.likeRight(User::getName, "J") // J% 首字母为J
  lqw.likeLeft(User::getName, "J") // %J 以J结尾
  ```

- 排序查询

  * .orderByDesc
  * .orderByAsc

- 字段映射和表名映射

  * @TableField
    * value：关联字段与属性
    * exist：该属性在表中是否存在
    * select：true/false 该字段是否参与查询
  * @TableName：关联表名

## DML控制

**id生成策略**

* @TableId(type = IdType.AUTO)

  * value：关联字段名
  * type：自增策略
    * AUTO：自增长
    * NONE
    * INPUT：输入
    * ASSIGN_ID（默认）：通过雪花算法自动生成的id（可兼容数值型与字符串型）
    * ASSIGN_UUID：通过UUID生成算法自动生成的id

* SpringBoot全局设置

  ```yaml
  mybatis-plus:
   global-config:
    db-config:
     id-type: assign_id
     table-prefix: tbl # 所有表都有该前缀
  ```

**多数据操作**

* 直接删除或查询

  * delete/selectBatchIds(Collection collection);

* Batch就是批量操作的意思，比如`userService.saveBatch(userList,100)`表示按照100个为一批来插入数据

* 逻辑删除

  * 为数据设置是否可用状态字段，删除时设置状态字段为不可用状态，数据仍保留在数据库中

  * @TableLogic

    * 逻辑删除字段
    * value：未删除的值
    * delval：删除后的值

  * 随后删除操作将变为逻辑删除操作，对原本的查询没有影响

  * 全局配置逻辑删除

    ```yaml
    mybatis-plus:
     global-config:
      db-config:
       logic-delete-field: deleted
       logic-not-delete-value: 0
       logic-delete-value: 1 # 将deleted作为逻辑删除字段，0为未删除值，1为已删除值
    ```

- update
  - xxxService.update().setSql("stock = stock -1").update()

## 乐观锁

* 增加字段Version
* @Version 关联属性与字段
* 添加乐观锁拦截器：OptimysticLokerInnerInterceptor
* 先查询出数据，再修改。保证version对应上
* 当操作时没有version属性时，将不会触发锁机制

## 驼峰映射

将数据库字段里头的下划线变成驼峰命名

```yml
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
```

==注意开启后实体类的成员变量凡是非首字母带大写的都会被驼峰映射成下划线==

## 公共字段填充

在新增员工时需要设置创建时间、创建人、修改时间、修改人等字段，在编辑员工时需要设置修改时间和修改人等字段。这些字段属于公共字段，也就是很多表中都有这些字段。我们可以对公共字段在某地方统一处理来简化开发：

- 在实体类需要自动填充的属性上加上`@TableField(fill = FieldFill.INSERT_UPDATE)`注解，更新和插入自动填充
- 编写元数据对象处理器

```java
@Component
public class MyMataObjectHandler implements MetaObjectHandler {
    @Override//插入操作时自动填充
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
    }
    @Override//更新操作时自动填充
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
```

# 实际开发

## 杂记

- lombok：@Slf4j，加上这个注释之后可以直接用log.info在控制台输出日志

- 如果使用了雪花算法，即用long型数据存储id，会导致js处理的精度不够java中long型数据，所以在传json数据时将long型数据转换成字符串，这需要自定义对象转换器

- 在Spring Boot中，可以通过使用注解`@JsonProperty`来解决实体类中变量名与前端传送的JSON数据键不一致的问题。
  - 在使用`@JsonProperty`注解时，需要确保你的Spring Boot应用程序中包含`jackson-databind`库的依赖

- Json字符串数组与数组和Set的转换
  
  ```java
     String str = "[\"张三\",\"李四\",\"王五\"]";
     //json字符串数组转数组
     Gson gson = new Gson();
     String[] array = gson.fromJson(str, String[].class);
     //json字符串数组转Set
     Set<String> list = new Gson().fromJson(str, new TypeToken<Set<String>>(){}.getType());
  ```
  
- sql语句中的`where id in (...)`中，是不会根据括号内数据的顺序查询

  - `qw.eq("id",ids)`想要顺序的话，可以将返回的List数据按照id转换成Map，在顺序按id取出
  - `.collect(Collectors.groupingBy(User::getId))`
  - `listByIds(ids)`在传入参数为0或者空也会报错，sql语句会变成`where id in ()`
  
- Mysql优化之分表

- 在maven项目中读取文件通常使用类加载器能直接读取`/src/main/resource`目录下的资源文件

  - `本类.class.getClassLoader().getResourceAsStream("文件名")`，返回值是一个`InputStream`对象



## MD5加密

* 导入commons-codec
* 加密
  * DigestUtils.md5DigestAsHex(Object.getBytes());

## 文件上传下载

- 文件上传

  - 文件只能用post请求上传

  - SpringBoot帮我们封装好了文件类MultipartFile file

  - 在controller类中，参数直接用MultipartFile file接收文件，==参数命名为file不能随便写==，原理是elementui设置的name=file

  - 在yml配置文件中写好文件上传的路径并用变量basePath读取`@Value("${head.img}")`

    ```java
    String originalFilename = file.getOriginalFilename();//原始文件名
    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//截取文件后缀
    String fileName = UUID.randomUUID().toString()+suffix;//UUID随机生成文件名防重复
    try {
        file.transferTo(new File(basePath+fileName));//另存为
    } catch (IOException e) {
        e.printStackTrace();
    }
    ```


- 文件下载

  ```java
  try {
      FileInputStream fileInputStream = new FileInputStream(new File(fileName));//读取本地文件
      ServletOutputStream outputStream = response.getOutputStream();//响应的输出流
      response.setContentType("image/jpeg");//设置响应的内容类型为 "image/jpeg"
      int len = 0;
      byte[] bytes = new byte[1024];
      while((len = fileInputStream.read(bytes))!=-1){//循环读取
          outputStream.write(bytes,0,len);//写入
          outputStream.flush();//在每次写入后，通过 flush() 刷新输出流
      }
      //关闭资源
      outputStream.close();
      fileInputStream.close();
  } catch (Exception e) {
      e.printStackTrace();
  }
  ```

- 设置文件上传允许的最大大小

```yml
spring:
  servlet:
    multipart:
      max-file-size: 10MB #单个文件最大大小默认为1MB
      max-request-size: 100MB #单次请求的最大大小默认为100MB
```

## 通用反馈对象

```java
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private Object data;
    private int code;
    private String message;

    public static Result success(Object data, String message) {
        return new Result(data, 200, message);
    }

    public static Result success(Object data) {
        return new Result(data, 200, "");
    }
    public static Result success(String message) {
        return new Result(null, 200, message);
    }

    public static Result fail(int code, String message) {
        return new Result(null, code, message);
    }

    public static Result fail(String message) {
        return new Result(null, 500, message);
    }
}

```

## 全局异常处理

- 枚举错误码
  ```java
  public enum MyErrorCode {
  
      SUCCESS(0, "ok"),
      PARAMS_ERROR(40000, "请求参数错误"),
      NULL_ERROR(40001, "请求数据为空"),
      NOT_LOGIN(40100, "未登录"),
      NO_AUTH(40101, "无权限"),
      FORBIDDEN(40301, "禁止操作"),
      SYSTEM_ERROR(50000, "系统内部异常");
  
      private final int code;
      private final String message;
  
      MyErrorCode(int code, String message) {
          this.code = code;
          this.message = message;
      }
  
      public int getCode() {
          return code;
      }
  
      public String getMessage() {
          return message;
      }
  
  }
  
  ```

- 自定义异常
  ```java
  @Getter
  public class CommonException extends RuntimeException {
      private final Integer code;
  
      public CommonException(Integer code, String message) {
          super(message);
          this.code = code;
      }
      public CommonException(MyErrorCode errorCode) {
          super(errorCode.getMessage());
          this.code = errorCode.getCode();
      }
  }
  ```
  
- 全局异常处理器
  ```java
  @RestControllerAdvice
  public class GlobalExceptionHandler {
      @ExceptionHandler(CommonException.class)
      public Result<Void> CommonException(CommonException ex){
          return Result.fail(ex.getCode(),ex.getMessage());
      }
      @ExceptionHandler(RuntimeException.class)
      public Result<Void> RuntimeException(RuntimeException ex){
          return Result.fail(MyErrorCode.SYSTEM_ERROR.getCode(),ex.getMessage());
      }
  }
  ```

## 跨域请求

- 之所以会产生跨域问题是由于浏览器实现了同源策略（Same origin policy），同源策略规定发起[ajax](https://so.csdn.net/so/search?q=ajax&spm=1001.2101.3001.7020)请求时当原地址（原始域）和请求地址（请求域）的协议、域名、端口号三者任意一个不同就会引起跨域问题。

- 解决方法：

  - 拦截器：

  ```
  @Component
  public class CORSInterceptor implements HandlerInterceptor {
      @Override
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
          String origin = request.getHeader("Origin");
          response.setHeader("Access-Control-Allow-Origin", origin);
          response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
          response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,Access-Token,token");
          response.setHeader("Access-Control-Expose-Headers", "*");//响应客户端的头部 允许携带Token 等等
          response.setHeader("Access-Control-Allow-Credentials", "true");  // 允许携带cookie
          response.setHeader("Access-Control-Max-Age", "3600");   // 预检请求的结果缓存时间
          return true;
      }
  }
  ```
  
  - ==记得在配置类中添加拦截器==
  - 注解：在我们需要的controller上加@CrossOrigin`@CrossOrigin(origins = "*",maxAge = 3600)`
    - origin="\*"代表所有域名都可访问
    - maxAge简单来说就是Cookie的有效期 单位为秒若maxAge是负数,则代表为临时Cookie,不会被持久化,Cookie信息保存在浏览器内存中,浏览器关闭Cookie就消失
  - 可以加在Controller公共父类（PublicUtilController）中，所有Controller继承

## ThreadLocal

- 客户端发送的每次http请求，对应的在服务端都会分配一个新的线程来处理，在处理过程中涉及到的类中的方法都属于相同的一个线程，由此我们可以通过线程来获取和传递数据

  - localThread是Thread的一个局部变量，ThreadLocal为每个线程提供单独一份存储空间，具有线程隔离的效果，只有在线程内才能获取到对应的值，线程外则不能访问。

    - 编写BaseContext工具类，基于ThreadLocal封装的工具类

    ```java
    public class BaseContext {
        private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
        public static void setCurrentId(Long id){
            threadLocal.set(id);
        }
        public static Long getCurrentId(Long id){
            return threadLocal.get();
        }
    }
    ```

    - 在LogincheckFilter的doFilter方法中调用BaseContext来设置当前登录用户的id
    - 在MyMetaobjectHandler的方法中调用BaseContext获取登录用户的id

## JWT令牌

- 全称：Json Web Token

- 定义了一种简洁、自包含的格式，用于在通信双方以json数据格式安全的传输信息。

- 组成:

  - 第一部分: Header(头)，记录令牌类型、签名算法等。例如: {"alg":"HS256","type":"J]WT"}
  - 第二部分:Payload(有效载荷），携带一些自定义信息、默认信息等。例如:{"id":"1","username":"Tom"}
  - 第三部分: Signature(签名)，防止Token被篡改、确保安全性。将header、 payload，并加入指定秘钥，通过指定签名算法计算而来。

- 实现：

  - 导入依赖`jjwt`
  - 调用jwt令牌的工具类生成
  - 一般使用工具类实现

  ```java
  public class JwtUtils {
      private static String signKey="签名密钥";
      private static Long expire = 43200000L;//有效时间
  
      public static String generateJwt(Map<String,Object> claims){
          String jwt = Jwts.builder()
                  .addClaims(claims)//自定义信息
                  .signWith(SignatureAlgorithm.HS256,signKey)//令牌类型
                  .setExpiration(new Date(System.currentTimeMillis()+expire))
                  .compact();
          return jwt;
      }
  
      public static Claims parseJwt(String jwt){
          Claims claims = Jwts.parser()
                  .setSigningKey(signKey)
                  .parseClaimsJws(jwt)
                  .getBody();
          return claims;
      }
  }
  ```

- 可以jwt官网解析jwt令牌

- jwt令牌在每次请求中都可以通过请求头`token`拿到

- `!StringUtils.hasLength(jwt)`判断令牌是否存在

- 如果在拦截器和过滤器中验证Jwt令牌，需要手动转换对象变成json格式的数据

  - 导入依赖`fastjson`

  - 使用阿里巴巴的工具类

    ```
    string notLogin = JSONObject.toJSONString(Result.fail()) ;
    resp.getwriter ( ).write(notLogin) ;
    ```

## Swagger+Knife4j

- 自动生成接口文档

- Knife4j是swagger的增强

- 使用

  - 引入依赖
    ```
    <dependency>
       <groupId>com.github.xiaoymin</groupId>
       <artifactId>knife4j-openapi2-spring-boot-starter</artifactId>
       <version>4.2.0</version>
    </dependency>
    ```

  - 编写配置类
    ```
    @Configuration
    @EnableSwagger2WebMvc
    public class SwaggerConfig {
    
        //配置Swagger2的Docket的Bean实例
        @Bean(value = "defaultApi2")
        public Docket createRestApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    // 配置 API 的一些基本信息，比如：文档标题title，文档描述description，文档版本号version
                    .apiInfo(apiInfo())
                    // 生成 API 文档的选择器，用于指定要生成哪些 API 文档
                    .select()
                    //指定要生成哪个包下的 API 文档
                    .apis(RequestHandlerSelectors.basePackage("com.yin.teamDemo.controller"))
                    //指定要生成哪个 URL 匹配模式下的 API 文档。这里使用 PathSelectors.any()，表示生成所有的 API 文档。
                    .paths(PathSelectors.any())
                    .build();
        }
        //文档信息配置
        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    // 文档标题
                    .title("swagger_knife4j")
                    // 文档描述信息
                    .description("在线API文档")
                    // 文档版本号
                    .version("1.0")
                    .build();
        }
    }
    ```

  - 访问`http://localhost:8080/doc.html`
  
- 常用注解

  - `@ApiModel(value = "Id请求体")`：添加在类上，描述类的一些基本信息，比如请求体DTO
  - ` @ApiModelProperty("主键id")`：添加在成员变量上，对数据的解释，跟请求参数的说明差不多
  - ` @ApiOperation(value = "根据id查询用户详情")`：对某个方法/接口进行描述
  - `@ApiParam(value = "用户名称", required = false)`：表示对请求参数的说明


## 分布式登录

- 第一种：业务生成token并将用户信息存入redis，token值既是前端登录令牌也是后端redis的键存储用户信息
  ```
     //uuid随机生成token
     String token = UUID.randomUUID().toString(true);
     //转换bean，存储用户信息
     UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
     //转换为map，并手动完成序列化
     Map<String, Object> map = BeanUtil.beanToMap(userDTO);
     map.put("userId",String.valueOf(userDTO.getUserId()));
     map.put("artistId",String.valueOf(userDTO.getArtistId()));
     //将map以hash的结构存入redis并设置有效期
     stringRedisTemplate.opsForHash().putAll(Login_USER_KEY+token,map);
     stringRedisTemplate.expire(Login_USER_KEY,LOGIN_USER_TTL, TimeUnit.MINUTES);
     //返回数据
     return Result.success(token,"登录成功");
  ```

- 第二种：将session用redis存储即可解决分布式登录问题（cookie-session实现登录）

  - 引入依赖
    ```
    <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
       <groupId>org.springframework.session</groupId>
       <artifactId>spring-session-data-redis</artifactId>
    </dependency>
    ```

  - application配置
    ```
    spring:
      session:
        timeout: 86400
        store-type: redis
    ```

  - 在启动配置类中添加`@EnableRedisHttpSession`注解即可

- 利用会话技术存在一个隐患
  - 用户如果不携带cookie一直登录，redis中存储的session越来越多可能会内存溢出

## 批量导入

- 利用mybatisplus的saveBatch方法
- 嵌套循环分批导入
- 利用并发编程`CompletableFuture`

```
              final int NUM = 5000;
              int j = 0;
              List<CompletableFuture<Void>> futureList = new ArrayList<>();
              for (int i = 0; i < 20; i++) {
                  List<User> userList = new ArrayList<>();
                  do {
                      j++;
                      User user = new User();
                      user.setUserAccount("root");
                      user.setUsername("kobe");
                      user.setPassword("root");
                      user.setGender(0);
                      user.setPhone("123");
                      user.setEmail("@123.com");
                      user.setUserStatus(0);
                      user.setIsDelete(0);
                      user.setUserRole(0);
                      user.setTagList("[]");
                      userList.add(user);
                  } while (j % NUM != 0);
                  CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                      userService.saveBatch(userList,NUM);
                  },executor);
                  futureList.add(future);
              }
              CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
```

## HttpClient

- 通过HttpClient发送请求，用于第三方接口对接

- 使用：

  - 导包：httpclient

  - ```
          // 1. 创建HttpClient实例
          CloseableHttpClient httpclient = HttpClients.createDefault();
          // 2. 创建HttpPost实例
          HttpPost httpPost = new HttpPost("http://httpbin.org/post");
          // 设置请求参数（使用Json工具包）
          httpPost.setEntity(new StringEntity("this is Post"));
          // 3. 调用HttpClient实例来执行HttpPost实例
          CloseableHttpResponse response = httpclient.execute(httpPost);
          // 4. 读 response
          int status = response.getStatusLine().getStatusCode();
          HttpEntity entity = response.getEntity();
          // 5. 释放连接
          response.close();
          httpclient.close();
    ```

  - 创建HttpClient工具类

