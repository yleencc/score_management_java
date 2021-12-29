package cc.yleen.gui.frame;

import cc.yleen.config.Theme;
import cc.yleen.dao.StudentDao;
import cc.yleen.gui.component.DefaultComponents;
import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.BackgroundJPanel;
import cc.yleen.model.Grade;
import cc.yleen.model.Student;
import cc.yleen.utils.DateUtil;
import cc.yleen.utils.ImgUtil;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class StudentMainFrame extends BaseFrame implements ActionListener, ItemListener {
    private JPanel panel_student_info = new JPanel();
    private JScrollPane panel_grade = new JScrollPane();
    private JPanel panel_tools = new JPanel();

    private String[] menus_str;
    private String[][] items2_str;
    private JPopupMenu popMenu;
    private JMenuItem delItem;
    private JMenuItem editItem;

    private JButton button_edit = new JButton("编辑");
    private JButton button_save = new JButton("保存修改");
    private JButton button_refresh_student = new JButton("刷新个人信息");
    private JButton button_refresh_table = new JButton("刷新表格");

    private JLabel text_title = DefaultComponents.getJLabel("");
    private JLabel text_sno = DefaultComponents.getJLabel("");
    private JTextField input_name = new JTextField();
    private JTextField input_school = new JTextField();
    private JTextField input_major = new JTextField();
    private JTextField input_class = new JTextField();
    private JTextField input_birthday = new JTextField();
    private JComboBox<String> select_sex = new JComboBox<String>();
    private JComboBox<String> select_course = new JComboBox<String>();

    private String sno;
    private Student student;
    StudentDao studentDao = new StudentDao();

    ArrayList<Grade> allGradeList = new ArrayList<Grade>();
    private DefaultTableModel model;
    private Vector columnName, rowData;
    private JTable table = new JTable() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }//表格不允许被编辑
    };

    public StudentMainFrame(String sno) {
        this.sno = sno;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        width = (int) (ScreenSizeUtil.getWidth() / 1.5);
        height = (int) (ScreenSizeUtil.getHeight() / 1.5);
        this.setBounds((ScreenSizeUtil.getWidth() - width) / 2, (ScreenSizeUtil.getHeight() - height) / 2, width, height);
        this.setTitle("成绩管理系统 - 学生端");
        this.setIconImage(ImgUtil.getImage("./images/book.png"));
        this.setMinimumSize(new Dimension((int) (width / 1.5), (int) (height / 1.5)));
        this.bgPanel = new BackgroundJPanel();
        this.add(bgPanel);
        initMenuBar(); // 初始化菜单
        initLayout(); // 初始化布局
        initAction(); // 初始化监听事件
        setDisableFormComponents();
        text_title.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/book.png")));
        this.setVisible(true);
        try {
            requestStudentInfo();
            requestGrade();
        } catch (SQLException e) {
            showRequestFailed(e);
        }
    }

    private void initMenuBar() {
        JMenuBar jb = new JMenuBar();
        JMenu[] menus;
        JMenuItem[][] items;
        menus_str = new String[]{"菜单"};
        items2_str = new String[][]{
                {"切换用户", "退出"}
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
        items[0][0].addActionListener(e -> {
            new LoginFrame();
            this.dispose();
        });
        items[0][1].addActionListener(e -> System.exit(0));
    }

    private void initLayout() {
        select_sex.addItem("男");
        select_sex.addItem("女");
        bgPanel.setLayout(new GridBagLayout());
        panel_student_info.setBackground(new Color(0xA6B9CD));
        panel_grade.setBackground(new Color(0xA6B9CD));
        panel_student_info.setLayout(new GridBagLayout());
        panel_tools.setLayout(new GridBagLayout());
        JLabel label = new JLabel("筛选课程：");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        //设置列名
        columnName = new Vector();
        rowData = new Vector();
        columnName.add("学号");
        columnName.add("学生姓名");
        columnName.add("课程ID");
        columnName.add("课程名");
        columnName.add("课程学分");
        columnName.add("成绩");
        // 添加表格
        model = new DefaultTableModel();
        model.setDataVector(rowData, columnName);
        table.setModel(model);
        panel_grade.setViewportView(table);
        //
        panel_tools.add(button_refresh_table, new GBC(0, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        panel_tools.add(new JLabel(" "), new GBC(1, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(100, 20));
        panel_tools.add(label, new GBC(2, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        panel_tools.add(select_course, new GBC(3, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        //
        bgPanel.add(panel_student_info, new GBC(0, 0, 1, 5)
                .setFill(GBC.BOTH)
                .setIpad(10, 10)
                .setWeight(40, 100));
        bgPanel.add(panel_tools, new GBC(1, 0, 4, 1)
                .setFill(GBC.BOTH)
                .setIpad(0, 20)
                .setWeight(120, 0));
        bgPanel.add(panel_grade, new GBC(1, 1, 4, 4)
                .setFill(GBC.BOTH)
                .setIpad(0, 0)
                .setWeight(120, 100));
        //
        panel_student_info.add(text_title, new GBC(1, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("学号：", Theme.Font_.NORMAL, Theme.Color_.PRIMARY), new GBC(2, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("姓名："), new GBC(1, 1, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("性别："), new GBC(1, 2, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("学校："), new GBC(1, 3, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("专业："), new GBC(1, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("班级："), new GBC(1, 5, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("生日："), new GBC(1, 6, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        //
        panel_student_info.add(text_sno, new GBC(3, 0, 1, 1)
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
        panel_student_info.add(button_refresh_student, new GBC(1, 9, 4, 1)
                .setFill(GBC.HORIZONTAL)
                .setInsets(0, 20, 0, 20)
                .setWeight(20, 10));
        panel_student_info.add(button_edit, new GBC(1, 10, 4, 1)
                .setFill(GBC.HORIZONTAL)
                .setInsets(0, 20, 0, 20)
                .setWeight(20, 10));
        panel_student_info.add(button_save, new GBC(1, 11, 4, 1)
                .setFill(GBC.HORIZONTAL)
                .setInsets(0, 20, 0, 20)
                .setWeight(20, 10));
        panel_student_info.add(new JLabel(" "), new GBC(0, 0, 1, 11)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(2, 50));
        panel_student_info.add(new JLabel(" "), new GBC(5, 0, 1, 11)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(2, 50));
        //
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
        button_refresh_student.addActionListener(this);
        button_refresh_table.addActionListener(this);
        button_save.setEnabled(false);
        select_course.addItemListener(this);
    }

    // 请求学生的信息
    private void requestStudentInfo() throws SQLException {
        this.student = studentDao.getStudentInfo(this.sno);
        text_sno.setText(student.getSno());
        input_name.setText(student.getName());
        select_sex.setSelectedItem(student.getSex());
        input_school.setText(student.getSchool());
        input_major.setText(student.getMajor());
        input_class.setText(student.getClassName());
        String day = DateUtil.DateToYYMMDD(student.getBirthday());
        input_birthday.setText(day);
    }

    // 请求学生的成绩记录
    private void requestGrade() throws SQLException {
        select_course.removeAllItems(); // 清空课程的所有选项
        select_course.addItem("全部");
        rowData.clear(); // 清空表格数据
        allGradeList.clear();
        allGradeList.addAll(studentDao.queryStudentAllGrade(this.sno));
        for (Grade gradeData : allGradeList) {
            Vector line = new Vector();
            line.add(gradeData.getSno());
            line.add(gradeData.getStudentName());
            line.add(gradeData.getCno());
            line.add(gradeData.getCourseName());
            line.add(gradeData.getCredit());
            line.add(gradeData.getGrade());
            rowData.add(line);
            select_course.addItem(gradeData.getCourseName());
        }
        table.updateUI();
    }

    // 当默认情况和点击保存的时候，关闭表单控件
    private void setDisableFormComponents() {
        select_sex.setEnabled(false);
        input_name.setEnabled(false);
        input_school.setEnabled(false);
        input_major.setEnabled(false);
        input_class.setEnabled(false);
        input_birthday.setEnabled(false);
    }

    // 当点击编辑的时候，启用表单控件
    private void setEnableFormComponents() {
        select_sex.setEnabled(true);
        input_name.setEnabled(true);
        input_school.setEnabled(true);
        input_major.setEnabled(true);
        input_class.setEnabled(true);
        input_birthday.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_save) {
            try {
                student.setAll(sno, input_name.getText(), (String) select_sex.getSelectedItem(), student.getBirthday(),
                        input_school.getText(), input_major.getText(), input_class.getText());
                int result = studentDao.updateStudentInfo(student);
                if (result > 0) {
                    showSaveSuccessful();
                    setDisableFormComponents();
                    button_save.setEnabled(false);
                } else {
                    showSaveFailed();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "保存信息失败！" + ex.getMessage(),
                        "提示", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == button_edit) {
            setEnableFormComponents();
            button_save.setEnabled(true);
        } else if (e.getSource() == button_refresh_table) {
            try {
                requestGrade();
                showRefreshSuccessful();
            } catch (SQLException e1) {
                showRequestFailed(e1);
            }
        } else if (e.getSource() == button_refresh_student) {
            try {
                requestStudentInfo();
                showRefreshSuccessful();
            } catch (SQLException e2) {
                showRequestFailed(e2);
            }
        }
    }

    // 筛选成绩表（自动取得课程选择框的内容）
    private void filterGradesTable() {
        String courseName = (String) select_course.getSelectedItem();
        int index = select_course.getSelectedIndex();
        rowData.clear(); // 清空表格数据
        for (Grade gradeData : allGradeList) {
            // 过滤课程名
            if (index == 0 || gradeData.getCourseName().equals(courseName)) {
                Vector line = new Vector();
                line.add(gradeData.getSno());
                line.add(gradeData.getStudentName());
                line.add(gradeData.getCno());
                line.add(gradeData.getCourseName());
                line.add(gradeData.getCredit());
                line.add(gradeData.getGrade());
                rowData.add(line);
            }
        }
        table.updateUI();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == select_course && e.getStateChange() == ItemEvent.SELECTED) {
            filterGradesTable();
        }
    }
}
