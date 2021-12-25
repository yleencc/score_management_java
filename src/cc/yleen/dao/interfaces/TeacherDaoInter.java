package cc.yleen.dao.interfaces;

import cc.yleen.model.Grade;
import cc.yleen.model.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TeacherDaoInter {
    // 查看教师信息
    Teacher getTeacherInfo(String tno) throws SQLException;

    // 查看该课程的所有学生的成绩
    ArrayList<Grade> queryCourseAllGrade(String cno) throws SQLException;

    // 修改学生成绩
    int updateStudentGrade(String sno, String cno, float grade) throws SQLException;

    // 修改个人信息
    int updateTeacherInfo(Teacher teacher) throws SQLException;
}
