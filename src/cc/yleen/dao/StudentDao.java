package cc.yleen.dao;

import cc.yleen.dao.interfaces.StudentDaoInter;
import cc.yleen.model.Grade;
import cc.yleen.model.Student;
import cc.yleen.utils.MysqlConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class StudentDao implements StudentDaoInter {
    @Override
    public Student getStudentInfo(String sno) throws SQLException {
        String str = ("SELECT * FROM student WHERE Sno=?");
        PreparedStatement statement = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        statement.setString(1, sno);
        ResultSet result = statement.executeQuery();
        Student student = null;
        if (result.next()) {
            String sno_ = result.getString("Sno");
            String name = result.getString("Sname");
            String sex = result.getString("Sex");
            Date birthday = result.getDate("Birthday");
            String school = result.getString("School");
            String major = result.getString("Major");
            String class_ = result.getString("Class");
            student = new Student(sno_, name, sex, birthday, school, major, class_);
        }
        return student;
    }

    @Override
    public ArrayList<Grade> queryStudentAllGrade(String sno) throws SQLException {
        String str = ("SELECT * FROM student, course, sc WHERE student.Sno = ? AND sc.Sno = ? AND course.Cno = sc.Cno");
        PreparedStatement pstmt = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        pstmt.setString(1, sno);
        pstmt.setString(2, sno);
        ResultSet result = pstmt.executeQuery();
        ArrayList<Grade> grades = new ArrayList<>();
        while (result.next()) {
            String sname = result.getString("Sname");
            String cno = result.getString("Cno");
            String cname = result.getString("Cname");
            float grade = result.getFloat("Grade");
            float credit = result.getFloat("Credit");
            grades.add(new Grade(sno, sname, cno, cname, grade, credit));
        }
        return grades;
    }

    @Override
    public int updateStudentInfo(Student student) throws SQLException {
        String str = "UPDATE student SET Sname=?,Sex=?,Birthday=?,School=?,Major=?,Class=? WHERE Sno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ppst.setString(1, student.getName());
        ppst.setString(2, student.getSex());
        ppst.setDate(3, student.getBirthday());
        ppst.setString(4, student.getSchool());
        ppst.setString(5, student.getMajor());
        ppst.setString(6, student.getClassName());
        ppst.setString(7, student.getSno());
        return ppst.executeUpdate();
    }
}
