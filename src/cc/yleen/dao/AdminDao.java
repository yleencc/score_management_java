package cc.yleen.dao;

import cc.yleen.dao.interfaces.AdminDaoInter;
import cc.yleen.model.Grade;
import cc.yleen.model.Student;
import cc.yleen.model.Teacher;
import cc.yleen.utils.MysqlConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDao implements AdminDaoInter {
    @Override
    public ArrayList<Student> getAllStudents() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Teacher> getAllTeachers() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Grade> queryAllGrade() throws SQLException {
        String str = ("SELECT * FROM student, course, sc WHERE student.Sno = sc.Sno AND course.Cno = sc.Cno");
        PreparedStatement pstmt = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ResultSet result = pstmt.executeQuery();
        ArrayList<Grade> grades = new ArrayList<>();
        while (result.next()) {
            String sno = result.getString("Sno");
            String sname = result.getString("Sname");
            String cno = result.getString("Cno");
            String cname = result.getString("Cname");
            float grade = result.getFloat("Grade");
            float credit = result.getFloat("Credit");
            grades.add(new Grade(sno, sname, cno, cname, grade, credit));
        }
        return grades;
    }
}
