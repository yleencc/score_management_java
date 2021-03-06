package cc.yleen.dao.interfaces;

import cc.yleen.model.Grade;
import cc.yleen.model.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentDaoInter {
    Student getStudentInfo(String sno) throws SQLException;

    // 查看某个学生的所有课程的成绩
    ArrayList<Grade> queryStudentAllGrade(String sno) throws SQLException;

    // 修改个人信息
    int updateStudentInfo(Student student) throws SQLException;
}
