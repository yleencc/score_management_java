package cc.yleen.dao.interfaces;

import cc.yleen.model.Course;
import cc.yleen.model.Grade;
import cc.yleen.model.Student;
import cc.yleen.model.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminDaoInter {
    ArrayList<Student> getAllStudents() throws SQLException;

    ArrayList<Teacher> getAllTeachers() throws SQLException;

    ArrayList<Course> queryAllCourse() throws SQLException;

    int addCourse(Course course) throws SQLException;

    int addTeacher(Course course) throws SQLException;

    int addStudent(Course course) throws SQLException;

    int updateCourse(Course course) throws SQLException;

    int updateTeacher(Course course) throws SQLException;

    int updateStudent(Course course) throws SQLException;

    int removeCourse(String cno) throws SQLException;

    int removeTeacher(String tno) throws SQLException;

    int removeStudent(String sno) throws SQLException;
}
