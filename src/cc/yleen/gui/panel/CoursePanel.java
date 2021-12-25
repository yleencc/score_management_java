package cc.yleen.gui.panel;

import cc.yleen.dao.AdminDao;
import cc.yleen.gui.BasePanel;
import cc.yleen.gui.component.GBC;
import cc.yleen.model.Course;
import cc.yleen.utils.ImgUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class CoursePanel extends BasePanel implements MouseListener, ActionListener {
    private AdminDao adminDao;
    ArrayList<Course> courses = new ArrayList<Course>();

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

    public CoursePanel(AdminDao adminDao) {
        this.adminDao = adminDao;
        initView();
        initAction();
        try {
            requestCourse();
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
        columnName.add("课程ID");
        columnName.add("课程名");
        columnName.add("课程学分");
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
            String cno = (String) line.get(0); // Cno
            String cName = (String) line.get(1); // Cname
            float credit;  // Credit
            try {
                credit = Float.parseFloat(line.get(2).toString());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,
                        "保存失败！请检查数字格式是否正确！",
                        "提示", JOptionPane.ERROR_MESSAGE);
                throw nfe;
            }
            try {
                int result = adminDao.updateCourse(new Course(cno, cName, credit));
                if (result > 0) {
                    // 执行成功
                    System.out.println("已修改");
                } else {
                    showSaveFailed();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "保存信息失败！" + ex.getMessage(),
                        "提示", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void requestCourse() throws SQLException {
        rowData.clear(); // 清空表格数据
        courses.clear();
        courses.addAll(adminDao.queryAllCourse());
        for (Course data : courses) {
            Vector line = new Vector();
            line.add(data.getCno());
            line.add(data.getName());
            line.add(data.getCredit());
            rowData.add(line);
        }
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
                requestCourse();
                showRefreshSuccessful();
            } catch (SQLException ex) {
                showRefreshFailed(ex);
            }
        } else if (e.getSource() == itemDelete) {
            int select = JOptionPane.showConfirmDialog(null,
                    "你确定删除这个课程吗?",
                    "警告 !", JOptionPane.YES_OPTION, JOptionPane.YES_OPTION, new ImageIcon(ImgUtil.getImage("cc/yleen/images/?.png")));
            if (select == JOptionPane.YES_OPTION) {
                int row = table.getSelectedRow();
                Vector line = (Vector) rowData.get(row); // 第row行数据
                String cno = (String) line.get(0); // Cno
                try {
                    int result = adminDao.removeCourse(cno);
                    if (result > 0) {
                        rowData.remove(row);
                        table.updateUI();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "删除失败！",
                                "提示", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    showRefreshFailed(ex);
                }
            }
        }
    }
}
