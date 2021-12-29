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
import java.sql.Date;

public class AdminDao implements AdminDaoInter {
    @Override
    public ArrayList<Student> getAllStudents() throws SQLException {
        String str = ("SELECT * FROM student");
        PreparedStatement pstmt = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ResultSet result = pstmt.executeQuery();
        ArrayList<Student> students = new ArrayList<>();
        while (result.next()) {
            String sno = result.getString("Sno");
            String sname = result.getString("Sname");
            String sex = result.getString("Sex");
            Date birthday = result.getDate("Birthday");
            String school = result.getString("School");
            String major = result.getString("Major");
            String class_ = result.getString("Class");
            students.add(new Student(sno, sname, sex, birthday, school, major, class_));
        }
        return students;
    }

    @Override
    public ArrayList<Teacher> getAllTeachers() throws SQLException {
        String str = ("SELECT teacher.*, course.Cname FROM teacher, course where teacher.Cno = course.Cno");
        PreparedStatement pstmt = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ResultSet result = pstmt.executeQuery();
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (result.next()) {
            String tno = result.getString("Tno");
            String tName = result.getString("Tname");
            Date birthday = result.getDate("Birthday");
            String sex = result.getString("Sex");
            String cno = result.getString("Cno");
            String cName = result.getString("Cname");
            teachers.add(new Teacher(tno, tName, sex, birthday, cno, cName));
        }
        return teachers;
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
        String str_add = "INSERT INTO course VALUES(?,?,?)";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_add);
        ppst.setString(1, course.getCno());
        ppst.setString(2, course.getName());
        ppst.setFloat(3, course.getCredit());
        return ppst.executeUpdate();
    }

    @Override
    public int addTeacher(Teacher teacher) throws SQLException {
        String str_add = "INSERT INTO teacher VALUES(?,?,?,?,?)";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_add);
        ppst.setString(1, teacher.getTno());
        ppst.setString(2, teacher.getName());
        ppst.setDate(3, teacher.getBirthday());
        ppst.setString(4, teacher.getSex());
        ppst.setString(5, teacher.getCno());
        return ppst.executeUpdate();
    }

    @Override
    public int addStudent(Student student) throws SQLException {
        String str_add = "INSERT INTO student VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_add);
        ppst.setString(1, student.getSno());
        ppst.setString(2, student.getName());
        ppst.setString(3, student.getSex());
        ppst.setDate(4, student.getBirthday());
        ppst.setString(5, student.getSchool());
        ppst.setString(6, student.getMajor());
        ppst.setString(7, student.getClassName());
        return ppst.executeUpdate();
    }

    @Override
    public int updateCourse(Course course) throws SQLException {
        String str = "UPDATE course SET Cname=?,Credit=? WHERE Cno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ppst.setString(1, course.getName());
        ppst.setFloat(2, course.getCredit());
        ppst.setString(3, course.getCno());
        return ppst.executeUpdate();
    }

    @Override
    public int updateTeacher(Teacher teacher) throws SQLException {
        String str = "UPDATE teacher SET Tname=?,Birthday=?,Sex=?,Cno=? WHERE Tno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
        ppst.setString(1, teacher.getName());
        ppst.setDate(2, teacher.getBirthday());
        ppst.setString(3, teacher.getSex());
        ppst.setString(4, teacher.getCno());
        ppst.setString(5, teacher.getTno());
        return ppst.executeUpdate();
    }

    @Override
    public int updateStudent(Student student) throws SQLException {
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

    @Override
    public int removeCourse(String cno) throws SQLException {
        String str_rm = "DELETE FROM course WHERE Cno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_rm);
        ppst.setString(1, cno);
        return ppst.executeUpdate();
    }

    @Override
    public int removeTeacher(String tno) throws SQLException {
        String str_rm = "DELETE FROM teacher WHERE Tno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_rm);
        ppst.setString(1, tno);
        return ppst.executeUpdate();
    }

    @Override
    public int removeStudent(String sno) throws SQLException {
        String str_rm = "DELETE FROM student WHERE Sno=?";
        PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_rm);
        ppst.setString(1, sno);
        return ppst.executeUpdate();
    }
}
