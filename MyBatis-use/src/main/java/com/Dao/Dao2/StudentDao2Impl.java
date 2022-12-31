package com.Dao.Dao2;

import com.model.Student;
import com.model.myBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentDao2Impl implements  StudentDao2{//Dao接口的实现类，在接口的实现方法中通过sql语句的工具类执行sql语句
    @Override
    public List<Student> selectstudents2() {
        SqlSession sqlSession= myBatisUtil.getSqlSessiono();
        String config="com.Dao.Dao2.StudentDao2.selectstudents2";//获取映射文件的命名空间
        List<Student>  p1=sqlSession.selectList(config);
        return  p1;
    }
}
