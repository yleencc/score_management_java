package cc.yleen.model;

import java.sql.Date;

public class Teacher {
    private String tno;
    private String name;
    private String sex;
    private Date birthday;
    private String cno;
    private String courseName;

    public Teacher(String tno, String name, String sex, Date birthday, String cno, String courseName) {
        this.tno = tno;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.cno = cno;
        this.courseName = courseName;
    }

    public void setAll(String tno, String name, String sex, Date birthday, String cno, String courseName) {
        this.tno = tno;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.cno = cno;
        this.courseName = courseName;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
}

