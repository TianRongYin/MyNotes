# Redis

## 安装配置与简单使用

- 下载安装：
  - `apt-get install redis`
- 配置文件：
  - `vim /etc/redis/redis.conf`
- 命令行客户端：
  - `redis-cli [-h 要连接的redis节点的IP地址（默认127.0.0.1） -p 端口 -a 访问密码 ] [命令]`
  - 不填写命令即可进入交互平台
  - `AUTH`：指定用户名和密码
  - `ping`心跳测试：正常返回pong
  - `set name value`、`get name`：存值取值
- 通用命令
  - 使用`help [command]`可以查看一个命令的具体用法
  - `KEYS *`查找符合条件的所有键
  - `DEL [key]`删除一个或多个指定的键
  - `EXISTS`判断key是否存在
  - `EXPIRE [key seconds] `：给一个key设置有效期，到期自动删除
  - `TTL [key]`查看一个key的有效期
- 官方文档：[Commands | Redis](https://redis.io/commands/)

## 数据结构

- key的格式：`[项目名]:[业务名]:[类型]:[id]`，例如`[heima]:[product]:[id]`
- String
  - `set name value [ex seconds] [nx]`、`get name`
  - `MSET`：批量添加、`MGET`：批量查询
  - `INCR`：自增、`INCRBY`：自增并设置步长`INCRBYFLOAT `：浮点数自增并设置步长
- Hash
  - `HSET key field value`：添加或修改、`HGET key field`：查询
  - `HMSET`、`HMGET`：批量操作
  - `HGETALL`获取hash类型一个key中的所有field和value
  - `HKEYS`、`HVALS` ：获取一个hash类型中的所有field或value
- List
  - 与java中的LinkedList相同，是一个双向链表
  - `LPUSH key elements...`：向列表左侧插入一个或多个数据、`RPUSH`右侧
  - `LPOP key`：移除并返回左侧第一个元素、`RPOP`右侧
  - `LRANGE key start end`：返回一段范围内的所有元素
  - `BLPOP key seconds`：与LPOP一样，不过在没有元素时会进行等待，需要指定等待时间
- Set
  - 无序，不重复，多用于做集合间的运算，比如计算共同好友
  - `SADD key member...`：向set集合中添加元素、`SREM key member...`移除指定元素
  - `SCARD key`：计算set集合中元素的总个数
  - `SISMEMBER key member`：判断该元素是否存于set中
  - `SMEMBERS key`：获取set中的所有元素
  - `SINTER`：交集、`SDIFF`：差集、`SUNION`：并集
- SortedSet（Zset）
  - 可排序，不重复，每个存入的元素都需要设置一个score值并根据score属性进行排序
  - `ZADD key score member...`：添加一个或多个，`ZREM key member`：删除一个
  - `ZSCORE key member`：获取指定元素的score值，`ZRANK key memeber`：获取指定元素的排名
  - `ZCARD key`：获取所有元素总个数、`ZINCRBY key increment member`：自增
  - `ZCOUNT key min max`：统计score值在指定范围内的所有元素的个数
  - `ZRANGE key min max`：获取指定排名范围内的元素、`ZRANGEBYSCORE key min max`：获取指定score范围内的元素
  - `ZINTER`、`ZDIFF`、`ZUNION`：交差补
  - 降序排名的命令只需要在`Z`后面添加`REV`即可，例如`ZREVRANK key member`

## Jedis

### 简单使用

```java
public class JedisTest {
    private Jedis jedis;
    @BeforeEach
    void setUp(){
        jedis = new Jedis("169.254.242.132",6379);
        jedis.auth("155357ytr");
        jedis.select(0);
    }
    @Test
    void testString(){
        String result = jedis.set("name", "张三");
        System.out.println(result);
        System.out.println(jedis.get("name"));
    }
    @AfterEach
    void tearDown(){
        if(jedis != null){
            jedis.close();
        }
    }
}
```

### Jedis连接池

```java
public class JedisConnectionFactory{
    private static final JedisPool jedisPool;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接
        jedisPoolConfig.setMaxTotal(8);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(8);
        //最小空闲连接
        jedisPoolConfig.setMinIdle(0);
        //最长等待时间，ms
        jedisPoolConfig.setMaxWaitMillis(200);
        jedisPool = new JedisPool(jedisPoolConfig,"169.254.242.132",6379,1000,"155357ytr");
    }
    //获取Jedis对象
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
```

## SpringDataRedis

- 提供了对不同Redis客户端的整合( Lettuce和Jedis)
- 提供了RedisTemplate统一API来操作Redis
- 支持Redis的发布订阅模型
- 支持Redis哨兵和Redis集群
- 支持基于Lettuce的响应式编程
- 支持基于JDK、JSON、字符串、Spring对象的数据序列化及反序列化
- 支持基于Redis的JDKCollection实现
- SpringDataRedis中提供了RedisTemplate工具类，其中封装了各种对Redis的操作。并且将不同数据类型的操作API封装到不同的类型中

|             API             |   返回值类型    |         说明          |
| :-------------------------: | :-------------: | :-------------------: |
| redisTemplate.opsForValue() | ValueOperations |  操作String类型数据   |
| redisTemplate.opsForHash()  | HashOperations  |   操作Hash类型数据    |
| redisTemplate.opsForList()  | ListOperations  |   操作List类型数据    |
|  redisTemplate.opsForSet()  |  SetOperations  |    操作Set类型数据    |
| redisTemplate.opsForZSet()  | ZSetOperations  | 操作SortedSet类型数据 |
|        redisTemplate        |                 |       通用命令        |

### RedisTemplate

- 使用步骤

  - 导入依赖：

    - spring-boot-starter-data-redis
    - commons-pool2

  - application.yml配置

    ```
    spring:
      redis:
        host: 169.254.242.132 #IP地址
        port: 6379 #端口号
        password: 155357ytr #密码
        database：0 #哪个库，默认0
        lettuce: # jedis: 使用jedis实现则需要引入jedis依赖
          pool:
            max-active: 8 # 最大连接数
            max-idle: 8 # 最大空闲连接数
            min-idle: 0 # 最小空闲连接数
            max-wait: 100ms # 连接等待时间
    ```
  
  - 注入RedisTemplate并使用

### RedisSerializer

* 默认为JDK序列化器

* 自定义序列化方式

  ```java
  @Bean // 工厂类会有SpringBoot自动创建
  public RedisTemplate<String, Object> redsiTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
      // 创建Template
      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
      // 设置连接工厂
      redisTemplate.setConnectionFActory(redisConnectionFactory);
      // 设置序列化工具
      GenericJackSon2JsonRedisSerializer jsonRedisSerializer = new 								GenericJackSon2JsonRedisSerializer();
      // key和hashkey采用String序列化
      redisTemplate.setKeySerializer(RedisSerializer.string());
      redisTemplate.setHashKeySerializer(RedisSerialzer.string());
      // value和hashValue采用JSON序列化
      redisTemplate.setValueSerializer(jsonRedisSerializer);
      redisTemplate.setHashValueSerializer(jsonRedisSerializer);
      return redisTemplate;
  }
  ```

### StringRedisTemplate

* 为了实现反序列化，Json序列化器会将类的class类型写入json结果中，存入Redis，会带来额外的内存开销。

  * 如：com.demo.domain.User

* 为了节省内存空间，不会使用JSON序列化器来处理value，而是统一使用String序列化器，要求只能存储String类型的key和value。当需要使用java对象时，手动完成对象的序列化和反序列化。

* Spring提供一个默认为String序列化方式的类StringRedisTemplate

* 手动序列化

  ```java
  String json = new ObjectMapper().writeValueAsString(object);
  ```

* 手动反序列化

  ```java
  Object object = new ObjectMapper().readValue(json, object.class);
  ```

* ==ObjectMapper是Json转String包下的一个工具类。SpringWeb依赖中自带==

* 其余操作类似

### 操作Hash类型

* 使用put增加数据 
* 使用get取出数据
* 使用entries获取所有值

## 缓存

- 缓存是将经常查询的数据放入Redis中，每次查询都先通过redis，没有再去数据库中查询并将其存入redis，这样可以提高查询速度

- 缓存更新
  - 主动更新：先更新数据库，再删除缓存，并且用超时剔除方案（缓存数据设置超时时间）兜底
- 缓存穿透
  - 客户端请求的数据在缓存和数据库中都不存在时，请求都会被打到数据库，浪费时间
  - 策略：
    - 缓存空值：数据库中查询出不存在的数据，将会以空值的形式存入缓存中，下次查询就不会进入数据库了
    - 布隆过滤：原理差不多是在查询redis之前经过布隆过滤判断数据是否存在于数据库，过滤不存在的请求，有误判的风险
- 缓存雪崩
  - 在同一时段大量的缓存key同时失效或者Redis服务宕机，导致大量请求到达数据库，带来巨大压力
  - 解决方案
    * 给有效期添加随机值
    * 利用Redis集群提高服务的可用性
    * 给缓存业务添加限流策略
    * 给业务添加多级缓存
- 缓存击穿
  - 热点key，就是一个被**高并发访问**并且**缓存重建业务较复杂**的key突然失效了，无数的请求访问会在瞬间给数据库带来巨大的冲击
  - 解决方案
    * 互斥锁（性能较低）	
      * 使用setnx(setIfAbsent)设置锁，使用del释放锁（设置有效期避免程序错误或者si'suo）
      * 使线程休眠并递归调用该业务（只有一个线程进行重建其他线程循环等待）
    * 逻辑过期（一致性较低）
      * 设置为永不过期，新加字段来表示过期时间
      * 判断对象是否过期
      * 使用互斥锁，但是在数据重建时及未取得锁时会返回过期信息

## 秒杀问题

### 库存超卖

- 悲观锁

  - synchronized lock
  - 效率低

- 乐观锁

  - 关键在于对数据修改时判断是否有其他线程

  - 实现

    - 版本号：增加版本号字段，每次修改都会使版本号自增，只有当前后两次版本号相同时才会进行修改
    - CAS：直接用数据本身来做判断

  - 弊端：同时间多个请求只有一个成功（成功率低）

    - 解决：只判断优惠券数量是否大于0

    - ```
      set
      	stock = stock - 1
      where
      	id = 
      	and stock > 0
      ```

### 一人一单

- 使用悲观锁
- 用id作为锁对象：`synchronized (id.toString().intern()) {}`
- 流程：
  - 查询优惠券
  - 判断秒杀库存
  - 查询订单
  - 校验一人一单
  - 减库存
  - 创建订单

### 优化

- 关键

  - 新增优惠券的同时将优惠券信息保存到Redis中

  - 将优惠券id作为key，库存数量做值

  - 将优惠券id作为key，下单的用户id集合做值

  - 编写lua脚本，判断库存，判断用户id集合中是否已经存在，然后再进行库存数量-1并将用户id存入集合

  - 成功后将优惠券id和用户id放入阻塞队列

  - 开启线程任务，不断从阻塞队列中获取信息，实现异步下单


## 分布式锁

- 获取锁：`set lock threadname nx ex (ttl) `
  - 设置互斥命令和有效时间，即可实现互斥锁和避免服务
  - setnx命令一旦执行失败就会返回flase，利用这个判断
  
- 释放锁：`del lock`

- Java代码中使用`setIfAbsent(key,value,second,TimeUnit.SECONDS)`获取锁
  - UUID和当前线程id做value，key可以用用户id进行拼接
  
- 在释放锁之前将线程id与Redis存放的锁内存储的线程id相等再释放，防止锁误删

- 业务执行时间超过锁的释放时间：看门狗机制，给锁续期
  
- Mysql行级锁：`select and update`

### Lua脚本

- 分布式锁的原子性问题：

  - 拿取并判断锁和释放锁必须是一个原子性的命令，所以采用Redis的Lua脚本来将多条命令绑定

  - 菜鸟教程中有Lua脚本的教程，写好脚本后使用Redis命令调用即可
    ```
    --redis.call("命令名称",'key','其他参数')可以使用参数数组，索引从1开始
    --比较线程标示与锁中的标示是否一致
    if(redis.call('get',KEYS[1] == ARGV[1]) then
    	--释放锁del key
    	return redis.call('del',KEYS[1])
    end
    --不一致则直接返回
    return 0
    ```

  - Redis命令调用Lua脚本：`EVAL "脚本" 'numbers' [keys] [ags]`，numbers指定key数量

  - Java调用：
    ```
    //定义
    private static final DefaultRedisscript<Long> UNLOCK_SCRIPT;
    static {
    	UNLOCK_SCRIPT = new DefaultRedisScript<>( );
    	UNLoCK_SCRIPT.setLocation(new ClassPathResource("路径"));
    	UNLOCK_SCRIPT.setResultType(Long.class);
    }
    //调用
    stringRedisTemplate.execute(UNLOCK_SCRIPT,Collections.singletonList(KEY),uuid+线程id)
    ```


### Redisson

- Redisson是一个在Redis的基础上实现的Java驻内存数据网格。它不仅提供了一系列的分布式的Java常用对象，还提供了许多分布式服务，其中就包含了各种分布式锁的实现。

- Redisson是一个java 操作Redis 的客户端，提供了大量的分布式数据集来简化对Redis的操作和使用，可以让开发者像使用本地集合一样使用Redis，完全感知不到 Redis的存在。

- 使用：

  - 引入依赖`redission`

  - 配置客户端
    ```
    @Configuration
    public class Redisconfig {
       @Bean
       public RedissonClient redissonclient() {
          //配置类
          Config config = new Config();
          //添加redis地址，这里添加了单点的地址，也可以使用config.useclusterServers()添加集群
          config.useSingleServer ()
          					.setAddress("redis://192.168.150.101:6379")
          					.setPassword();//没有密码就不能配置密码，会报错
          					//.setDatabase(3);
          //创建客户端
          return Redisson.create(config);
       }
    }
    ```

  - 使用
    ```
    //获取锁（可重入），指定锁的名称
    RLock lock = redissonclient.getLock( "anyLock"");
    //尝试获取锁，参数分别是:获取锁的最大等待时间（期间会重试)，锁自动释放时间，时间单位
    boolean isLock = lock.tryLock(1,10,TimeUnit.SECONDS);
    ```

- 原理

  - 可重入
    - 用Hash存储锁，设置线程id和访问次数两个字段
    - 每次获取锁的时候，除了判断锁是否存在，还需要判断当前锁自己是否拥有，已拥有则访问次数加1
    - 释放锁的时候，只需要将访问次数减1，并判断锁的访问次数是否为0，归零就可以直接删除锁

  - 可重试：利用信号量和PubSub功能实现等待、唤醒，获取锁失败的重试机制
  - 超时续约
    - 看门狗机制：需要将锁的自动释放时间设为-1
    - 监听当前线程，TTL为30秒，每隔10秒续期一下锁的释放时间（补为30秒）
    - 如果当前线程挂掉或者长时间断点调试，将会被看门狗认作服务器宕机，不会续期

- multilock

  - 多个独立的Redis节点，必须在所有节点都获取重入锁，才算获取锁成功

  - ```
    RLock lock1 = redissonclient.getLock( name: "order" );
    RLock lock2 = redissonclient2.getLock( name: "order") ;
    RLock lock3 = redissonclient3.getLock( name: "order") ;
    lock = redissonclient.getMultiLock(ldck1，lock2，lock3);
    ```

## Redis消息队列

- 基于List
  - list数据结构就是一个双向链表，很容易模拟出队列效果
  - `LPUSH`+`RPOP`或者`RPUSH`+`LPOP`即可实现队列，不过当队列中为空时，L/RPOP操作取值为null
  - 使用`BRPUSH`+`BLPOP`即可实现阻塞效果
  - 只支持单消费者
- 基于PubSub
  - 发布订阅模型，支持多消费者多生产者
  - SUBSCRIBE channel [channel]:订阅一个或多个频道
  - PUBLISH channel msg:向一个频道发送消息
  - PSUBSCRIBE pattern[pattern] :订阅与pattern格式匹配的所有频道
  - 不支持数据持久化，容易消息丢失
- Stream
  - 

## 杂记

- `template.expire()`：刷新存储键值对的有效期
- `CountDownLatch`是一个同步工具类，它通过一个计数器来实现的,初始值为线程的数量。每当一个线程完成了自己的任务,计数器的值就相应得减1。当计数器到达0时,表示所有的线程都已执行完毕,然后在等待的线程就可以恢复执行任务。

- ==spring整合redis时，注意redis只能存储string类型的数据，记得完成序列化==

- 

