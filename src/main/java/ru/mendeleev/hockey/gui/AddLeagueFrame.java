package ru.mendeleev.hockey.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class AddLeagueFrame extends JFrame {

    private static final String TITLE = "Добавление лиги";

    private final JTextField nameField = new JTextField();

    private final Consumer<String> newLeagueNameConsumer;

    public AddLeagueFrame(Consumer<String> newLeagueNameConsumer) {
        this.newLeagueNameConsumer = newLeagueNameConsumer;

        setTitle(TITLE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new BorderLayout());

        fieldsPanel.add(new JLabel("Название: "), BorderLayout.WEST);
        fieldsPanel.add(nameField, BorderLayout.CENTER);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(new JButton(new SaveAction()), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(360, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = nameField.getText();
            if (text == null || text.isEmpty()) {
                JOptionPane.showMessageDialog(
                        AddLeagueFrame.this,
                        "Введите название лиги!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            newLeagueNameConsumer.accept(text);
            dispose();
        }
    }

}
