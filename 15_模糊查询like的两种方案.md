# 1.第一种模糊查询方式(在java代码中指定like的内容)



 1.dao接口中的方法：

List<List>  selectrlikeone(String name);

2.在mapper文件中,用占位符表示like的值

```xml
<select id="selectone" resultType="com.people">
    select  *from  people where name  like  #{name}
</select>
```

 3.在执行sql语句时准备like的内容,在把准备的内容传入到模糊查询的方法的形参中

```java
Stirng  name="%liu%";
List<people>  peoplelist=po.selectlike(name);
```

# xxxxxxxxxx     <dataSource type="POOLED">                <property name="driver" value="${jdbc.driver}"/>                <property name="url" value="${jdbc.url}"/>                <property name="username" value="${jdbc.username}"/>                <property name="password" value="${jdbc.password}"/>            </dataSource>xml

1.接口中的方法

List<List>  selectrlikeone(String name);

2.在mapper文件中用"%" #{name} "%"拼接出like的值

```java
<select id="selectone" resultType="com.people">
    select  *from  people where name  like  "%" #{name} "%"
</select>
```

3.在执行sql语句前给#{name}占位符赋值

```java
String name="liu";
List<people>  peoplelist=po.selectlike(name);
```