package ru.mendeleev.hockey.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class EditLeagueDialog extends JDialog {

    private static final String TITLE = "Добавление лиги";

    private final JTextField nameField = new JTextField();

    private final String prevLeagueName;
    private final Consumer<String> newLeagueNameConsumer;

    public EditLeagueDialog(Consumer<String> newLeagueNameConsumer) {
        this(null, newLeagueNameConsumer);
    }

    public EditLeagueDialog(String prevLeagueName, Consumer<String> newLeagueNameConsumer) {
        this.prevLeagueName = prevLeagueName;
        this.newLeagueNameConsumer = newLeagueNameConsumer;

        setTitle(TITLE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new BorderLayout());

        fieldsPanel.add(new JLabel("Название: "), BorderLayout.WEST);
        if (prevLeagueName != null) {
            nameField.setText(prevLeagueName);
        }
        fieldsPanel.add(nameField, BorderLayout.CENTER);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(new JButton(new SaveAction()), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(360, 100);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevLeagueName != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = nameField.getText();
            if (text == null || text.isEmpty()) {
                JOptionPane.showMessageDialog(
                        EditLeagueDialog.this,
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
