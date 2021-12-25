package cc.yleen.dao;

import cc.yleen.dao.interfaces.AdminDaoInter;
import cc.yleen.model.Course;
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
    public ArrayList<Course> queryAllCourse() throws SQLException {
        String str = ("SELECT * FROM course");
        PreparedStatement pstmt = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ResultSet result = pstmt.executeQuery();
        ArrayList<Course> courses = new ArrayList<>();
        while (result.next()) {
            String cno = result.getString("Cno");
            String cname = result.getString("Cname");
            float credit = result.getFloat("Credit");
            courses.add(new Course(cno, cname, credit));
        }
        return courses;
    }

    @Override
    public int addCourse(Course course) throws SQLException {
        return 0;
    }

    @Override
    public int addTeacher(Course course) throws SQLException {
        return 0;
    }

    @Override
    public int addStudent(Course course) throws SQLException {
        return 0;
    }

    @Override
    public int updateCourse(Course course) throws SQLException {
        String str = "UPDATE course SET Cno=?,Cname=?,Credit=? WHERE Cno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ppst.setString(1, course.getCno());
        ppst.setString(2, course.getName());
        ppst.setFloat(3, course.getCredit());
        ppst.setString(4, course.getCno());
        return ppst.executeUpdate();
    }

    @Override
    public int updateTeacher(Course course) throws SQLException {
        return 0;
    }

    @Override
    public int updateStudent(Course course) throws SQLException {
        return 0;
    }

    @Override
    public int removeCourse(String cno) throws SQLException {
        String str_rm = "DELETE FROM course WHERE cno=?";
        PreparedStatement ppst = (PreparedStatement)  MysqlConnect.getInstance().prepareStatement(str_rm);
        ppst.setString(1, cno);
        return ppst.executeUpdate();
    }

    @Override
    public int removeTeacher(String tno) throws SQLException {
        return 0;
    }

    @Override
    public int removeStudent(String sno) throws SQLException {
        return 0;
    }
}
