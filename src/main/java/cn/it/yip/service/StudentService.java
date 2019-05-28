package cn.it.yip.service;

import cn.it.yip.beans.factory.annotation.Autowired;
import cn.it.yip.dao.StudentDao;
import cn.it.yip.dao.TeacherDao;
import cn.it.yip.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-19 15:21
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;
    private String name;
    private int age;

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public TeacherDao getTeacherDao() {
        return teacherDao;
    }

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public void go() {
        System.out.println("上学");
    }
}
