package cc.yleen.gui.panel;

import cc.yleen.dao.AdminDao;
import cc.yleen.gui.frame.AddStudentFrame;
import cc.yleen.gui.frame.BasePanel;
import cc.yleen.gui.component.GBC;
import cc.yleen.model.Student;
import cc.yleen.utils.DateUtil;
import cc.yleen.utils.ImgUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class StudentPanel extends BasePanel implements MouseListener, ActionListener {
    public AdminDao adminDao;
    public JLabel tip;
    ArrayList<Student> students = new ArrayList<>();

    private JScrollPane panel_table = new JScrollPane();
    private DefaultTableModel model;
    private Vector columnName, rowData;
    private JTable table = new JTable() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0; // 表格列0不允许被编辑
        }
    };
    private JPopupMenu menus = new JPopupMenu(); // 右键鼠标后的菜单

    JMenuItem itemDelete = new JMenuItem("删除 ");
    JMenuItem itemAdd = new JMenuItem("添加 ");
    JMenuItem itemRefresh = new JMenuItem("刷新 ");

    public StudentPanel(AdminDao adminDao, JLabel tip) {
        this.adminDao = adminDao;
        this.tip = tip;
        initView();
        initAction();
        try {
            requestStudent();
        } catch (SQLException e) {
            showRequestFailed(e);
        }
    }

    void initView() {
        this.setLayout(new GridBagLayout());
        itemRefresh.addActionListener(this);
        itemAdd.addActionListener(this);
        itemDelete.addActionListener(this);
        menus.add(itemDelete);
        menus.add(itemAdd);
        menus.add(itemRefresh);
        // 添加表格
        columnName = new Vector();
        rowData = new Vector();
        columnName.add("学号");
        columnName.add("姓名");
        columnName.add("性别");
        columnName.add("生日");
        columnName.add("学校");
        columnName.add("专业");
        columnName.add("班级");
        model = new DefaultTableModel();
        model.setDataVector(rowData, columnName);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false); //不可整列移动
        panel_table.setViewportView(table);
        this.add(panel_table, new GBC(0, 0, 1, 1)
                .setFill(GBC.BOTH)
                .setIpad(10, 10)
                .setWeight(40, 100));
    }

    void initAction() {
        panel_table.addMouseListener(this);
        table.addMouseListener(this);
        // 表格修改值的监听
        table.getModel().addTableModelListener(e -> {
//            int col = e.getColumn();
            int row = e.getFirstRow();
            Vector line = (Vector) rowData.get(row); // 第row行数据
            String sno = (String) line.get(0);
            String sName = (String) line.get(1);
            String sex = (String) line.get(2);
            String school = (String) line.get(4);
            String major = (String) line.get(5);
            String className = (String) line.get(6);
            java.sql.Date birthday;
            try {
                java.util.Date date = DateUtil.yyyy_MM_ddToDate(line.get(3).toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                birthday = new java.sql.Date(calendar.getTimeInMillis());
            } catch (ParseException ee1) {
                JOptionPane.showMessageDialog(null,
                        "保存失败！请检查生日的日期格式是否正确！",
                        "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int result = adminDao.updateStudent(new Student(sno, sName, sex, birthday, school, major, className));
                if (result > 0) {
                    // 执行成功
                    System.out.println("已修改");
                    tip.setText("成功更新学号为" + sno + "的学生信息");
                } else {
                    showSaveFailed();
                    tip.setText("更新学号为" + sno + "的学生信息失败！");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "保存信息失败！" + ex.getMessage(),
                        "提示", JOptionPane.ERROR_MESSAGE);
                tip.setText("更新学号为" + sno + "的学生信息失败！");
            }
        });
    }

    private void requestStudent() throws SQLException {
        rowData.clear(); // 清空表格数据
        students.clear();
        students.addAll(adminDao.getAllStudents());
        for (Student data : students) {
            Vector line = new Vector();
            line.add(data.getSno());
            line.add(data.getName());
            line.add(data.getSex());
            line.add(data.getBirthday());
            line.add(data.getSchool());
            line.add(data.getMajor());
            line.add(data.getClassName());
            rowData.add(line);
        }
        table.updateUI();
    }

    // 添加学生到表格（不执行数据库）
    public void addStudentToTable(Student student) throws SQLException {
        students.add(student);
        Vector line = new Vector();
        line.add(student.getSno());
        line.add(student.getName());
        line.add(student.getSex());
        line.add(student.getBirthday());
        line.add(student.getSchool());
        line.add(student.getMajor());
        line.add(student.getClassName());
        rowData.add(line);
        table.updateUI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() == table) {
            //通过点击位置找到点击为表格中的行
            int focusedRowIndex = table.rowAtPoint(e.getPoint());
            if (focusedRowIndex == -1) {
                return;
            }
            // 将表格所选项设为当前右键点击的行
            if (table.getSelectedRowCount() < 2)
                table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            //弹出菜单
            itemDelete.setEnabled(true);
            menus.show(table, e.getX(), e.getY());
        }
        if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() == panel_table) {
            itemDelete.setEnabled(false);
            menus.show(panel_table, e.getX(), e.getY());
        }
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemRefresh) {
            try {
                requestStudent();
                showRefreshSuccessful();
            } catch (SQLException ex) {
                showRefreshFailed(ex);
            }
        } else if (e.getSource() == itemDelete) {
            int select = JOptionPane.showConfirmDialog(null,
                    "你确定删除这个学生吗?",
                    "警告 !", JOptionPane.YES_OPTION, JOptionPane.YES_OPTION, new ImageIcon(ImgUtil.getImage("cc/yleen/images/?.png")));
            if (select == JOptionPane.YES_OPTION) {
                int row = table.getSelectedRow();
                Vector line = (Vector) rowData.get(row); // 第row行数据
                String sno = (String) line.get(0); // Cno
                try {
                    int result = adminDao.removeStudent(sno);
                    if (result > 0) {
                        rowData.remove(row);
                        table.updateUI();
                        tip.setText("删除学号为" + sno + "的学生成功");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "删除失败！",
                                "提示", JOptionPane.ERROR_MESSAGE);
                        tip.setText("删除学号为" + sno + "的学生失败！");
                    }
                } catch (SQLException ex) {
                    showRefreshFailed(ex);
                }
            }
        } else if (e.getSource() == itemAdd) {
            new AddStudentFrame(this);
        }
    }
}
