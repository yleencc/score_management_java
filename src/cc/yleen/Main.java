package cc.yleen;

import cc.yleen.gui.frame.LoginFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LoginFrame());
    }
}
