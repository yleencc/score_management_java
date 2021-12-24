package cc.yleen.dao;

import cc.yleen.dao.interfaces.StudentDaoInter;
import cc.yleen.model.Grade;
import cc.yleen.model.Student;
import cc.yleen.utils.MysqlConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class StudentDao implements StudentDaoInter {
    @Override
    public ArrayList<Student> getAllStudents() throws SQLException {
        return null;
    }

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
    public Grade queryStudentGrade(String sno, String cno) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Grade> queryStudentAllGrade(String sno) throws SQLException {
        return null;
    }

    @Override
    public void updateStudentInfo(String sno) throws SQLException {

    }
}
