package me.xueyao.controller;

import javax.swing.*;

/**
 * @author Simon.Xue
 * @date 2019-12-30 16:49
 **/
public class DialogController {

    public DialogController() {

    }

    public void defaultDialog(String title, String content) {
        JDialog dialog = new JDialog();
        dialog.setSize(300, 400);
        dialog.setTitle(title);
        JPanel dialogPanel = new JPanel();
        dialogPanel.add(new JLabel(content));
        dialog.setContentPane(dialogPanel);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void defaultTitleDialog(String title) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setLayout(null);
        dialog.setVisible(true);
    }
}
