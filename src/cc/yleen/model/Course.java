package cc.yleen.model;

public class Course {
    private String cno;
    private String name;
    private String credit;

    public Course(String cno, String name, String credit) {
        this.cno = cno;
        this.name = name;
        this.credit = credit;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}

