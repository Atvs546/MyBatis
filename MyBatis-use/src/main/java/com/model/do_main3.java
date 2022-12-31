package com.model;

import com.Dao.Dao3.StudentDao3;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class do_main3 {
    public static void main(String[] args) {
        //通过SqlSession中的getMapper方法得到peoplein接口的实现类对象po
        SqlSession sqlSession= myBatisUtil.getSqlSessiono();
        StudentDao3 po=sqlSession.getMapper(StudentDao3.class);//获取接口对象
        //调用方法执行sql语句
        List<Student> peoplist=po.selectstudents3();
    }
}
