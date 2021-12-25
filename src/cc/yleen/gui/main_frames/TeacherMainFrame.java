package cc.yleen.gui.main_frames;

import cc.yleen.config.Theme;
import cc.yleen.dao.TeacherDao;
import cc.yleen.gui.BaseFrame;
import cc.yleen.gui.LoginFrame;
import cc.yleen.gui.component.DefaultComponents;
import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.BackgroundJPanel;
import cc.yleen.model.Grade;
import cc.yleen.model.Teacher;
import cc.yleen.utils.DateUtil;
import cc.yleen.utils.ImgUtil;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class TeacherMainFrame extends BaseFrame implements MenuListener, ActionListener, MouseListener, ItemListener {
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
    private JLabel text_tno = DefaultComponents.getJLabel("");
    private JLabel text_course = DefaultComponents.getJLabel("");
    private JLabel text_save_grade = DefaultComponents.getJLabel("", Theme.Font_.SMALL);
    private JTextField input_name = new JTextField();
    private JTextField input_birthday = new JTextField();
    private JComboBox<String> select_sex = new JComboBox<String>();
    private JComboBox<String> select_student = new JComboBox<String>();

    private String tno;
    private Teacher teacher;
    TeacherDao teacherDao = new TeacherDao();

    ArrayList<Grade> allGradeList = new ArrayList<Grade>();
    private DefaultTableModel model;
    private Vector columnName, rowData;
    private JTable table = new JTable() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5;
        }//表格不允许被编辑
    };

    public TeacherMainFrame(String tno) {
        this.tno = tno;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        width = (int) (ScreenSizeUtil.getWidth() / 1.7);
        height = (int) (ScreenSizeUtil.getHeight() / 1.7);
        this.setBounds(width, height, width, height);
        this.setTitle("成绩管理系统 - 教师端");
        this.setIconImage(ImgUtil.getImage("cc/yleen/images/book.png"));
        this.setMinimumSize(new Dimension((int) (width / 1.5), (int) (height / 1.5)));
        this.bgPanel = new BackgroundJPanel();
        this.add(bgPanel);
        // init
        initMenuBar();
        initLayout();
        initAction();
        setDisableFormComponents();
        text_title.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/book.png")));
        // Visible
        this.setVisible(true);
        try {
            requestTeacherInfo();
            requestGrade();
        } catch (SQLException e) {
            showRequestFailed(e);
        }
    }

    private void initMenuBar() {
        JMenuBar jb = new JMenuBar();
        JMenu[] menus;
        JMenuItem[][] items;
        menus_str = new String[]{"菜单", "其他"};
        items2_str = new String[][]{
                {"切换用户"},
                {"关于作者", "退出"}
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
        items[1][1].addActionListener(e -> System.exit(0));
    }

    private void initLayout() {
        table.getTableHeader().setReorderingAllowed(false); //不可整列移动
//        table.getTableHeader().setResizingAllowed(false); //不可拉动表格宽度
        select_sex.addItem("男");
        select_sex.addItem("女");
        select_student.addItem("全部学生");
        select_student.addItem("及格的学生");
        select_student.addItem("不及格的学生");
        bgPanel.setLayout(new GridBagLayout());
        panel_student_info.setBackground(new Color(0xDAE2FF));
        panel_grade.setBackground(new Color(0xFFDCD8));
        panel_student_info.setLayout(new GridBagLayout());
        panel_tools.setLayout(new GridBagLayout());
        panel_tools.add(button_refresh_student);
        JLabel label = new JLabel("（仅显示本人负责的学生）筛选学生：");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel_tools.add(button_refresh_table, new GBC(0, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        panel_tools.add(new JLabel(" "), new GBC(1, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        panel_tools.add(label, new GBC(2, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        panel_tools.add(select_student, new GBC(3, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        panel_tools.add(new JLabel(" "), new GBC(4, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 10, 0, 0)
                .setWeight(20, 20));
        panel_tools.add(text_save_grade, new GBC(5, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 0, 0, 10)
                .setWeight(20, 20));
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
                .setInsets(0, 10, 0, 0)
                .setWeight(0, 10));
        panel_student_info.add(DefaultComponents.getJLabel("教师号：", Theme.Font_.NORMAL, Theme.Color_.PRIMARY), new GBC(2, 0, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 0, 0, 0)
                .setWeight(10, 10));
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
        panel_student_info.add(DefaultComponents.getJLabel("生日："), new GBC(1, 3, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        panel_student_info.add(DefaultComponents.getJLabel("负责课程："), new GBC(1, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        //
        panel_student_info.add(text_tno, new GBC(3, 0, 3, 1)
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
        panel_student_info.add(input_birthday, new GBC(2, 3, 2, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setWeight(50, 10));
        panel_student_info.add(text_course, new GBC(2, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 0)
                .setWeight(20, 10));
        //
        panel_student_info.add(button_refresh_student, new GBC(1, 9, 5, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 20)
                .setWeight(20, 10));
        panel_student_info.add(button_edit, new GBC(1, 10, 5, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
                .setInsets(0, 20, 0, 20)
                .setWeight(20, 10));
        panel_student_info.add(button_save, new GBC(1, 11, 5, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 0)
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
        select_student.addItemListener(this);
        // 表格修改值的监听
        table.getModel().addTableModelListener(e -> {
            int col = e.getColumn();
            int row = e.getFirstRow();
            if (col == 5) {
                Vector line = (Vector) rowData.get(row); // 第row行数据
                String sno = (String) line.get(0); // Sno
                String sname = (String) line.get(1);
                String cno = (String) line.get(2); // Cno
                String cname = (String) line.get(3);
                float grade = Float.parseFloat((String) line.get(5)); // Grade
                try {
                    int result = teacherDao.updateStudentGrade(sno, cno, grade);
                    if (result > 0) {
                        // 执行成功
                        text_save_grade.setText("已修改" + sname + "的" + cname + "成绩为" + grade);
                    } else {
                        showSaveFailed();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "保存信息失败！" + ex.getMessage(),
                            "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void requestTeacherInfo() throws SQLException {
        this.teacher = teacherDao.getTeacherInfo(this.tno);
        text_tno.setText(teacher.getTno());
        input_name.setText(teacher.getName());
        select_sex.setSelectedItem(teacher.getSex());
        text_course.setText(teacher.getCourseName());
        String day = DateUtil.DateToYYMMDD(teacher.getBirthday());
        input_birthday.setText(day);
    }

    private void requestGrade() throws SQLException {
        rowData.clear(); // 清空表格数据
        select_student.setSelectedIndex(0);
        allGradeList.clear();
        allGradeList.addAll(teacherDao.queryCourseAllGrade(this.tno));
        for (Grade gradeData : allGradeList) {
            Vector line = new Vector();
            line.add(gradeData.getSno());
            line.add(gradeData.getStudentName());
            line.add(gradeData.getCno());
            line.add(gradeData.getCourseName());
            line.add(gradeData.getCredit());
            line.add(gradeData.getGrade());
            rowData.add(line);
        }
        table.updateUI();
    }

    private void setDisableFormComponents() {
        select_sex.setEnabled(false);
        input_name.setEnabled(false);
        input_birthday.setEnabled(false);
    }

    private void setEnableFormComponents() {
        select_sex.setEnabled(true);
        input_name.setEnabled(true);
        input_birthday.setEnabled(true);
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
        if (e.getSource() == button_save) {
            try {
                teacher.setAll(tno, input_name.getText(), (String) select_sex.getSelectedItem(), teacher.getBirthday(),
                        teacher.getCno(), teacher.getCourseName());
                int result = teacherDao.updateTeacherInfo(teacher);
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
                requestTeacherInfo();
                showRefreshSuccessful();
            } catch (SQLException e2) {
                showRequestFailed(e2);
            }
        }
    }

    // 筛选成绩表（自动取得课程选择框的内容）
    private void filterGradesTable() {
        int index = select_student.getSelectedIndex();
        rowData.clear(); // 清空表格数据
        for (Grade gradeData : allGradeList) {
            boolean isPass = gradeData.getGrade() >= 60.0; // 该学生是否及格
            if (index == 0 || (index == 1 && isPass) || (index == 2 && !isPass)) {
                // 符合条件，添加到表格
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == select_student && e.getStateChange() == ItemEvent.SELECTED) {
            filterGradesTable();
        }
    }
}
