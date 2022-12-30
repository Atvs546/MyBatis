# 1.一级缓存

一级缓存是SqlSession级别的，通过同一个SqlSession查询的数据会被缓存，下次查询相同的数据，就 

会从缓存中直接获取，不会从数据库重新访问 

使一级缓存失效的四种情况： 

\1) 不同的SqlSession对应不同的一级缓存 

\2) 同一个SqlSession但是查询条件不同 

\3) 同一个SqlSession两次查询期间执行了任何一次增删改操作 

\4) 同一个SqlSession两次查询期间手动清空了缓存

# 2.二级缓存

二级缓存是SqlSessionFactory级别，通过同一个SqlSessionFactory创建的SqlSession查询的结果会被 

缓存；此后若再次执行相同的查询语句，结果就会从缓存中获取 

二级缓存开启的条件： 

a>在核心配置文件中，设置全局配置属性cacheEnabled="true"，默认为true，不需要设置 

b>在映射文件中设置标签<cache /> 

```html
</delete> 
<!--int deleteMoreByArray(int[] eids);--> 
<delete id="deleteMoreByArray"> 
  delete from t_emp where eid in 
  <foreach collection="eids" item="eid" separator="," open="(" close=")"> 
    #{eid} 
  </foreach> 
</delete> 
<sql id="empColumns"> 
  eid,ename,age,sex,did 
</sql> 
select <include refid="empColumns"></include> from t_emp 
```

更多Java –大数据 – 前端 – UI/UE - Android - 人工智能资料下载，可访问百度：尚硅谷官网(www.atguigu.com)c>二级缓存必须在SqlSession关闭或提交之后有效 

d>查询的数据所转换的实体类类型必须实现序列化的接口 

使二级缓存失效的情况： 

两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效



# 二级缓存相关的配置

在mapper配置文件中添加的cache标签可以设置一些属性： 

eviction属性：缓存回收策略 

LRU（Least Recently Used） – 最近最少使用的：移除最长时间不被使用的对象。 

FIFO（First in First out） – 先进先出：按对象进入缓存的顺序来移除它们。 

SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。 

WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。 

默认的是 LRU。 

flushInterval属性：刷新间隔，单位毫秒 

默认情况是不设置，也就是没有刷新间隔，缓存仅仅调用语句时刷新 

size属性：引用数目，正整数 

代表缓存最多可以存储多少个对象，太大容易导致内存溢出 

readOnly属性：只读，true/false 

true：只读缓存；会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了 

很重要的性能优势。 

false：读写缓存；会返回缓存对象的拷贝（通过序列化）。这会慢一些，但是安全，因此默认是 

false。 



# 3.MyBatis缓存查询的效率

先查询二级缓存，因为二级缓存中可能会有其他程序已经查出来的数据，可以拿来直接使用。 

如果二级缓存没有命中，再查询一级缓存 

如果一级缓存也没有命中，则查询数据库 

SqlSession关闭之后，一级缓存中的数据会写入二级缓存

# 4.整合第三方EHcache

## 1.添加依赖

```xml
<!-- Mybatis EHCache整合包 --> 
<dependency>
  <groupId>org.mybatis.caches</groupId>
  <artifactId>mybatis-ehcache</artifactId> 
  <version>1.2.1</version> 
</dependency>
<!-- slf4j日志门面的一个具体实现 --> 
<dependency>
  <groupId>ch.qos.logback</groupId> 
  <artifactId>logback-classic</artifactId> 
  <version>1.2.3</version> 
</dependency>
```

## 2.各jar包功能

![img](https://cdn.nlark.com/yuque/0/2022/png/25696041/1653221872424-41cef48b-b63a-4f5e-a15d-487ca6651c3f.png)

## **创建EHCache的配置文件ehcache.xml** 

```html
<?xml version="1.0" encoding="utf-8" ?> 
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="../config/ehcache.xsd"> <!-- 磁盘保存路径 --> <diskStore path="D:\atguigu\ehcache"/> <defaultCache maxElementsInMemory="1000" maxElementsOnDisk="10000000" eternal="false" overflowToDisk="true" timeToIdleSeconds="120" timeToLiveSeconds="120" diskExpiryThreadIntervalSeconds="120" memoryStoreEvictionPolicy="LRU"> 
  </defaultCache> 
</ehcache>
```

## 设置二级缓存类型

```html
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```

## **加入logback日志**

存在SLF4J时，作为简易日志的log4j将失效，此时我们需要借助SLF4J的具体实现logback来打印日志。 创建logback的配置文件logback.xml

```html
<?xml version="1.0" encoding="UTF-8"?> 
<configuration debug="true"> 
  <!-- 指定日志输出的位置 --> 
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender"> 
    <encoder> 
      <!-- 日志输出的格式 --> <!-- 按照顺序分别是：时间、日志级别、线程名称、打印日志的类、日志主体内容、换行 - -> <pattern>[%d{HH:mm:ss.SSS}] [%-5level] [%thread] [%logger] [%msg]%n</pattern> </encoder> </appender> <!-- 设置全局日志级别。日志级别按顺序分别是：DEBUG、INFO、WARN、ERROR --> <!-- 指定任何一个日志级别都只打印当前级别和后面级别的日志。 -->
      <root level="DEBUG"> <!-- 指定打印日志的appender，这里通过“STDOUT”引用了前面配置的appender --> 
      <appender-ref ref="STDOUT" /> </root> <!-- 根据特殊需求指定局部日志级别 -->
  <logger name="com.atguigu.crowd.mapper" level="DEBUG"/> 
  </configuration>
```

![img](https://cdn.nlark.com/yuque/0/2022/png/25696041/1653222087313-da917e3c-594c-4fc6-8ca7-14599b2ab1a7.png)

![img](https://cdn.nlark.com/yuque/0/2022/png/25696041/1653222107874-c86d0678-da10-48b6-b04f-8e108ea8e7c5.png)

