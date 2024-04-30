# CSS

## 选择器

- 后代选择器：
  - 语法：`父选择器 子选择器{ css属性 }`
  - 选择所有后代，包括子代，孙代
- 子代选择器
  - 语法：`父选择器 > 子选择器{ css属性 }`
  - 只选择子代
- 并集选择器:

  - 写法：`选择器1,选择器2,...，选择器N{CSS属性}`，选择器之间用，隔开。

  - 选中多组标签设置相同的样式。
- 交集选择器：

  - 写法：`选择器1选择器2{CSS属性}，选择器之间连写，没有任何符号`，`p.box {}` = > p标签中class为box的标签
  - 选中同时满足多个条件的元素。
- 伪类选择器：
  - 选中元素的某个状态设置样式。
  - 写法：`选择器:元素状态{ css }`，`p:hover{}`= > 鼠标悬停p标签时展示样式
  - link访问前，visited访问后，hover鼠标悬停，active鼠标点击，==前两种只适用于超链接==
  
- 结构伪类选择器
  - 根据元素的结构关系查找元素
  - 写法：`选择器:first-child{}`、`选择器:last-child{}`、`选择器:nth-child(N){}`
  - 查找第一个、最后一个、第N个，N也可以是公式，比如2n表示找偶数标签

- 伪元素选择器
  - 创建虚拟元素（伪元素)，用来摆放装饰性的内容
  - 写法：`E::before{content:}`、`E::before{content:}`，在E标签前或后添加一个伪元素
  - 必须设置content属性，用来设置伪元素的内容


## 三大特性

- 继承性：子级默认继承父级的文字控制属性
- 层叠性：
  - 相同的属性会覆盖，后面的css属性会覆盖前面的css属性
  - 不同的属性会叠加
  - 元素堆叠顺序是后来者居上，使用`z-index: N`属性可以根据属性N的大小判断显示顺序
- 优先级：
  - 通配符选择器(*) < 标签选择器 < 类选择器 < id选择器 < 行内样式 < !important (提升优先级到最高`color:red !important`)
  - 范围越广优先级越低

## Emmet写法

| 说明           | 标签结构                | Emmet                |
| -------------- | ----------------------- | -------------------- |
| 类选择器       | <div class="box"></div> | 标签名.类名(div.box) |
| id选择器       | <div id="box"></div>    | 标签名#id(div#box)   |
| 同级标签       | <div></div><p></p>      | 标签名+标签名        |
| 父子级         | <div><p></p></div>      | 父标签名 > 子标签名  |
| 多个相同的标签 |                         | 标签名*3             |
| 有内容的标签   | <div>内容</div>         | div{内容}            |

## 标准流（布局）

- 显示模式

  - 块级（block）：独占一行，宽高属性生效，默认宽度是父级的100%，例如div

  - 行内（inline，不常用）：一行共存多个，宽高属性不生效，宽高由内容撑开，例如span

  - 行内块（inline-block）：一行共存多个;宽高属性生效;宽高默认由内容撑开，例如img

  - 转换显示模式：display


- 浮动

  - 让块元素水平排列，即一行共存多个
  
  - 属性名：float，left左对齐，right右对齐

  - 父级宽度不够，浮动的子级会自动换行

  - ==会脱离标准流==，当父级元素没有设置高度，子级无法撑开父级高度则会导致页面布局错乱

  - 解决办法：清除浮动

    - 额外标签法：在父元素内容最后添加一个块级元素，并设置属性`clear:both`清除浮动

    - 单伪元素法：给父元素添加一个伪元素并设置属性`clear:both`和`display:block`

    - 双伪元素法（推荐）：
  
      ```
      .clearfix ::after,
      .clearfix ::before{
            content: "";
            display: table;/* 此元素会作为块级表格来显示（类似 <table>），表格前后带有换行符。 */
      }
      .clearfix ::after{
            clear: both;
      ```
    
  - overflow：父级元素添加属性`overflow : hidden`
  
