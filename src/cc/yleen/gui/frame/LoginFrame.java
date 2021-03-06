package cc.yleen.gui.frame;

import cc.yleen.config.Const;
import cc.yleen.config.Theme;
import cc.yleen.dao.LoginDao;
import cc.yleen.gui.component.DefaultComponents;
import cc.yleen.gui.component.GBC;
import cc.yleen.gui.panel.BackgroundJPanel;
import cc.yleen.utils.ImgUtil;
import cc.yleen.utils.MysqlConnect;
import cc.yleen.utils.ScreenSizeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginFrame extends BaseFrame {
    LoginDao loginDao = new LoginDao(); // 登陆的Dao执行类
    private Const.AccountType type = Const.AccountType.student; // 用户类型

    private JLabel title = DefaultComponents.getJLabel("成绩管理系统 - 登录页", titleFont);
    private JLabel text_account = DefaultComponents.getJLabel("帐号/学号：");
    private JLabel text_pass = DefaultComponents.getJLabel("密码：");
    private JLabel text_type = DefaultComponents.getJLabel("用户类型：");

    private JTextField input_account = new JTextField(12);
    private JTextField input_pass = new JTextField(10);

    private JButton login = new JButton("登录");
    private ButtonGroup group_user = new ButtonGroup();
    private JRadioButton radio_student = new JRadioButton("学生");
    private JRadioButton radio_teacher = new JRadioButton("教师");
    private JRadioButton radio_admin = new JRadioButton("管理员");

    static Font titleFont = new Font(Theme.Font_.NORMAL.getName(), Theme.Font_.NORMAL.getStyle(),
            Theme.Font_.NORMAL.getSize() + 12); // 标题的字体
    static Font loginButtonFont = new Font(Theme.Font_.NORMAL.getName(), Font.BOLD,
            Theme.Font_.NORMAL.getSize() + 4); // 登录按钮的字体
    private Color colorForeground = new Color(0xF5DCDCDC, true);
    private Color colorbackground = new Color(0x48969B);
    private Color colorForegroundHover = new Color(0xF5EBEBEB, true);
    private Color colorbackgroundHover = new Color(0x448287);

    public LoginFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        width = (int) (ScreenSizeUtil.getHeight() / 2);
        height = (int) (ScreenSizeUtil.getHeight() / 1.8);
        this.setBounds((ScreenSizeUtil.getWidth() - width) / 2,
                (ScreenSizeUtil.getHeight() - height) / 2, width, height);
        this.setMinimumSize(new Dimension(width, height));
        this.setTitle("成绩管理系统");
        this.setIconImage(ImgUtil.getImage("./images/book.png"));
        bgPanel = new BackgroundJPanel();
        bgPanel.setBg("./images/bg.png");
        initView(); // 初始化视图
        setTextColor(); // 设置字体
        initAction(); // 初始化监听事件
        radio_student.doClick();
        this.add(bgPanel);
        this.setVisible(true);
    }

    private void initView() {
        bgPanel.setLayout(new GridBagLayout());
        title.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/book.png")));
        // 登陆按钮
        login.setBorderPainted(true);
        login.setBorder(null);
        login.setOpaque(true);
        login.setForeground(colorForeground);
        login.setBackground(colorbackground);
        login.setFont(loginButtonFont);
        group_user.add(radio_student);
        group_user.add(radio_teacher);
        group_user.add(radio_admin);
        // 设定布局
        bgPanel.add(title, new GBC(0, 0, 8, 1)
                .setFill(GBC.CENTER)
                .setIpad(0, 0)
                .setWeight(100, 30));
        bgPanel.add(text_account, new GBC(2, 2, 1, 1)
                .setFill(GBC.BOTH)
                .setInsets(0, 20, 0, 0)
                .setIpad(0, 10)
                .setWeight(100, 30));
        bgPanel.add(text_pass, new GBC(2, 3, 1, 1)
                .setFill(GBC.BOTH)
                .setInsets(0, 20, 0, 0)
                .setIpad(0, 10)
                .setWeight(0, 30));
        bgPanel.add(input_account, new GBC(3, 2, 3, 1)
                .setFill(GBC.HORIZONTAL)
                .setInsets(0, 0, 0, 20)
                .setIpad(10, 10)
                .setWeight(0, 0));
        bgPanel.add(input_pass, new GBC(3, 3, 3, 1)
                .setFill(GBC.HORIZONTAL)
                .setInsets(0, 0, 0, 20)
                .setIpad(10, 10)
                .setWeight(0, 0));
        bgPanel.add(text_type, new GBC(1, 4, 2, 1)
                .setFill(GBC.BOTH)
                .setIpad(0, 10)
                .setInsets(0, 20, 0, 0)
                .setWeight(0, 30));
        bgPanel.add(radio_student, new GBC(3, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 15)
                .setWeight(20, 30));
        bgPanel.add(radio_teacher, new GBC(4, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 15)
                .setWeight(20, 30));
        bgPanel.add(radio_admin, new GBC(5, 4, 1, 1)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 15)
                .setWeight(20, 30));
        //
        bgPanel.add(login, new GBC(1, 5, 6, 2)
                .setFill(GBC.HORIZONTAL)
                .setIpad(0, 20)
                .setWeight(50, 30));
        bgPanel.add(new JLabel("  "), new GBC(0, 1, 8, 1)
                .setFill(GBC.BOTH)
                .setIpad(10, 10)
                .setWeight(0, 10));
        bgPanel.add(new JLabel("  "), new GBC(0, 5, 1, 1)
                .setFill(GBC.BOTH)
                .setIpad(10, 10)
                .setWeight(0, 10));
        bgPanel.add(new JLabel("  "), new GBC(7, 5, 1, 1)
                .setFill(GBC.BOTH)
                .setIpad(10, 10)
                .setWeight(0, 10));
    }

    // Actions
    private void initAction() {
        radio_student.addActionListener(e -> type = Const.AccountType.student);
        radio_teacher.addActionListener(e -> type = Const.AccountType.teacher);
        radio_admin.addActionListener(e -> type = Const.AccountType.admin);
        login.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                login.setBackground(colorbackgroundHover);
                login.setForeground(colorForegroundHover);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                login.setBackground(colorbackground);
                if (!isExistNoneInput()) {
                    if (MysqlConnect.reconnectSQL()) {
                        login();
                    } else {
                        // 连接失败
                        JOptionPane.showMessageDialog(null, "连接数据库失败！请检查网络或联系管理员。",
                                "提示", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                login.setForeground(new Color(0xFFFFFF));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                login.setForeground(colorForeground);
                login.setBackground(colorbackground);
            }
        });
    }

    // Text color
    public void setTextColor() {
        title.setForeground(Theme.Color_.PRIMARY);
        text_account.setForeground(Theme.Color_.PRIMARY);
        text_pass.setForeground(Theme.Color_.PRIMARY);
        text_type.setForeground(Theme.Color_.PRIMARY);
    }

    // 检查输入框是否存在空值
    public boolean isExistNoneInput() {
        if (input_account.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入帐号！",
                    "提示", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (input_pass.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入密码！",
                    "提示", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public void login() {
        try {
            boolean result = loginDao.login(type, input_account.getText(), input_pass.getText());
            if (!result) {
                JOptionPane.showMessageDialog(null, "登录失败，密码错误，请检查帐号或密码！",
                        "提示", JOptionPane.ERROR_MESSAGE);
            } else {
                EventQueue.invokeLater(() -> {
                    dispose();
                    // 跳转对应用户类型的面板
                    switch (type) {
                        case teacher:
                            new TeacherMainFrame(input_account.getText());
                            break;
                        case admin:
                            new AdminMainFrame(input_account.getText());
                            break;
                        default:
                            new StudentMainFrame(input_account.getText());
                    }
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "登录失败！" + ex.getMessage(),
                    "提示", JOptionPane.ERROR_MESSAGE);
        }
    }
}
