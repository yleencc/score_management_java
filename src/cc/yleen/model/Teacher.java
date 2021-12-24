package cc.yleen.model;

import java.util.Date;

public class Teacher {
    private String tno;
    private String name;
    private String sex;
    private Date birthday;
    private String cno;

    public Teacher(String tno, String name, String sex, Date birthday, String cno) {
        this.tno = tno;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.cno = cno;
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
}

