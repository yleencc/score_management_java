package cc.yleen.gui;

import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.CoursePanel;
import cc.yleen.model.Course;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddCourseFrame extends BaseFrame {
    private JLabel text_cno = new JLabel("课程号");
    private JLabel text_cname = new JLabel("课程名");
    private JLabel text_cridet = new JLabel("学分");
    private JTextField input_cno = new JTextField();
    private JTextField input_cname = new JTextField();
    private JTextField input_cridet = new JTextField();
    private JButton ok = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private CoursePanel panel_course;

    public AddCourseFrame(CoursePanel panel_course) {
        this.panel_course = panel_course;
        width = ScreenSizeUtil.getWidth() / 4;
        height = ScreenSizeUtil.getHeight() / 4;
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(
                (int) (ScreenSizeUtil.getWidth() / 3), (int) (ScreenSizeUtil.getHeight() / 3))
        );
        this.setTitle("添加成绩");
        this.setBounds(width, height, width, height);
        initView();
        initLayout();
        initActon();
        this.setVisible(true);
    }

    private void initView() {

    }

    private void initLayout() {
        this.setLayout(new GridBagLayout());
        this.add(text_cno, new GBC(0, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_cname, new GBC(0, 1, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(text_cridet, new GBC(0, 2, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(input_cno, new GBC(1, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_cname, new GBC(1, 1, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(input_cridet, new GBC(1, 2, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(110, 0)
                .setWeight(0, 30));
        this.add(ok, new GBC(0, 3, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
        this.add(cancel, new GBC(1, 3, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(0, 30));
    }

    private void initActon() {
        ok.addActionListener(e -> {
            String cno = input_cno.getText();
            String cname = input_cname.getText();
            float credit;
            try {
                credit = Float.parseFloat(input_cridet.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,
                        "学分必须是数字格式！",
                        "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Course course = new Course(cno, cname, credit);
                int result = panel_course.adminDao.addCourse(course);
                if (result > 0) {
                    dispose();
                    panel_course.addCourseToTable(course);
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
