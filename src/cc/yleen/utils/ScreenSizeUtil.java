package cc.yleen.utils;

import java.awt.*;

public class ScreenSizeUtil {
    public static int getWidth(){
        Dimension screenSize   =   Toolkit.getDefaultToolkit().getScreenSize();
        return  (int)screenSize.getWidth();
    }
    public static int getHeight(){
        Dimension screenSize   =   Toolkit.getDefaultToolkit().getScreenSize();
        return (int)screenSize.getHeight();
    }

}
