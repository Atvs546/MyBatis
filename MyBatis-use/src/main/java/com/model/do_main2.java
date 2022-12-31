package com.model;
import com.Dao.Dao2.StudentDao2;
import com.Dao.Dao2.StudentDao2Impl;

import  java.util.List;

public class do_main2 {
    public static void main(String[] args) {
        StudentDao2 studentDao2=new StudentDao2Impl();
        List<Student> p1=studentDao2.selectstudents2();
        for(Student stu : p1){
            System.out.println("查询的学生="+stu);
        }

    }
}
