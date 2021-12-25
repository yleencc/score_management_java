package cc.yleen.model;

public class Course {
    private String cno;
    private String name;
    private float credit;

    public Course(String cno, String name, float credit) {
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

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }
}

