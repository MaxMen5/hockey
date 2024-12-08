package ru.mendeleev.hockey.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class EditTeamFrame extends JFrame {
    private static final String TITLE = "Добавление команды";

    private final JTextField nameField = new JTextField();
    private final JTextField leagueField = new JTextField();
    private final JTextField cityField = new JTextField();

    private final String prevTeamName;
    private final String prevLeagueId;
    private final String prevCityId;
    private final Consumer<String> newTeamNameConsumer;
    //private final Consumer<Integer> newLeagueIdConsumer;
    //private final Consumer<Integer> newCityIdConsumer;

    public EditTeamFrame(Consumer<String> newTeamNameConsumer) { this(null, null, null, newTeamNameConsumer); }

    public EditTeamFrame(String prevTeamName, String prevLeagueId, String prevCityId, Consumer<String> newTeamNameConsumer) {
        this.prevTeamName = prevTeamName;
        this.prevLeagueId = prevLeagueId;
        this.prevCityId = prevCityId;
        this.newTeamNameConsumer = newTeamNameConsumer;
        //this.newLeagueIdConsumer = newLeagueIdConsumer;
        //this.newCityIdConsumer = newCityIdConsumer;

        setTitle(TITLE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel namePanel = new JPanel(new BorderLayout());

        namePanel.add(new JLabel("Название: "), BorderLayout.WEST);
        if (prevTeamName != null) {
            nameField.setText(prevTeamName);
        }
        namePanel.add(nameField, BorderLayout.CENTER);

        JPanel leaguePanel = new JPanel(new BorderLayout());

        leaguePanel.add(new JLabel("Лига:     "), BorderLayout.WEST);
        if (prevLeagueId != null) {
            leagueField.setText(prevLeagueId);
        }
        leaguePanel.add(leagueField, BorderLayout.CENTER);

        JPanel cityPanel = new JPanel(new BorderLayout());

        cityPanel.add(new JLabel("Город:    "), BorderLayout.WEST);
        if (prevCityId != null) {
            nameField.setText(prevCityId);
        }
        cityPanel.add(cityField, BorderLayout.CENTER);

        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(namePanel, BorderLayout.NORTH);
        subPanel.add(leaguePanel, BorderLayout.CENTER);
        subPanel.add(cityPanel, BorderLayout.SOUTH);

        mainPanel.add(subPanel, BorderLayout.CENTER);
        mainPanel.add(new JButton(new EditTeamFrame.SaveAction()), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(400, 133);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevTeamName != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = nameField.getText();
            if (text == null || text.isEmpty()) {
                JOptionPane.showMessageDialog(
                        EditTeamFrame.this,
                        "Введите название команды!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            newTeamNameConsumer.accept(text);
            dispose();
        }
    }
}
