package cc.yleen.model;

public class Grade {
    private String sno;
    private String studentName;
    private String cno;
    private String courseName;
    private float grade;
    private float credit;

    public Grade(String sno, String studentName, String cno, String courseName, float grade, float credit) {
        this.sno = sno;
        this.studentName = studentName;
        this.cno = cno;
        this.courseName = courseName;
        this.grade = grade;
        this.credit = credit;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }
}
