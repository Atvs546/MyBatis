1.下载MyBatis

[MyBatis下载地址](https://github.com/mybatis/mybatis-3/releases)

2.mybatis-3.5.9.jar是MyBatis的核心jar包

3.[MyBatis的中文文档](https://mybatis.net.cn/index.html)

# 1.MyBatis的使用步骤

重点:sql映射文件和主目录配置文件一定要编译进target/calssess目录中，在resources目录必须 是资源文件夹，且pom.xml文件中要有resource标签，如果还是没有，clean,在compile,最后构建重新编译工程 如果没有可以自己复制对应的文件到指定的位置，



  sql映射文件：指定接口的路径的值是接口在target/classes目录下相对路径并将"/"改为"."没有后缀

​                         指定sql执行语句结果的值转换成的java对象的类在target/classes的相对路径没有后缀

1.新建数据库的表

2.Maven添加MyBatis依赖,和Mysql的驱动的坐标

```xml
 <!--添加依赖-->
<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.2</version>
      <scope>test</scope>
    </dependency>
    <!--mybatis依赖-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.9</version>
    </dependency>
    <!--mysql驱动,注意版本-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.28</version>
    </dependency>
  </dependencies>

<!--在pom.xml文件中一定要有编译插件，如果没有自己配,另外要指明sql映射文件和主目录文件的位置这是重点
-->

  <build>
    <!--配置编译插件-->
    <plugins>
      <plugin>
        <!--插件的坐标-->
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId><!--插件的名称-->
        <version>3.1</version><!--插件的版本-->
        <!--配置插件的信息-->
        <configuration>
          <!--告诉maven使用的jdk版本编译的-->
          <source>1.8</source>
          <!--程序应该运行在jdk1.8版本上的版本-->
          <target>1.8</target>
        </configuration>
      </plugin>
    </plugins>
    <!--把sql映射文件拷贝到target/classes目录中-->
    <resources>
      <resource>
        <directory>src/main/java</directory><!--所在的目录-->
        <includes><!--包括目录下的.properties,.xml 文件都会扫描到-->
          <include>**/*.properties</include>
          <include>**/*.xml</include>
        </includes>
        <filtering>false</filtering>
      </resource>
    </resources>

  </build>
```

3.创建实体类保存表中的一行数据的实体类，并创建属性的get和set方法，并生成toString方法

```java
//推荐和表名一样。容易记忆
public class Student {
    //定义属性， 目前要求是属性名和列名一样。
    private Integer id;
    private String name;
    private String email;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
```

4.创建持久层的dao接口,定义操作数据库的方法

```java
//接口操作student表
public interface StudentDao {

    //查询student表的所有的数据
    public List<Student> selectStudents();
}
```

5.创建一个MyBatis使用的配置文件，叫做sql的映射文件用于写sql语句,一般一个表一个sql映射文件(mapper文件)，这个文件是xml文件，写在dao连接数据库的接口同一个目录中，文件名和接口名相同如：studentdao.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace的值为接口在target/calsses目录下的相对目录-->
<mapper namespace="com.bjpowernode.dao.StudentDao">
    <!--
       select:表示查询操作。
       id: 你要执行的sql语法的唯一标识， mybatis会使用这个id的值来找到要执行的sql语句
           可以自定义，但是要求你使用接口中的方法名称。

       resultType:表示结果类型的， 是sql语句执行后得到ResultSet,遍历这个ResultSet得到java对象的类型。值是这个类在target/classes目录下的相对路径
        
    -->
    <select id="selectStudents" resultType="com.bjpowernode.domain.Student" >
        select id,name,email,age from student order by id
    </select>

    <!--插入操作-->
    <insert id="insertStudent">
        insert into student values(#{id},#{name},#{email},#{age})
    </insert>
</mapper>
<!--
  sql映射文件（sql mapper）： 写sql语句的， mybatis会执行这些sql
  1.指定约束文件
     <!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

    mybatis-3-mapper.dtd是约束文件的名称， 扩展名是dtd的。
  2.约束文件作用：  限制，检查在当前文件中出现的标签，属性必须符合mybatis的要求。

  3.mapper 是当前文件的根标签，必须的。
    namespace：叫做命名空间，唯一值的， 可以是自定义的字符串。
               要求你使用dao接口的全限定名称。

  4.在当前文件中，可以使用特定的标签，表示数据库的特定操作。
   <select>:表示执行查询，select语句
   <update>:表示更新数据库的操作， 就是在<update>标签中 写的是update sql语句
   <insert>:表示插入， 放的是insert语句
   <delete>:表示删除， 执行的delete语句
-->
```



6.创建MyBatis的主配置文件：

​    一个项目就是一个主配置文件

​    主配置文件提供数据库的连接信息和sql映射文件的位置信息

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--settings：控制mybatis全局行为-->
    <settings>
        <!--设置mybatis输出日志-->
        <setting name="logImpl" value="STDOUT_LOGGING" />
    </settings>

    <!--环境配置： 数据库的连接信息
        default:必须和某个environment的id值一样。
        告诉mybatis使用哪个数据库的连接信息。也就是访问哪个数据库
    -->
    <environments default="mydev">
        <!-- environment : 一个数据库信息的配置， 环境
             id:一个唯一值，自定义，表示环境的名称。
        -->
        <environment id="mydev">
            <!--
               transactionManager ：mybatis的事务类型
                   type: JDBC(表示使用jdbc中的Connection对象的commit，rollback做事务处理)
            -->
            <transactionManager type="JDBC"/>
            <!--
               dataSource:表示数据源，连接数据库的
                  type：表示数据源的类型， POOLED表示使用连接池
            -->
            <dataSource type="POOLED">
                <!--
                   driver, user, username, password 是固定的，不能自定义。
                -->
                <!--数据库的驱动类名-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <!--连接数据库的url字符串-->
                <property name="url" value="jdbc:mysql://localhost:3306/liu"/>
                <!--访问数据库的用户名-->
                <property name="username" value="root"/>
                <!--密码-->
                <property name="password" value="123456"/>
            </dataSource>
        </environment>


        <!--表示线上的数据库，是项目真实使用的库-->
        <environment id="online">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/liu"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>

    <!-- sql mapper(sql映射文件)的位置-->
    <mappers>
        <!--一个mapper标签指定一个文件的位置。
           从类路径开始的路径信息。  target/clasess(类路径)的相对路径
        -->
        <mapper resource="com/bjpowernode/dao/StudentDao.xml"/>
        <!--<mapper resource="com/bjpowernode/dao/SchoolDao.xml" />-->
    </mappers>
</configuration>
<!--
   mybatis的主配置文件： 主要定义了数据库的配置信息， sql映射文件的位置

   1. 约束文件
   <!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

    mybatis-3-config.dtd：约束文件的名称

  2. configuration 根标签。
-->
```

7.创建使用MyBatis的类：使用mybatis的对象，SqlSession,通过他的方法执行sql语句

Resources类导入的是*import* org.apache.ibatis.io.Resources;这个类

```java
public class MyApp {

    public static void main(String[] args) throws IOException {
        //访问mybatis读取student数据
        //1.定义mybatis主配置文件的名称, 从类路径的根开始（target/clasess）
        String config="mybatis.xml";
        //2.读取这个config表示的文件
        InputStream in = Resources.getResourceAsStream(config);
        //3.创建了SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder  = new SqlSessionFactoryBuilder();
        //4.创建SqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        //5.获取SqlSession对象，从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //6.【重要】指定要执行的sql语句的标识。  sql映射文件中的namespace + "." + 标签的id值
        String sqlId = "com.bjpowernode.dao.StudentDao.selectStudents";
        //7. 重要】执行sql语句，通过sqlId找到语句
        List<Student> studentList = sqlSession.selectList(sqlId);
        //8.输出结果
        //studentList.forEach( stu -> System.out.println(stu));
        for(Student stu : studentList){
            System.out.println("查询的学生="+stu);
        }
        //9.关闭SqlSession对象
        sqlSession.close();
    }
}
```

# 1.插入操作

1.在接口中增加插入的方法

```java
public interface peoplein {
    /*
    插入的方法   
     参数：表示要插入的数据库的数据  
     返回值:表示执行insert操作后影响了数据库多少行的数据 
    */
     public  int  insert(people p1);
}
```

2.在sql映射文件中添加insert标签，

```xml
    <!--插入操作
        用#{属性名}表示插入的只是类中的属性的值-->
    <insert id="insertpeople">
        insert  into  people values (#{id},#{name},#{sex},#{age})
    </insert>
```

3.给属性赋值也就是确定要插入的值,并且通过SqlSession执行sql语句，反馈执行的结果

```java
//访问mybatis读取student数据
        //1.定义mybatis主配置文件的名称, 从类路径的根开始（target/clasess）
        String config="mybatis.xml";
        //2.读取这个config表示的文件
        InputStream in = Resources.getResourceAsStream(config);
        //3.创建了SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder  = new SqlSessionFactoryBuilder();
        //4.创建SqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        //5.获取SqlSession对象，从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //6.【重要】指定要执行的sql语句的标识。  sql映射文件中的namespace + "." + 插入标签的id值
        String sqlId = "domain.peoplein.class.insertpeople";
       //引入类的对象,通过对象的set方法给属性赋值,也就是要插入的值
        people p2=new people();
        p2.setId(3);
        p2.setName("来则毅");
        p2.setSex("男");
        p2.setAge(19);
        /*得到执行插入操作影响数据库的表的行数，
             形参1:要执行的sql语句的id
             形参2:要插入的数据的类对象
             如果执行的是其他的DML语句，调用的SqlSession类的方法也不同
             */
        int num= sqlSession.insert(sqlId,p2);
     //mybatis默认不是自动提交事务的，所以在insert,update,delete之后要手动提交事务
        sqlSession.commit();
        System.out.println("执行insert的结果"+num);
        //9.关闭SqlSession对象
        sqlSession.close();
```