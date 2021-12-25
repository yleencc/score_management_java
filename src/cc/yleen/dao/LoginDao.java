package cc.yleen.dao;

import cc.yleen.config.Const;
import cc.yleen.dao.interfaces.LoginDaoInter;
import cc.yleen.utils.DateUtil;
import cc.yleen.utils.MysqlConnect;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class LoginDao implements LoginDaoInter {
    @Override
    public boolean login(Const.AccountType type, String account, String pass) throws SQLException {
        PreparedStatement ppst;
        ResultSet resultSet;
        switch (type) {
            case teacher:
                String str_teacher = ("SELECT birthday FROM teacher WHERE Tno=?");
                ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_teacher);
                ppst.setString(1, account);
                resultSet = ppst.executeQuery();
                if (resultSet.next()) {
                    Date date = resultSet.getDate("birthday");
                    String getPass = DateUtil.DateToYYMMDD(date);
                    System.out.println("从数据库取得密码：" + getPass);
                    return getPass.equals(pass);
                } else {
                    return false;
                }
            case admin:
                String str_admin = ("SELECT password FROM admin WHERE adminName=?");
                ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_admin);
                ppst.setString(1, account);
                resultSet = ppst.executeQuery();
                if (resultSet.next()) {
                    String getPass = resultSet.getString("password");
                    System.out.println("从数据库取得密码：" + getPass);
                    return getPass.equals(pass);
                } else {
                    return false;
                }
            default:
                String str_student = ("SELECT birthday FROM student WHERE Sno=?");
                ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str_student);
                ppst.setString(1, account);
                resultSet = ppst.executeQuery();
                if (resultSet.next()) {
                    Date date = resultSet.getDate("birthday");
                    String getPass = DateUtil.DateToYYMMDD(date);
                    System.out.println("从数据库取得密码：" + getPass);
                    return getPass.equals(pass);
                } else {
                    return false;
                }
        }
    }
}
