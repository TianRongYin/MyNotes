# JavaWeb

## 前端相关

### HTML

## 后端相关

![image-20230408094415077](C:\Users\31493\AppData\Roaming\Typora\typora-user-images\image-20230408094415077.png)![image-20230408094754252](C:\Users\31493\AppData\Roaming\Typora\typora-user-images\image-20230408094754252.png)

### tomcat

简化部署：将web项目打成war包放入webapps下，war包会自动解压缩

index.jsp默认首页，动态资源在WEB-INF下

### Servlet

原理：

1. 当服务器接受客户端浏览器的访问后，会请求url路径，获取访问servlet的路径
2. 查找web.xml文件，对应的<url-pattern>标签体内容
3. 如果有就找到对应的<servlet-class>全类名
4. tomcat会将字节码文件加载进内存，创建其对象并调用方法

Servlet本身是一个接口用来，需要我们手动写实现类对象并重写方法

生命周期：

1. 被创建：执行init方法，只执行一次（默认第一次被访问的时候，也可以是服务器启动的时候，可以自己改）
2. 被访问（提供服务）：执行service方法
3. 被销毁：服务器正常关闭前执行destroy方法，一次

**快速入门**

1. 导入servlet依赖坐标javax.servlet-api，scope记得改成provided（运行期与tomcat自带的servlet依赖冲突）

2. 定义一个类实现servlet接口，重写方法（主要是service方法）
3. 在类上使用@WebServlet("访问路径")注解

==注：后续直接继承HttpServlet更方便，重写doGet和doPost方法==

### 请求和响应格式

![image-20230423190318128](C:\Users\31493\AppData\Roaming\Typora\typora-user-images\image-20230423190318128.png)

![image-20230423190340503](C:\Users\31493\AppData\Roaming\Typora\typora-user-images\image-20230423190340503.png)

![image-20230423190504137](C:\Users\31493\AppData\Roaming\Typora\typora-user-images\image-20230423190504137.png)

![image-20230423190542834](C:\Users\31493\AppData\Roaming\Typora\typora-user-images\image-20230423190542834.png)

### 路径问题

关于跳转（重定向）：

response：返回给浏览器的路径需要加上虚拟目录

request：服务器自己给本身用的不用加虚拟目录

即浏览器访问服务器需要加虚拟目录

而服务器访问服务器不需要加虚拟目录

### response

获取字符输出流