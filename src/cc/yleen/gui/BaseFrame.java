package cc.yleen.gui;

import cc.yleen.gui.panel.BackgroundJPanel;
import cc.yleen.utils.ImgUtil;

import javax.swing.*;

public class BaseFrame extends JFrame {
    protected int width = 600;
    protected int height = 400;
    protected BackgroundJPanel bgPanel; // 底面板、基本面板

    public BaseFrame() {
    }

    protected void showOperateSuccessful() {
        JOptionPane.showMessageDialog(null,
                "操作成功",
                "提示", JOptionPane.DEFAULT_OPTION,
                new ImageIcon(ImgUtil.getImage("cc/yleen/images/ok.png")));
    }
    protected void showSaveSuccessful() {
        JOptionPane.showMessageDialog(null,
                "保存成功",
                "提示", JOptionPane.DEFAULT_OPTION,
                new ImageIcon(ImgUtil.getImage("cc/yleen/images/ok.png")));
    }
    protected void showSaveFailed() {
        JOptionPane.showMessageDialog(null,
                "保存失败！请检查格式或联系管理员。",
                "提示", JOptionPane.ERROR_MESSAGE);
    }

    protected void showRefreshSuccessful() {
        JOptionPane.showMessageDialog(null,
                "刷新成功",
                "提示", JOptionPane.DEFAULT_OPTION,
                new ImageIcon(ImgUtil.getImage("cc/yleen/images/ok.png")));
    }

    protected void showRequestFailed(Exception e) {
        JOptionPane.showMessageDialog(null,
                "获取数据失败！请检查网络。" + e.getMessage(),
                "提示", JOptionPane.ERROR_MESSAGE);
        System.out.println(e.getMessage());
    }

    protected void showExecuteFailed(Exception e) {
        JOptionPane.showMessageDialog(null,
                "执行操作失败！请检查网络。" + e.getMessage(),
                "提示", JOptionPane.ERROR_MESSAGE);
        System.out.println(e.getMessage());
    }

    protected void startLoading() {

    }
}