- flex布局

  - 给父元素设置属性`display: flex`，子元素可以自动压缩或拉伸

  - 组成部分：弹性容器（父元素）、弹性盒子（子元素）、主轴（默认水平方向）、侧轴（默认垂直方向）
  - 主轴对齐方式`justify-content`：center居中、space-between元素间隔、space-around、space-evenly
  - 侧轴对齐方式`align-items`（全部子）或`align-self`（单个子）：stretch拉伸（弹性盒子没有设置尺寸）、center居中，
  - 修改主轴方向：`flex-direction: column`垂直方向
  - flex布局默认主轴尺寸按内容撑开、侧轴拉伸
  - 弹性伸缩比：`flex: N`，N是一个数字，可以设置该弹性盒子在主轴占用父级剩余尺寸的份数
  - 因为弹性盒子的自动压缩的特性，所以默认不换行，切换换行：`flex-wrap: wrap`
  - 行对齐方式（相当于侧轴）：`align-content`
  - 神奇：
    - `margin-left: auto;`将单个元素推到弹性盒子的最右边

## 定位

- `position:`
  - `relative`：相对定位，不脱标，占位，参照原来的位置改变，不改变显示模式
  - `absolute`：绝对定位，脱标，不占位，参照父级的相对定位改变，没有则想上找直到参照浏览器，改变显示模式（行内块）
  - `fixed`：绝对定位，不随页面滚动，参照浏览器位置改变，改变显示模式（行内块）
- 通常都是父级相对定位，子级绝对定位搭配使用
- 绝对定位的子级不想继承父级的事件怎么办？设置同级暂时能解决

## 动画

- 过渡
  - `transition: 过渡的属性 花费时间（s）`
  - 过渡的属性可以为all，transition设置给元素本身
- 透明度：`opacity`

## 文本文字

- 想要文本文字在标签块内垂直居中，需要设置行高（line-height）与盒子高度（height）相同
- `input::placeholder{}`控制属性文字样式
- `vertical-align`行内块和行内垂直方向对齐方式
  - 行内块和行内默认所有元素当文字处理，图片底下按基线会留白，转显示模式或改变对齐方式就能解决

## 图或背景图

- 内容溢出`overflow:`属性，hidden隐藏溢出内容，scroll滚动条常驻，auto自动生成滚动条

- 背景图：

  ```css
    background-image: url("");
    background-repeat: no-repeat;
    background-size: 100% 100%;
  ```

## 杂记

- 清楚标签自带默认样式：
  - `*{padding: 0 ; margin: 0 ;}`
  - 列表符号`li{list-style = none;}`
  - 设置统一的字体和颜色
  - 去除a标签默认下划线：`text-decoration:none;`

- 外边距合并和坍塌
  - 合并：两个垂直排列的兄弟元素，上下margin会合并，取最大值生效
  - 坍塌：父子级标签，子级添加上外边距会产生坍塌问题（留白）
    - 父级设置`overflow: hidden`

- 行内元素使用`line-height`改变垂直内外边距

- 搜索引擎优化

- 去掉表单控件的焦点框
  ```
      background-color: transparent;
      appearance: none;
      border: none;
      outline: none;
  ```

- `transform: translate( -50%,-50%);`：表示子级向左上移动自身尺寸的一半，配合绝对定位中的左上偏移50%可以形成整个页面居中显示，常用于弹框

- 光标类型：`cursor: pointer/text/move`点击/选择文字/移动

# JS

## 事件

| 事件        | 描述                         |
| :---------- | :--------------------------- |
| onchange    | HTML 元素已被改变            |
| onclick     | 用户点击了 HTML 元素         |
| onmouseover | 用户把鼠标移动到 HTML 元素上 |
| onmouseout  | 用户把鼠标移开 HTML 元素     |
| onkeydown   | 用户按下键盘按键             |
| onload      | 浏览器已经完成页面加载       |
| onfocus     | 获得焦点                     |
| onblur      | 失去焦点                     |

移动端事件：

`touchstart`触摸、`touchmove` 滑动 `touchend`手指离开

## 字符串方法

两种方法，`indexOf()` 与 `search()`，是*相等的*。

这两种方法是不相等的。区别在于：

- search() 方法无法设置第二个开始位置参数。
- indexOf() 方法无法设置更强大的搜索值（正则表达式）。

