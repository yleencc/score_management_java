package cc.yleen.config;

import java.awt.*;

/**
 * 主题、样式配置
 */
public class Theme {
    public static class Font_ {
        public static final Font NORMAL = new Font("Microsoft Yahei UI",Font.PLAIN,16);
        public static final Font SMALL = new Font("Microsoft Yahei UI",Font.PLAIN,12);
        public static final Font BIG_TITLE = new Font("Microsoft Sans Serif",Font.PLAIN,30);
    }
    public static class Color_ {
        public static final Color BLUE_LIGHT= new Color(96,125,139);
        public static final Color BLUE_DEEPTH= new Color(60, 86, 95);
        public static final Color PRIMARY= BLUE_DEEPTH;
    }
}
