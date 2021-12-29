package cc.yleen.gui;

import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.CoursePanel;
import cc.yleen.gui.panel.StudentPanel;
import cc.yleen.model.Course;
import cc.yleen.model.Student;
import cc.yleen.utils.DateUtil;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class AddStudentFrame extends BaseFrame {
    private JLabel text_sno = new JLabel("学号");
    private JLabel text_sname = new JLabel("姓名");
    private JLabel text_sex = new JLabel("性别");
    private JLabel text_birthday = new JLabel("生日（格式2020-01-01）");
    private JLabel text_school = new JLabel("学校");
    private JLabel text_major = new JLabel("专业");
    private JLabel text_class = new JLabel("班级");
    private JTextField input_sno = new JTextField();
    private JTextField input_sname = new JTextField();
    private JTextField input_sex = new JTextField();
    private JTextField input_birthday = new JTextField();
    private JTextField input_school = new JTextField();
    private JTextField input_major = new JTextField();
    private JTextField input_class = new JTextField();
    private JButton ok = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private StudentPanel panel_student;

    public AddStudentFrame(StudentPanel panel_student) {
        this.panel_student = panel_student;
        width = ScreenSizeUtil.getWidth() / 4;
        height = ScreenSizeUtil.getHeight() / 4;
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(
                (int) (ScreenSizeUtil.getWidth() / 3), (int) (ScreenSizeUtil.getHeight() / 3))
        );
        this.setTitle("添加成绩");
        this.setBounds((ScreenSizeUtil.getWidth() - width) / 2, (ScreenSizeUtil.getHeight() - height) / 2, width, height);
        initView();
        initLayout();
        initActon();
        this.setVisible(true);
    }

    private void initView() {

    }

    private void initLayout() {
        this.setLayout(new GridBagLayout());
        this.add(text_sno, new GBC(0, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_sname, new GBC(0, 1, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_sex, new GBC(0, 2, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_birthday, new GBC(0, 3, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_school, new GBC(0, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_major, new GBC(0, 5, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_class, new GBC(0, 6, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(input_sno, new GBC(1, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_sname, new GBC(1, 1, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_sex, new GBC(1, 2, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_birthday, new GBC(1, 3, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_school, new GBC(1, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_major, new GBC(1, 5, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_class, new GBC(1, 6, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(ok, new GBC(0, 7, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(cancel, new GBC(1, 7, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
    }

    private void initActon() {
        ok.addActionListener(e -> {
            String sno = input_sno.getText();
            String sName = input_sname.getText();
            String sex = input_sex.getText();
            java.sql.Date birthday;
            String school = input_school.getText();
            String major = input_major.getText();
            String class_ = input_class.getText();
            try {
                java.util.Date date = DateUtil.yyyy_MM_ddToDate(input_birthday.getText());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                birthday = new java.sql.Date(calendar.getTimeInMillis());
            } catch (ParseException nfe) {
                JOptionPane.showMessageDialog(null,
                        "错误！生日的日期格式必须是yyyy-MM-dd，例如2020-01-01",
                        "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Student student = new Student(sno, sName, sex, birthday, school, major, class_);
                int result = panel_student.adminDao.addStudent(student);
                if (result > 0) {
                    dispose();
                    panel_student.addStudentToTable(student);
                    panel_student.tip.setText("添加学号为" + sno + "的学生成功" );
                } else {
                    JOptionPane.showMessageDialog(null,
                            "添加失败！",
                            "提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "添加失败！请检查网络！" + ex.getMessage(),
                        "提示", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancel.addActionListener(e -> {
            dispose();
        });
    }
}
