package ru.mendeleev.hockey.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import ru.mendeleev.hockey.editClasses.TeamEdit;
import ru.mendeleev.hockey.entity.City;
import ru.mendeleev.hockey.entity.League;
import java.util.List;

public class EditTeamFrame extends JFrame {
    private static final String TITLEADD = "Добавление команды";
    private static final String TITLEEDIT = "Редактирование команды";
    private final JTextField nameField = new JTextField();
    private JComboBox league = new JComboBox();
    private JComboBox city = new JComboBox();

    private List<City> cityList;
    private List<League> leagueList;

    private final TeamEdit prevData;
    private final Consumer<TeamEdit> newTeamConsumer;

    public EditTeamFrame(List<League> leagueList, List<City> cityList, Consumer<TeamEdit> newTeamConsumer) { this(leagueList, cityList,null, newTeamConsumer); }

    public EditTeamFrame(List<League> leagueList, List<City> cityList, TeamEdit prevData, Consumer<TeamEdit> newTeamConsumer) {
        this.prevData = prevData;
        this.newTeamConsumer = newTeamConsumer;
        this.cityList = cityList;
        this.leagueList = leagueList;

        for (int i = 0; i < leagueList.size(); i++) {
            league.addItem(leagueList.get(i).getId());
        }

        for (int i = 0; i < cityList.size(); i++) {
            city.addItem(cityList.get(i).getId());
        }

        if (prevData != null) {setTitle(TITLEEDIT);}
        else setTitle(TITLEADD);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel cityPanel = new JPanel(new BorderLayout());
        JPanel leaguePanel = new JPanel(new BorderLayout());

        namePanel.add(new JLabel("Название: "), BorderLayout.WEST);
        leaguePanel.add(new JLabel("Лига:     "), BorderLayout.WEST);
        cityPanel.add(new JLabel("Город:    "), BorderLayout.WEST);

        if (prevData != null) {
            nameField.setText(prevData.getName());
            league.setSelectedItem(prevData.getLeagueName());
            city.setSelectedItem(prevData.getCity());
        }

        namePanel.add(nameField, BorderLayout.CENTER);
        leaguePanel.add(league, BorderLayout.CENTER);
        cityPanel.add(city, BorderLayout.CENTER);

        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(namePanel, BorderLayout.NORTH);
        subPanel.add(leaguePanel, BorderLayout.CENTER);
        subPanel.add(cityPanel, BorderLayout.SOUTH);

        mainPanel.add(subPanel, BorderLayout.CENTER);
        mainPanel.add(new JButton(new EditTeamFrame.SaveAction()), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevData != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (nameField.getText() == null || league.getSelectedItem() == null || city.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(
                        EditTeamFrame.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            TeamEdit teamEdit = new TeamEdit(nameField.getText(),  (Integer) league.getSelectedItem(), (Integer) city.getSelectedItem());
            newTeamConsumer.accept(teamEdit);
            dispose();
        }
    }
}