属性访问：

- 如果找不到字符，`[ ]` 返回 `undefined`，而 `charAt()` 返回空字符串。

- 它是只读的。`str[0] = "A"` 不会产生错误（但也不会工作！）

## 数字

- NaN - 非数值：

  - `NaN` 属于 JavaScript 保留词，指示某个数不是合法数。

  - 您可使用全局 JavaScript 函数 `isNaN()` 来确定某个值是否是数

  - 要小心 `NaN`。假如您在数学运算中使用了 `NaN`，则结果也将是 `NaN`：

  - `NAN`是数，`typeof NaN` 返回 `number`

- Infinity:

  - `Infinity` （或 `-Infinity`）是 JavaScript 在计算数时超出最大可能数范围时返回的值。

  - 除以 0（零）也会生成 `Infinity`

  - `Infinity` 是数：`typeOf Infinity` 返回 `number`。

- 十六进制

  - JavaScript 会把前缀为 `0x` 的数值常量解释为十六进制。

  - 绝不要用前导零写数字（比如 07）。一些 JavaScript 版本会把带有前导零的数解释为八进制。

  - 您能够使用 `toString()` 方法把数输出为十六进制、八进制或二进制。`Number.toString(8)` = > 返回八进制输出

- 大整数：

  - BigInt 不能有小数。

  - 不允许在 BigInt 和 Number 之间进行算术运算（类型转换会丢失信息）。

  - BigInt 无法进行无符号右移操作（>>>），因为它没有固定的宽度。

- 安全整数：

  - 安全整数是从 -(2^53^ - 1) 到 +(2^53^ - 1) 的所有整数。

  - ```
    9007199254740992 === 9007199254740993; // 为 true !!!
    ```

- Number() 方法也可以将日期转换为数字。

## 数组

- `join()` 方法也可将所有数组元素结合为一个字符串。它的行为类似 toString()，但是您还可以规定分隔符：

- 使用 `delete` 会在数组留下未定义的空洞（undefined）。请使用 `pop()` 或 `shift()` 取而代之。

- ```
  var myChildren = arr1.concat(arr2, arr3);   // 将arr1、arr2 与 arr3 连接在一起
  ```

- `forEach()` 方法为每个数组元素调用一次函数（回调函数）。

- `filter(方法)`根据条件，保留满足条件的对应项，得到一个新数组

- `reduce(function(pre,current){},起始值)`，循环执行函数，没有初始值就用数组di'yi'ge

## 日期与时间函数

- 日期对象：`new Date(毫秒值/时间)`
  - getFullYear, getMonth, getDate, getHours, getDay, getMinutes, getSeconds, getTime

  - 获取时间戳：`日期对象.getTime()`、`+new Date()`、`Date.now()`

- 定时器函数：
  - `let 变量名 = setInterval(函数名,间隔时间)`每隔一段时间（默认单位毫秒）执行一次函数，函数名不要写小括号
  - `clearInterval(变量名)`：关闭计时器

- 延时函数：
  - `let 变量名 = setTimeout(回调函数，等待的毫秒数)`仅执行一次，延迟执行
  - `clearTimeout(变量名)`清除延时函数


## 正则表达式

- 正则表达式可用于执行所有类型的**文本搜索**和**文本替换**操作。

- 语法：

  - `const 变量 = /规则/`定义正则表达式的规则
  - `变量.test(字符串)`判断是否有符合规则的字符串，返回布尔值
  - `变量.exec(字符串)`查找符合规则的字符串，返回数组或None

- 元字符：

  - 边界符：`^`字符：以什么开头，`字符$`：以什么结尾，`^字符$`：精准匹配单个字符

  - 量词：

    | 量词 | 说明           | 量词  | 说明          |
    | ---- | -------------- | ----- | ------------- |
    | *    | 重复零次或多次 | {n}   | 重复n次       |
    | +    | 重复一次或多次 | {n,}  | 重复n次及以上 |
    | ？   | 0或1次         | {n,m} | 重复n到m次    |

  - 字符类：

    - [abc]、[a-z]：只选一个，加上量词才能多选
    - [a-zA-Z0-9]：大小写数字都可以
    - [^a-z]：取反，除了小写字母都可以
    - `.`匹配除了换行符的所有单个符号
    - 特殊符号放中括号最后

