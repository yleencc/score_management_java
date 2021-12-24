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
        switch (type) {
            case teacher:
            case admin:
                return false;
            default:
                String str = ("SELECT birthday FROM student WHERE Sno=?");
                PreparedStatement ppst = (PreparedStatement) MysqlConnect.getInstance().prepareStatement(str);
                ppst.setString(1, account);
                ResultSet rs = ppst.executeQuery();
                if (rs.next()) {
                    Date date = rs.getDate("birthday");
                    String getPass = DateUtil.DateToYYMMDD(date);
                    System.out.println("从数据库取得密码：" + getPass);
                    return getPass.equals(pass);
                } else {
                    return false;
                }
        }
    }
}
