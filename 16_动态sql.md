# 1.动态sql的概念

动态sql：sql语句的内容是变化，我们可以根据条件获取不同的sql语句，主要是where部分发生变化  





## 1.mybatis提供的标签:

<if>,<where>,<foreach>

# 2. if



语法：

<if>是判断条件的

语法:

<if  test="判断java对象的属性值">  

​     部分sql语句

</if>



1.在Dao接口中，以什么类作为方法的形参在mapp文件的if标签中的test属性就是以该类中的属性作为判断条件

```java
List<people>  selectsql(people p)
```



2.在mapper文件中:根据if标签中的条件判断是否加上部分sql语句

```java
<select id="selectsql" resultType="com.people">
    select id,name,age from  people  where 1=1
<if test="name!=null and  name1=''" >
  and  name=#{name}
</if>
<if  test="age>0">
    and age>#{age}
</if>
</select>
```

如果name属性有值加上查询条件name=#{name},如果age>0再加上age>#{age}

3.在执行sql语句之前，要调用类的对象给if标签中的进行条件判断赋值



在条件判断之后连接的sql语句一定要满足sql语句的书写规范

# 3.where

<where>用来包含多个<if>的,

​     多个<if>有一个成立的话 ，<where>标签会自动添加一个where关键字，并去掉if多余的and,or等 

​      多个if如果都不成立,<where>标签不会添加where关键字和if里的部分sql语句



语法：<where><if><if>....</where>

## 1.1.<where>标签的使用

```java
<select id="selectsql" resultType="com.people">
    select id,name,age from  people  
 <where>
     <if test="name!=null and  name1=''" >
            name=#{name}
    </if>
     <if  test="age>0">
            age>#{age}
     </if>
 </where>
```

# 4.foreache



<foreach>循环java中的数组,List集合的，主要用在sql语句的in语句中



例如:找出id为1001,1002,1003的三个学生

select  *from  people where id in(1001,1002,1003)





## 1.foreach标签中的属性介绍

*collection：**表示接口中方法参数的类型	(全限定名称)如果是数组值为array,如果是list集* 

 *item:*  *自定义的，表示数组和集合成员的变量*

 *open：**循环开始时的字符*

*close：**循环结束时的字符*

*separato：**集合成员之间的分隔符*



## 2.foreach使用方法一



1.在dao接口中List<people>  selectbyid(List<Integer> idlist)







2.在mapper文件中，执行的是select *from people where id in(1,2,3）

所以collection="list" open="("    close=")"   separato=","

```java
<select id="selectforeach"  resultType="com.people">
    select  *from people  where id  in
<foreach collection="list" item="myids"  open="(" close=")" separator=",">
        #{myids}
</foreach>
</select>
```

3.在执行sql语句前要给List集合里要循环遍历的值

*List*<Integer > id=*new* ArrayList<>();
id.add(1);
id.add(2);
id.add(3);

## 1.foreach使用方法二(复杂，不常用)

1.在dao接口中以装有类的集合作为形参

*List*<people> selectforeach2(*List*<people> pe);

2.在mapper文件中

```xml
<select id="selectforeach2"  resultType="com.people">
    select  *from people  where id  in
    <foreach collection="list" item="peo"  open="(" close=")" separator=",">
        #{peo.id}
    </foreach>
    </select>
```

3.在执行sql语句之前，创建几个类的对象，调用他们的set方法赋值，在把对象装入到List集合中,最后把List集合作为形参传入到方法中

# 5.代码片段

  当一条sql语句或得到多次使用时，为了减少代码量可以将这个sql语句编辑成代码片段，在其他sql语句的标签中通过<include>标签的id属性来引用指定的代码片段





注：<if><where><foreach>等对sql语句进行动态操作的标签不能写在sql代码片段中,只能在引用代码片段的地方进行补充



1.创建代码片段

创建的代码片段甚至可以不是一个完整的sql语句，也可以得到复用

```xml
   <sql id="selectpian">
        select  *from people  where   id=#{id}
    </sql>
```

2.引用代码片段(通过include标签的refid属性指向代码片段的id

```xml
   <select id="ko"  resultType="com.people" >
        <include refid="selectpian"/>
    </select>
```