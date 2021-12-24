package cc.yleen.model;

import java.util.Date;

public class Student {
    private String sno;
    private String name;
    private String sex;
    private Date birthday;
    private String school;
    private String major;
    private String className;

    public Student(String sno, String name, String sex, Date birthday, String school, String major, String className) {
        this.sno = sno;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.school = school;
        this.major = major;
        this.className = className;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

