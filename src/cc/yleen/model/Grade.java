package cc.yleen.model;

public class Grade {
    private long StudentID;
    private String StudentName;
    private long CourseID;
    private String CourseName;
    private String SemesterName;
    private int Achievement;

    public Grade(long studentID, String studentName, long courseID, String courseName, String semesterName, int achievement) {
        StudentID = studentID;
        StudentName = studentName;
        CourseID = courseID;
        CourseName = courseName;
        SemesterName = semesterName;
        Achievement = achievement;
    }

    public long getStudentID() {
        return StudentID;
    }

    public void setStudentID(long studentID) {
        StudentID = studentID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public long getCourseID() {
        return CourseID;
    }

    public void setCourseID(long courseID) {
        CourseID = courseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getSemesterName() {
        return SemesterName;
    }

    public void setSemesterName(String semesterName) {
        SemesterName = semesterName;
    }

    public int getAchievement() {
        return Achievement;
    }

    public void setAchievement(int achievement) {
        Achievement = achievement;
    }
}
