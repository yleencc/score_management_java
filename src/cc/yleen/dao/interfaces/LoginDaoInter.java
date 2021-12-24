package cc.yleen.dao.interfaces;

import cc.yleen.config.Const;

import java.sql.SQLException;

public interface LoginDaoInter {
    boolean login(Const.AccountType type, String account, String pass) throws SQLException;
}
