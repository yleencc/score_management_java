package cc.yleen.gui.component;

import cc.yleen.config.Theme;

import javax.swing.*;
import java.awt.*;

public class DefaultComponents {
    public static JLabel getJLabel(String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(Theme.Font_.NORMAL);
        return jLabel;
    }

    public static JLabel getJLabel(String text, Font font) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(font);
        return jLabel;
    }

    public static JLabel getJLabel(String text, Font font, Color color) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(font);
        jLabel.setForeground(color);
        return jLabel;
    }
}
