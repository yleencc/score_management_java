package cc.yleen.config;

/**
 * 数据库配置
 */
public class SQLConfig {
    public static final String ADDRESS = "127.0.0.1";
    public static final String PORT = "3306";
    public static final String USER = "root";
    public static final String PASSWORD = "1540";
    public static final String DATABASE = "score_management";
    public static final String URL = "jdbc:mysql://" + ADDRESS + ":" + PORT + "/" + DATABASE;
    // jdbc:mysql://127.0.0.1:3306/score_management
}
