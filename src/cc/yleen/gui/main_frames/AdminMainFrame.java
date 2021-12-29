package cc.yleen.gui.main_frames;

import cc.yleen.config.Theme;
import cc.yleen.dao.AdminDao;
import cc.yleen.gui.BaseFrame;
import cc.yleen.gui.LoginFrame;
import cc.yleen.gui.component.DefaultComponents;
import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.BackgroundJPanel;
import cc.yleen.gui.panel.CoursePanel;
import cc.yleen.gui.panel.StudentPanel;
import cc.yleen.gui.panel.TeacherPanel;
import cc.yleen.utils.ImgUtil;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class AdminMainFrame extends BaseFrame implements ActionListener {
    private JPanel panel_course;
    private JPanel panel_teacher;
    private JPanel panel_student;
    private JTabbedPane tabbedPane = new JTabbedPane(); // 选项卡
    public JLabel tip = DefaultComponents.getJLabel("（操作的执行会在这里获得反馈）", Theme.Font_.SMALL);
    private String[] menus_str;
    private String[][] items2_str;

    private String adminName;
    AdminDao adminDao = new AdminDao();

    public AdminMainFrame(String adminName) {
        this.adminName = adminName;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        width = (int) (ScreenSizeUtil.getWidth() / 1.7);
        height = (int) (ScreenSizeUtil.getHeight() / 1.7);
        this.setBounds((ScreenSizeUtil.getWidth() - width) / 2, (ScreenSizeUtil.getHeight() - height) / 2, width, height);
        this.setTitle("成绩管理系统 - 当前管理员帐号：" + this.adminName);
        this.setIconImage(ImgUtil.getImage("cc/yleen/images/book.png"));
        this.setMinimumSize(new Dimension((int) (width / 1.5), (int) (height / 1.5)));
        this.bgPanel = new BackgroundJPanel();
        this.add(bgPanel);
        // init
        initMenuBar();
        initLayout();
        initAction();
        this.setVisible(true);
    }

    private void initMenuBar() {
        JMenuBar jb = new JMenuBar();
        JMenu[] menus;
        JMenuItem[][] items;
        menus_str = new String[]{"菜单"};
        items2_str = new String[][]{
                {"切换用户"},
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
        items[0][0].addActionListener(e -> {
            new LoginFrame();
            this.dispose();
        });
        items[0][1].addActionListener(e -> System.exit(0));
    }

    private void initLayout() {
        panel_course = new CoursePanel(adminDao, tip);
        panel_teacher = new TeacherPanel(adminDao, tip);
        panel_student = new StudentPanel(adminDao, tip);
        bgPanel.setLayout(new GridBagLayout());
        // 设置选项卡标签的布局方式
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        bgPanel.add(tabbedPane, new GBC(0, 0, 1, 1)
                .setFill(GBC.BOTH)
                .setIpad(0, 0)
                .setWeight(100, 100));
        bgPanel.add(tip, new GBC(0, 1, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(20, 0)
                .setInsets(0, 10, 4, 0)
                .setWeight(100, 0));
        URL resource = AdminMainFrame.class.getResource("/images/book.png");
        ImageIcon imageIcon = new ImageIcon(resource);
        JLabel tabLabelA = new JLabel();
        tabLabelA.setText("课程信息");
        // 将上面标签组件添加到选项卡中
        tabbedPane.addTab("课程信息", imageIcon, panel_course, "点击管理课程信息");
        tabbedPane.addTab("教师信息", imageIcon, panel_teacher, "点击管理教师信息");
        tabbedPane.addTab("学生信息", imageIcon, panel_student, "点击管理学生信息");
        tabbedPane.setSelectedIndex(0); // 设置索引为1的选项卡被选中
        tabbedPane.setForeground(Theme.Color_.PRIMARY);
    }

    private void initAction() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
