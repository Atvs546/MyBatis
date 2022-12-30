# 1.准备工作



1.在Maven中添加pageHelper的依赖

```xml
    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>5.3.0</version>
    </dependency>
```

2.在主配置文件的<envirements>标签之前加入plugin配置

```xml
  <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
    </plugins>
```

# 2.通过PageHelper插件实现分页的步骤

1.在dao接口中创建方法

*List*<people> selectStudents();

2.在,mapper文件中执行排序查询的sql语句

```xml
   <select id="selectall"   resultType="com.people">
        select  *from people order by id
    </select>
```

3.执行sql语句

```java
 SqlSession sqlSession=mybatisutil.getSqlSessiono();
        String sqlidm="domain.peoplein.selectall";
        //分页查询，第一个参数是第几页从1开始，第二个是一页中有几条数据
        PageHelper.startPage(1,2);
       List<people> pb= sqlSession.selectList(sqlidm);
       sqlSession.close();
       return  pb;
```