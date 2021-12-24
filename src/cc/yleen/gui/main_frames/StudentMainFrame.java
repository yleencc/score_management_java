package cc.yleen.gui.main_frames;

import cc.yleen.dao.StudentDao;
import cc.yleen.gui.BaseFrame;
import cc.yleen.gui.component.DefaultComponents;
import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.BackgroundJPanel;
import cc.yleen.model.Student;
import cc.yleen.utils.DateUtil;
import cc.yleen.utils.ImgUtil;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;

public class StudentMainFrame extends BaseFrame implements MenuListener, ActionListener, MouseListener {
    private JPanel panel_student_info = new JPanel();
    private JPanel panel_grade = new JPanel();
    private JPanel panel_tools = new JPanel();

    private String[] menus_str;
    private String[][] items2_str;
    private JPopupMenu popMenu;
    private JMenuItem delItem;
    private JMenuItem editItem;
    private HashMap<String, JButton> buttons = new HashMap<String, JButton>();

    private JButton button_edit = new JButton("编辑");
    private JButton button_save = new JButton("保存修改");
    private JButton button_filter = new JButton("筛选");

    private JLabel text_sno = DefaultComponents.getJLabel("");
    private JTextField input_name = new JTextField();
    private JTextField input_school = new JTextField();
    private JTextField input_major = new JTextField();
    private JTextField input_class = new JTextField();
    private JTextField input_birthday = new JTextField();
    private JComboBox<String> select_sex = new JComboBox<String>();

    private String sno;
    private Student student;

    public StudentMainFrame(String sno) {
        this.sno = sno;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        width = (int) (ScreenSizeUtil.getWidth() / 1.5);
        height = (int) (ScreenSizeUtil.getHeight() / 1.5);
        this.setBounds(width, height, width, height);
        this.setTitle("成绩管理系统 - 学生端");
        this.setIconImage(ImgUtil.getImage("cc/yleen/images/book.png"));
        this.setMinimumSize(new Dimension((int) (width / 1.5), (int) (height / 1.5)));
        this.bgPanel = new BackgroundJPanel();
        this.add(bgPanel);
        // init
        initMenuBar();
        initLayout();
        initToolPanel();
        initAction();
        // Visible
        this.setVisible(true);
        try {
            initData();
        } catch (SQLException e) {
            showRequestFailed(e);
        }
    }