## DOM

### Dom控制属性样式

- 查找Dom元素（通过css选择器查找）
  - `document.querySelector('#box')`：找一个
  - `document.querySelectorAll('.box')`：找多个
- 修改内容
  - `.innerText`：修改内容，不能解析标签
  - `.innerHTML`：能解析标签（即修改标签）
  - `inner`是双标签里的内容，`value`是单标签里的内容，所以表单的内容（`input`）只能通过value获取
- 修改属性：`对象.属性`即可修改
- 修改样式：`对象.style.样式属性`即可修改
- 通过类名修改样式
  - `对象.className`修改类名，会覆盖原有的类，原有的类也要写上
  - `对象.classList.add/remove/toggle`添加/修改/更换类名
- 自定义属性
  - 标准属性是标签自带的属性，可以直接使用点语法进行操作
  - 自定义属性语法：`data-自定义属性=" "`
  - 在Dom对象中一律用`.dataset.自定义属性`获取

### Dom控制事件

- 事件监听
  - `事件源.on事件 = function(){}`，会被覆盖
  - `事件源.addEventListener(事件,事件处理函数（，true/false）)`，不会被覆盖
  - `事件源.removeEventListener(事件,事件处理函数（，true/false）)`，移除事件监听
  - input：用户输入事件
  - true为事件捕获（从外到里），默认false事件冒泡（从里到外）
  - 事件对象e.stopPropagation()阻止流动传播
  - 事件对象e.preventDefault()阻止默认行为（表单提交等）
- 事件对象（event）
  - 事件绑定的回调函数中的参数就是事件对象
  - 常用属性：
  - `type`当前事件类型
  - `clientX/clientY` 相对于浏览器窗口的位置
  - `offsetX/offsetY` 相对于当前Dom元素位置
  - `key`用户按下的按键值 
  - `target`点击的对象，`target.tagName`点击对象的名字
- 事件流
  - 事件冒泡：父级绑定的监听会被子级继承，向上传递，所有祖先元素依次触发
  - 事件捕获反之
  - 关闭：`@`
  - 利用事件冒泡的特性，可以通过对父元素进行事件监听拿到事件对象，从事件对象中操作子元素
- 页面事件
  - 页面加载
    - `load`：给window加上这个事件的监听器，当整个页面资源加载完就会执行这个方法
    - `DOMContentLoaded`：给document加，无需等待样式、图像等完全加载就会执行

  - 页面滚动
    - `scroll`：`scrollTop/scrollLeft`可以读取和修改被卷去的头部或左侧的像素值
    - `document.documentElement.scrollTop`表示页面滚动的头部距离

### Dom节点

- 查找节点
  - `DOM对象.parentNode`返回父节点
  - `DOM对象.children`返回所有子节点，得到一个伪数组
  - `DOM对象.nextElementSibling`、`DOM对象.previousElementSibling`返回兄弟节点（前/后）
- 增加节点
  - `document.createElement('div')`创建节点
  - `DOM对象.cloneNode(true)`返回一个克隆节点
  -  `DOM对象.appendChild(节点)`追加节点，成为最后一个子元素
  -  `DOM对象.insertBefore(插入的节点，放到哪个节点之前)`
- 删除节点
  - `DOM对象.removeChlid(子元素)`只能通过父元素删除子元素

## BOM

所有 JavaScript 全局对象、函数以及变量均自动成为 window 对象的成员。

- location对象指的是地址栏
  - `location.href`页面跳转
  - `location.search`地址栏中的参数部分（？后面的部分）
  - `location.hash`地址栏中的哈希值（符号#后面的值），vue中的路由需要用到
  - `location.reload()`方法，传入参数true表示强制刷新
- navigator对象封装了浏览器自身的信息，可以用来判断pc还是移动端
- history对象管理历史记录
  - `history.back()` `history.forward()` `history.go(num)`后退、前进、num为正数就前进几步，后退反之
