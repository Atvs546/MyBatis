mapper.xml文件

文件全限定名称:target/class目录下的相对目录没有后缀名：例如domain.peoplein

resultType指定转化成的java对象的值也是文件全限定名称:target/class目录下的相对目录没有后缀名如：com.domain.student

sql映射文件是用来编写要执行的sql语句的,一般与dao的接口在同一目录下，

```xml
<!--指定约束文件
      <?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
      PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
      "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    mybatis-3-mapper.dtd是约束文件的名称，扩展名是dtd的
      约束文件的作用:限制检查在当前文件中出现的标签，属性必须符合MyBatis的要求的
-->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--mapper是当前文件的根标签，必须的
           namespace是命名空间，唯一值，可以是自定义的字符串，要求使用dao接口的全限定的名称
           在mapper标签中可以使用特定的标签，表示数据库的特定操作
            <select>表示执行查询
            <update>表示更新数据库的操作，在<update>中编写update语句
            <insert>表示插入，<insert>中编写insert语句
            <delete>表示删除,<delete>中编写delete语句-->
<mapper namespace="org.mybatis.example.BlogMapper">
  <!--seletc表示执行查询操作
    id:是你要执行的sql语句的唯一标识，可以自定义，但是要求使用接口中的方法名称
    resultType:表示sql语句执行后得到的ResultSet，遍历ResultSet得到的java对象的类型，值写的是类型的全限定名称-->
        -->
    <select id="selectstudent" resultType="com.domain.student">
      select * from student where id = #{id}
    </select>
</mapper>
        <!--sql映射文件:写sql语句的，MyBatis会执行这些sql语句-->
```