    private void initMenuBar() {
        JMenuBar jb = new JMenuBar();
        JMenu[] menus;
        JMenuItem[][] items;
        menus_str = new String[]{"切换面板", "更多操作"};
        items2_str = new String[][]{
                {"学生/班级管理", "课程管理", "成绩管理", "学期管理"},
                {"退出"}
        };
        // First level Menus
        menus = new JMenu[menus_str.length];
        for (int i = 0; i < menus_str.length; i++) {
            menus[i] = new JMenu(menus_str[i]);
            jb.add(menus[i]);
        }
        // replace MenuItems
        items = new JMenuItem[items2_str.length][];
        for (int i = 0; i < items2_str.length; i++) {
            items[i] = new JMenuItem[items2_str[i].length];
            for (int j = 0; j < items2_str[i].length; j++) {
                items[i][j] = new JMenuItem(items2_str[i][j]);
                menus[i].add(items[i][j]);
            }
        }
        // setJMenuBar
        this.setJMenuBar(jb);
        items[1][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void initLayout() {
        select_sex.addItem("男");
        select_sex.addItem("女");
        bgPanel.setLayout(new GridBagLayout());
        panel_student_info.setBackground(new Color(0xDAE2FF));
        panel_grade.setBackground(new Color(0xFFDCD8));
        panel_student_info.setLayout(new GridBagLayout());
        panel_grade.setLayout(new GridBagLayout());
        panel_tools.setLayout(new GridBagLayout());
        bgPanel.add(panel_student_info, new GBC(0, 0, 1, 5)
                .setFill(GBC.BOTH)
                .setIpad(10, 10)
                .setWeight(50, 100));
        bgPanel.add(panel_tools, new GBC(1, 0, 4, 1)
                .setFill(GBC.BOTH)
                .setIpad(0, 0)
                .setWeight(100, 10));
        bgPanel.add(panel_grade, new GBC(1, 1, 4, 4)
                .setFill(GBC.BOTH)
                .setIpad(0, 0)
                .setWeight(100, 100));
        //
        panel_student_info.add(DefaultComponents.getJLabel("学号"), new GBC(1, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("姓名"), new GBC(1, 1, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("性别"), new GBC(1, 2, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("学校"), new GBC(1, 3, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("专业"), new GBC(1, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("班级"), new GBC(1, 5, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("生日"), new GBC(1, 6, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        //
        panel_student_info.add(text_sno, new GBC(2, 0, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(50, 10));
        panel_student_info.add(input_name, new GBC(2, 1, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(50, 10));
        panel_student_info.add(select_sex, new GBC(2, 2, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 10)
                .setWeight(50, 10));
        panel_student_info.add(input_school, new GBC(2, 3, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(50, 10));
        panel_student_info.add(input_major, new GBC(2, 4, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(50, 10));
        panel_student_info.add(input_class, new GBC(2, 5, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(50, 10));
        panel_student_info.add(input_birthday, new GBC(2, 6, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(50, 10));
        //
        panel_student_info.add(button_edit, new GBC(1, 9, 4, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(20, 10));
        panel_student_info.add(button_save, new GBC(1, 10, 4, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(20, 10));
        panel_student_info.add(new JLabel(" "), new GBC(0, 0, 1, 11)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(2, 50));
        panel_student_info.add(new JLabel(" "), new GBC(5, 0, 1, 11)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(2, 50));
    }

    /**
     * init top tool panel
     */
    private void initToolPanel() {
        JButton refresh = new JButton("刷新列表");
        JButton add_class = new JButton("添加班级");
        JButton add_student = new JButton("添加学生");
        JButton remove_class = new JButton("删除班级");
        buttons.put("refresh", refresh);
        buttons.put("add_class", add_class);
        buttons.put("add_student", add_student);
        buttons.put("remove_class", remove_class);
        panel_tools.add(refresh);
        panel_tools.add(add_class);
        panel_tools.add(add_student);
        panel_tools.add(remove_class);
    }

    private void initAction() {
        //添加菜单项以及为菜单项添加事件
        popMenu = new JPopupMenu();
        delItem = new JMenuItem("删除");
        delItem.addActionListener(this);
        editItem = new JMenuItem("。。。");
        editItem.addActionListener(this);
        popMenu.add(delItem);
        popMenu.add(editItem);
        button_save.addActionListener(this);
        button_edit.addActionListener(this);
        button_filter.addActionListener(this);
        button_save.setEnabled(false);
    }

    public void initData() throws SQLException {
        StudentDao dao = new StudentDao();
        this.student = dao.getStudentInfo(this.sno);
        text_sno.setText(student.getSno());
        input_name.setText(student.getName());
        select_sex.setSelectedItem(student.getSex());
        input_school.setText(student.getSchool());
        input_major.setText(student.getMajor());
        input_class.setText(student.getClassName());
        String day = DateUtil.DateToYYMMDD(student.getBirthday());
        input_birthday.setText(day);
    }

    @Override
    public void menuSelected(MenuEvent e) {

    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ok button
        if (e.getSource() == button_save) {
//            try {
//                StudentDao.modifyStudentInfo(connection, getStudent_fromEditText());
////                updateLeftPanel();
//                super.showSuccessful();
//            } catch (SQLException okError) {
//                okError.printStackTrace();
//                JOptionPane.showMessageDialog(null,
//                        "更新学生信息失败",
//                        "Update Error", JOptionPane.ERROR_MESSAGE);
//            }
        } else if (e.getSource() == button_edit) {
            button_save.setEnabled(true);
        } else if (e.getSource() == button_filter) {
        }
    }

//    private Student getStudent_fromEditText() {
//        long id = Long.parseLong(edits_hmap.get("id").getText());
//        String name = edits_hmap.get("name").getText();
//        String className = class_select.getSelectedItem().toString();
//        String sex = sex_select.getSelectedItem().toString();
//        long phone = Long.parseLong(edits_hmap.get("phone").getText());
//        String addr = edits_hmap.get("addr").getText();
//        String remarks_str = remarks.getText();
//        return new Student(id, name, className, sex, phone, addr, remarks_str);
//    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
