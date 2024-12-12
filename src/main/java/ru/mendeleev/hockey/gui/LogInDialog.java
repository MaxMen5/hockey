package ru.mendeleev.hockey.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LogInDialog extends JDialog {

    private static final String TITLE = "Авторизация";

    private final JTextField loginField = new JTextField();
    private final JTextField passwordField = new JTextField();

    public LogInDialog() {
        setTitle(TITLE);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));

        JPanel loginPanel = new JPanel(new BorderLayout());
        JPanel passwordPanel = new JPanel(new BorderLayout());

        loginPanel.add(new JLabel("Логин: "), BorderLayout.WEST);
        loginPanel.add(loginField, BorderLayout.CENTER);

        passwordPanel.add(new JLabel("Пароль: "), BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        mainPanel.add(loginPanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(new JButton(new ButtonAction()));

        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class ButtonAction extends AbstractAction {
        ButtonAction() {
            putValue(NAME,  "Авторизоваться");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (loginField.getText().equals("login") || passwordField.getText().equals("password")) {
                // TODO
                dispose();
            }
            else {
                loginField.setText("");
                passwordField.setText("");
            }
        }
    }

}
