package ru.mendeleev.hockey.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class EditPlayerFrame extends JFrame {
    private static final String TITLE = "Добавление игрока";

    private final JTextField nameField = new JTextField();

    private final String prevPlayerName;
    private final Consumer<String> newPlayerNameConsumer;

    public EditPlayerFrame(Consumer<String> newPlayerNameConsumer) {
        this(null, newPlayerNameConsumer);
    }

    public EditPlayerFrame(String prevPlayerName, Consumer<String> newPlayerNameConsumer) {
        this.prevPlayerName = prevPlayerName;
        this.newPlayerNameConsumer = newPlayerNameConsumer;

        setTitle(TITLE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new BorderLayout());

        fieldsPanel.add(new JLabel("Название: "), BorderLayout.WEST);
        if (prevPlayerName != null) {
            nameField.setText(prevPlayerName);
        }
        fieldsPanel.add(nameField, BorderLayout.CENTER);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(new JButton(new EditPlayerFrame.SaveAction()), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(360, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevPlayerName != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = nameField.getText();
            if (text == null || text.isEmpty()) {
                JOptionPane.showMessageDialog(
                        EditPlayerFrame.this,
                        "Введите название игрока!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            newPlayerNameConsumer.accept(text);
            dispose();
        }
    }
}
