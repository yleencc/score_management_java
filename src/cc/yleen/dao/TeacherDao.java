package cc.yleen.dao;

import cc.yleen.dao.interfaces.TeacherDaoInter;
import cc.yleen.model.Grade;
import cc.yleen.model.Teacher;
import cc.yleen.utils.MysqlConnect;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDao implements TeacherDaoInter {
    @Override
    public Teacher getTeacherInfo(String tno) throws SQLException {
        String str = ("SELECT teacher.*, course.Cname FROM teacher, course WHERE Tno=? AND course.Cno = teacher.Cno order by Tno");
        PreparedStatement statement = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        statement.setString(1, tno);
        ResultSet result = statement.executeQuery();
        Teacher teacher = null;
        if (result.next()) {
            String tno_ = result.getString("Tno");
            String name = result.getString("Tname");
            String sex = result.getString("Sex");
            Date birthday = result.getDate("Birthday");
            String cno = result.getString("Cno");
            String course = result.getString("Cname");
            teacher = new Teacher(tno_, name, sex, birthday, cno, course);
        }
        return teacher;
    }

    @Override
    public ArrayList<Grade> queryCourseAllGrade(String cno) throws SQLException {
        String str = ("SELECT * FROM student, course, sc WHERE student.Sno = sc.Sno AND course.Cno = ? AND sc.Cno = ? order by student.Sno");
        PreparedStatement pstmt = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        pstmt.setString(1, cno);
        pstmt.setString(2, cno);
        ResultSet result = pstmt.executeQuery();
        ArrayList<Grade> grades = new ArrayList<>();
        while (result.next()) {
            String sno = result.getString("Sno");
            String sname = result.getString("Sname");
            String cno_ = result.getString("Cno");
            String cname = result.getString("Cname");
            float grade = result.getFloat("Grade");
            float credit = result.getFloat("Credit");
            grades.add(new Grade(sno, sname, cno_, cname, grade, credit));
        }
        return grades;
    }

    @Override
    public int updateStudentGrade(String sno, String cno, float grade) throws SQLException {
        String str = "UPDATE sc SET Grade=? WHERE Sno=? AND Cno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ppst.setFloat(1, grade);
        ppst.setString(2, sno);
        ppst.setString(3, cno);
        return ppst.executeUpdate();
    }

    @Override
    public int updateTeacherInfo(Teacher teacher) throws SQLException {
        String str = "UPDATE teacher SET Tname=?,Sex=?,Birthday=?,Cno=? WHERE Tno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ppst.setString(1, teacher.getName());
        ppst.setString(2, teacher.getSex());
        ppst.setDate(3, teacher.getBirthday());
        ppst.setString(4, teacher.getCno());
        ppst.setString(5, teacher.getTno());
        return ppst.executeUpdate();
    }
}
