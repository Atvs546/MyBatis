当属性名和列名保持一致时使用resultType,当属性名和列名不一样添加resultMap指定列名和属性的对应关系，在sql语句的标签中只能使用resultMap，所以resultType和resultMap不能一起使用，只能根据情况二选一



在创建类时最好使属性名和列名保持一致，否则在指定这两者对应关系时容易出错导致赋值混乱



# 1.第一种指定列名和属性对应关系的方法

```java
<!--定义结果集映射，通过该映射来指定表的列与类的属性的映射关系-->
<!--id:唯一表示; type:关联到实体类-->
    <resultMap id="studentResultMap" type="Student2">
        <!--id:配置主键与实体类中属性的映射关系-->
        <id column="ID" property="id" jdbcType="DECIMAL"></id>
        <!--result:配置非主键列-->
        <result column="name" property="studentName"></result>
        <result column="classid" property="classid"></result>
        <result column="version" property="version"></result>
    </resultMap>
    <!--resultMap：指定返回的结果集映射的ID值-->
    <select id="selectByIdMappingWithResultMap" resultMap="studentResultMap">
        select * from h_student where id=#{id}
    </select>
```

1.自定义列值赋值给哪个属性

2.当你的列名和属性名不一样时,一定使用resultMap

例如：在类中属性名为myid，而sql语句的是#{id}，此时就需要resultMap指定



使用resultMap

1.先定义resultMap

2.在select标签，使用resultMap来引用1定义的 







在mapper文件中:在sql语句的标签中不能再用resultType指定类，因为类中的属性和列名不同，指向类无法找到对应的属性

，只能通过resultMap，指定列名和属性的对应关系

<!--定义结果集映射，通过该映射来指定表的列与类的属性的映射关系-->

<!--id:唯一表示; type:关联到实体类-->

​    <resultMap id="studentResultMap" type="Student2">

​        <!--id:配置主键与实体类中属性的映射关系-->

​        <id column="ID" property="id" jdbcType="DECIMAL"></id>

​        <!--result:配置非主键列-->

​        <result column="name" property="studentName"></result>

​        <result column="classid" property="classid"></result>

​        <result column="version" property="version"></result>

​    </resultMap>

​    <!--resultMap：指定返回的结果集映射的ID值-->

​    <select id="selectByIdMappingWithResultMap" resultMap="studentResultMap">

​        select * from h_student where id=#{id}

​    </select>

# 2.第二种方法指定属性和列名的对应关系

当类中的属性和列名不一样时，可以给列名起一个与属性名一致的别名，指定他们的对应关系

属性名为myid，列名为id时

select  *from  people  where  id as myid=#{myid}



select  id as myid  where name='liu'