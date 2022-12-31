package com.model;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class myBatisUtil {//执行sql语句的工具类
    private static SqlSessionFactory factory = null;

    //SqlSessionFactory对象只需要一个就行，所以在静态代码块中创建SqlSessionFactory对象
    static {
        //获取主配置文件,要和项目中的主配置文件target/classes目录下的相对路径保持一致
        String config = "mybatis.xml";
        try {
            //获取SqlSessionFactory对象
            InputStream in = Resources.getResourceAsStream(config);
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession的方法
    public static SqlSession getSqlSessiono() {
        //初始化SqlSession对象
        SqlSession sqlSession = null;
        if (factory != null) {
            //设置非自动提交事务添加形参true时，表示自动提交事务
            sqlSession = factory.openSession();
        }
        return sqlSession;
    }
}
