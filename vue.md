# Json

格式：

```
{
    "key":value,
    "key":value,
    "key":value
}
```

字符串转json：`var obj = JSON.parse(jsonstr);`

json转字符串：`JSON.stringify(obj)`

# ajax

```
//1. 创建XMLHttpRequest 
var xmlHttpRequest  = new XMLHttpRequest();
//2. 发送异步请求
xmlHttpRequest.open('GET','http://yapi.smart-xwork.cn/mock/169327/emp/list');
xmlHttpRequest.send();//发送请求
//3. 获取服务响应数据
xmlHttpRequest.onreadystatechange = function(){
    //此处判断 4表示浏览器已经完全接受到Ajax请求得到的响应， 200表示这是一个正确的Http请求，没有错误
    if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
        document.getElementById('div1').innerHTML = xmlHttpRequest.responseText;
    }
}
```

# axios

在vue项目下打开终端，输入命令`npm install axios`

```
axios({
    method:"post",
    url:"http://localhost:8080/ajax-demo1/aJAXDemo1",
    data:"username=zhangsan"
}).then(function (resp){
    alert(resp.data);
});
```

axios拦截器配置

```
axios.interceptors.request.use(config => {
  // 获取 token
  const token = sessionStorage.getItem("token")
  // 如果有 token，在请求头中添加 token 字段
  if (token) {
      config.headers.Authorization = token
  }
  return config
}, error => {
  return Promise.reject(error)
})
```

# vue

## 入门

- 创建
  - 命令行创建：vue create vue-project01
  - 图形化界面：vue  ui
- 目录结构

![1669302973198](C:/Users/31493/Desktop/project/%E7%AC%94%E8%AE%B0/%E5%89%8D%E7%AB%AF%E4%B8%89%E5%89%91%E5%AE%A2/day03-Vue-Element/day03-Vue-Element/%E8%AE%B2%E4%B9%89/assets/1669302973198.png)

- 修改端口

  - vue.config.js文件中加入

  ```
  devServer:{
      port:7000
  }
  ```

- 启动

  - 命令行：npm run serve
  - 脚本npm直接运行serve

- 对于vue项目，index.html文件默认是引入了入口函数main.js文件

  - import: 导入指定文件，并且重新起名。例如上述代码`import App from './App.vue'`导入当前目录下得App.vue并且起名为App
  - new Vue(): 创建vue对象
  - $mount('#app');将vue对象创建的dom对象挂在到id=app的这个标签区域中，作用和之前学习的vue对象的le属性一致。
  - router:  路由，详细在后面的小节讲解
  - render: 主要使用视图的渲染的。

- vue文件
  - template: 模板部分，主要是HTML代码，用来展示页面主体结构的
  - script: js代码区域，主要是通过js代码来控制模板的数据来源和行为的
  - style: css样式部分，主要通过css样式控制模板的页面效果得

## element

- 在当前项目目录下安装组件库

  ```
  npm install element-ui@版本号
  ```

- 将main.js中引入组件库

  ```
  import ElementUI from 'element-ui';
  import 'element-ui/lib/theme-chalk/index.css';
  Vue.use(ElementUI);
  ```

- 按照vue项目规范，我们需要在src/views目录下，创建文件夹和ElementView.vue文件，在其中编写组件语法

- ElementUI的官网，找到组件库，然后找到按钮组件，抄写代码即可

## 路由跳转

在index.js文件中配置routes

```
{
    path:'/',
    name:'login',
    component: () => import('../views/Login.vue')
 },
```

main.js中引入

```
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

```

# 跨域代理

在vue.config.js配置文件下加入

```js
devServer :{
    port: 7000,//端口号
    proxy: {//代理
      '/api': {
        target: 'http://localhost:8090',//后端d
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
}
```

将前端请求的URL修改为`/api/*`，这将被代理到`http://localhost:8090/*`。