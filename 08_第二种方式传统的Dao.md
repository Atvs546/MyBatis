1.编写dao接口并且创建sql语句的方法，

```java
public interface peoplein {
    public List<people>  selectpeople();
     public  int  insertpeople(people p1);
}
```





在执行sql语句的类中继承该接口，并重写接口中的方法，在方法中

使用SqlSession类中的方法执行sql语句

```java
public class myappp3 implements peoplein{
    @Override
    public List<people> selectpeople() {
        SqlSession sqlSession=mybatisutil.getSqlSessiono();
        String config="domain.peoplein.class.sel ctBlog";
       List<people>  p1=sqlSession.selectList(config);
        return  p1;
    }

    @Override
    public int insertpeople(people p1) {
        people p2=new people();
        SqlSession sqlSession=mybatisutil.getSqlSessiono();
        String config="domain.peoplein.class.sel insertpeople";
        int a=sqlSession.insert(config,p2);
        return  a;
    }
}
```



在主函数中创建接口实现类的对象，并调用实现类中的重写的执行sql语句的方法

```java
public class main {
   public static void main(String[] args) {
      people p2=new people();
      peoplein  po=new myappp3();
     List<people> peoplelist= po.selectpeople();
     for(people  pe:peoplelist){
        System.out.println(pe);
     }
     int a=po.insertpeople(p2);
      System.out.println(a);
   }
}
```









这种方式更加贴近开发