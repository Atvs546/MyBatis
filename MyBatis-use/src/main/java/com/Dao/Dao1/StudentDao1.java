package com.Dao.Dao1;

import com.model.Student;

import java.util.List;

public interface StudentDao1 {
    //查询student表的所有的数据
    public List<Student> selectStudents();
}
