package cc.yleen.gui.frame;

import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.TeacherPanel;
import cc.yleen.model.Teacher;
import cc.yleen.utils.DateUtil;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class AddTeacherFrame extends BaseFrame {
    private JLabel text_tno = new JLabel("教师号");
    private JLabel text_tname = new JLabel("姓名");
    private JLabel text_sex = new JLabel("性别");
    private JLabel text_birthday = new JLabel("生日（格式2020-01-01）");
    private JLabel text_cno = new JLabel("负责的课程号");
    private JTextField input_tno = new JTextField();
    private JTextField input_tname = new JTextField();
    private JTextField input_sex = new JTextField();
    private JTextField input_birthday = new JTextField();
    private JTextField input_cno = new JTextField();
    private JButton ok = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private TeacherPanel panel_teacher;

    public AddTeacherFrame(TeacherPanel panel_teacher) {
        this.panel_teacher = panel_teacher;
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
        this.add(text_tno, new GBC(0, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_tname, new GBC(0, 1, 1, 1)
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
        this.add(text_cno, new GBC(0, 5, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(input_tno, new GBC(1, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_tname, new GBC(1, 1, 1, 1)
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
        this.add(input_cno, new GBC(1, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(ok, new GBC(1, 5, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(cancel, new GBC(0, 5, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
    }

    private void initActon() {
        ok.addActionListener(e -> {
            String tno = input_tno.getText();
            String tName = input_tname.getText();
            String sex = input_sex.getText();
            java.sql.Date birthday;
            String cno = input_cno.getText();
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
                Teacher teacher = new Teacher(tno, tName, sex, birthday, cno, "");
                int result = panel_teacher.adminDao.addTeacher(teacher);
                if (result > 0) {
                    dispose();
                    panel_teacher.addteacherToTable(teacher);
                    panel_teacher.tip.setText("添加教师号为" + tno + "的教师成功" );
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
