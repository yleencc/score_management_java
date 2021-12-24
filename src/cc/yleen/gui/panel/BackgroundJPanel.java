package cc.yleen.gui.panel;

import cc.yleen.utils.ImgUtil;

import javax.swing.*;
import java.awt.*;

public class BackgroundJPanel extends JPanel {
    /**
     * This is a JPanel  just to set a background in GUI.
     * */
    private Image bg;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = this.getParent().getSize();
        Graphics2D g2 = (Graphics2D) g;
//        g.drawOval(100,100,100,100);
        g2.drawImage(bg,
                0,0,(int)size.getWidth(),(int)size.getHeight(),null);
    }

    public void setBg(String filePath) {
        this.bg = ImgUtil.getImage(filePath);
    }
}