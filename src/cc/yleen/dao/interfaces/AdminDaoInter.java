package cc.yleen.dao.interfaces;

import cc.yleen.model.Grade;
import cc.yleen.model.Student;
import cc.yleen.model.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminDaoInter {
    ArrayList<Student> getAllStudents() throws SQLException;

    ArrayList<Teacher> getAllTeachers() throws SQLException;

    ArrayList<Grade> queryAllGrade() throws SQLException;
}
