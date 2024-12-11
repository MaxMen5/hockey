package ru.mendeleev.hockey.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import ru.mendeleev.hockey.editClasses.TeamEdit;
import ru.mendeleev.hockey.editClasses.TeamLists;

import static ru.mendeleev.hockey.utils.CommonUtils.isBlank;

public class EditTeamDialog extends JDialog {

    private static final String TITLEADD = "Добавление команды";
    private static final String TITLEEDIT = "Редактирование команды";

    private final JTextField nameField = new JTextField();
    private JComboBox league = new JComboBox();
    private JComboBox city = new JComboBox();

    private final TeamLists teamList;

    private final TeamEdit prevData;
    private final Consumer<TeamEdit> newTeamConsumer;

    public EditTeamDialog(TeamLists teamList, Consumer<TeamEdit> newTeamConsumer) { this(teamList, null, newTeamConsumer); }

    public EditTeamDialog(TeamLists teamList, TeamEdit prevData, Consumer<TeamEdit> newTeamConsumer) {
        this.prevData = prevData;
        this.newTeamConsumer = newTeamConsumer;
        this.teamList = teamList;

        for (int i = 0; i < teamList.getLeagueList().size(); i++) {
            league.addItem(teamList.getLeagueList().get(i).getName());
        }

        for (int i = 0; i < teamList.getCityList().size(); i++) {
            city.addItem(teamList.getCityList().get(i).getName());
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
        mainPanel.add(new JButton(new EditTeamDialog.SaveAction()), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(400, 150);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevData != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isBlank(nameField.getText()) ||
                    league.getSelectedItem() == null ||
                    city.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(
                        EditTeamDialog.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            TeamEdit teamEdit = new TeamEdit(nameField.getText(), (String) league.getSelectedItem(), (String) city.getSelectedItem());
            newTeamConsumer.accept(teamEdit);
            dispose();
        }
    }
}