- 本地存储：
  - `localStorage`
    - 永久存储，只能存字符串
    - `localStorage.setItem(key,value)`存入或修改
    - `localStorage.getItem(key)`获取
    - `localStorage.removeItem(key)`删除
  - `sessionStorage`
    - 生命周期持续到浏览器关闭，只能存字符串
  - 存储对象需要通过转换成json格式的字符串来存储

## 防抖和节流

- 防抖：
  - 单位时间内，频繁触发事件，只执行最后一次，节省性能，避免多次发送请求
  - 使用： 导入lodash库函数，在监听器的回调函数中使用`_.debounce(执行函数，单位时间)`即可
  - 底层原理实现：声明一个定时器变量，每次调用函数的时候判断是否有定时器，有则清除以前定时器，建立新的定时器
- 节流：
  - 单位时间内，频繁触发事件，只执行一次
  - 使用：导入lodash库函数，语法`_throttle(执行函数，单位时间)`
  - 底层：声明一个定时器函数，每次调用函数的时候判断是否有定时器，有则不开启新定时器

## 箭头函数

```
const fn = () =>{} //基本语法
const fn = x => {} //x是唯一形参
const fn = () => 代码/返回值 //只有一行代码
const fn = (uname) => ({ name : uname})//直接返回一个对象
consf fn = (...args) = {}//没有动态参数，只有剩余参数
```

- 箭头函数不会创建自己的this，会沿用上一级的this，比正常函数的this还要上一级

## 回调函数地狱

- 简单描述：前一个异步函数的结果是后一个异步函数的参数

- 解决办法：

  - 把回调函数嵌套的代码，改成promise链式调用结构

  - ```
    const p1 = new Promise((resolve,reject) => {})
    const p2 = p1.then(result={
    	//代码逻辑
    	//返回一个新的promise对象
    	return new Promise(resolve,reject) => {})
    })
    ```

- 原理：then()方法的返回值会返回一个新的promise对象的特性

- async和await

  - 函数前的关键字 `async` 使函数返回 promise

  - 函数前的关键字 `await` 使函数等待 promise

  - ```
    async function getData(){
    	const data = await axios({url:'xxx'})
    	const data2 = await axios({url:xxx}, params:{ data })
    }
    ```

## 杂记

- 随机数：

  ```
  function getRndInteger(min, max) {
      return Math.floor(Math.random() * (max - min) ) + min;
  }
  ```

- 所有不具有“真实”值的即为 False

- Switch case 使用严格比较（`===`），值必须与要匹配的类型相同。

- 通过将其值设置为 `undefined`，可以清空任何变量。类型也将是 `undefined`。

- `null`：你可以认为它是 JavaScript 中的一个 bug，`typeof null` 是一个对象。类型应为 `object`。

- `null`和`undefined`值相等类型不相等

- `use strict`严格模式使我们更容易编写“安全的” JavaScript。具有块作用域

- 用字符串减去字符串，不会产生错误而是返回 `NaN`

- Json转换

  - `JSON.stringify(obj)`：json转字符串
  - `JSON.parse(jsonstr)`：字符串转json

- arguments动态参数，返回传入方法的所有参数（数组），`...args`剩余参数，前面几个正常赋值，多出来的参数加入剩余参数数组

-  解构：解构就是从数组或对象中拿取值`const {name} = user `、`const [a, b, ...rest] = numbers`

  - 支持嵌套：`const {message, data : { data }} = axios.get()`
  
- 假链接：`<a href="javascript:;" >`

- 对象或数组打平

  - flatMap( parent => { parent.children })：将对象数组中的某一属性抽取出来成为新的数组
  - flat(depth)：按照给定深度（默认为1）将元素递归并将子元素和父元素合并在一个数组

- ES6数组和对象复制：`const people = {...oldpeople}`、`const array = [...oldArray]`


# 一些奇怪的属性

- 文本溢出：
  ```
  white-space: nowrap;//规定段落中的文本不进行换行
  overflow: hidden;//
  text-overflow:ellipsis;//当文本溢出包含它的元素时用省略号代替，需要搭配上面两个使用
  ```

- 

