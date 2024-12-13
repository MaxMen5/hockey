package ru.eltech.hockey.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eltech.hockey.dao.interfaces.ICityDao;
import ru.eltech.hockey.dao.interfaces.ILeagueDao;
import ru.eltech.hockey.dao.interfaces.IPlayerDao;
import ru.eltech.hockey.dao.interfaces.ITeamDao;
import ru.eltech.hockey.editClasses.TeamEdit;
import ru.eltech.hockey.editClasses.TeamFilter;
import ru.eltech.hockey.editClasses.TeamLists;
import ru.eltech.hockey.entity.City;
import ru.eltech.hockey.entity.League;
import ru.eltech.hockey.entity.Player;
import ru.eltech.hockey.entity.Team;
import ru.eltech.hockey.service.AuthManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class TeamPanel extends JPanel {

    private final TeamTableModel tableModel = new TeamTableModel();
    private final JTable table = createTable();

    private final JTextField filterNameField = new JTextField();
    private final JTextField filterLeagueField = new JTextField();
    private final JTextField filterCityField = new JTextField();

    private TeamLists teamList = new TeamLists();
    private final ITeamDao teamDao;
    private final ICityDao cityDao;
    private final ILeagueDao leagueDao;
    private final IPlayerDao playerDao;

    @Autowired
    private PlayerPanel playerPanel;

    @Autowired
    private LeaguePanel leaguePanel;

    private AuthManager authManager;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    public TeamPanel(ITeamDao teamDao, ICityDao cityDao, ILeagueDao leagueDao, IPlayerDao playerDao, AuthManager authManager) {
        this.teamDao = teamDao;
        this.cityDao = cityDao;
        this.leagueDao = leagueDao;
        this.playerDao = playerDao;
        this.authManager = authManager;
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createLeagueToolBar(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(1));
        table.removeColumn(table.getColumnModel().getColumn(2));

        refreshTableData();
    }

    private JTable createTable() {
        JTable table = new JTable(tableModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int column = columnAtPoint(p);

                Object value = getValueAt(row, column);
                if (value == null) {
                    return null;
                }

                String strValue = value.toString();
                if (!strValue.isEmpty()) {
                    return "<html>" + strValue.replaceAll(",", "<br>") + "</html>";
                }

                return (String) value;
            }
        };
        return table;
    }

    private JToolBar createLeagueToolBar() {
        JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);

        toolBar.setFloatable(false);
        toolBar.setFloatable(false);
        addButton = new JButton(new AddTeamAction());
        addButton.setEnabled(false);
        toolBar.add(addButton);
        editButton = new JButton(new EditTeamAction());
        editButton.setEnabled(false);
        toolBar.add(editButton);
        removeButton = new JButton(new RemoveTeamAction());
        removeButton.setEnabled(false);
        toolBar.add(removeButton);

        toolBar.add(new JLabel("   Название: "));
        toolBar.add(filterNameField);
        filterNameField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Лига: "));
        toolBar.add(filterLeagueField);
        filterLeagueField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Город: "));
        toolBar.add(filterCityField);
        filterCityField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JButton(new FilterTeamAction()));

        return toolBar;
    }

    public void refreshTableData() {
        boolean isLoggedIn = authManager.isLoggedIn();
        addButton.setEnabled(isLoggedIn);
        editButton.setEnabled(isLoggedIn);
        removeButton.setEnabled(isLoggedIn);
        TeamFilter teamFilter = new TeamFilter(filterNameField.getText(), filterLeagueField.getText(), filterCityField.getText());
        List<Team> allTeams = teamDao.findAll(teamFilter);
        tableModel.initWith(allTeams);
        table.revalidate();
        table.repaint();
    }

    private class AddTeamAction extends AbstractAction {
        AddTeamAction() {
            putValue(SHORT_DESCRIPTION, "Добавить команду");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            teamList.setLeagueList(leagueDao.findAll());
            teamList.setCityList(cityDao.findAll());
            teamList.setPlayerList(playerDao.findAllPlayers());

            EditTeamDialog editTeamDialog = new EditTeamDialog(teamList, newTeam -> {
                teamDao.save(newTeam);
                refreshTableData();
                playerPanel.refreshTableData();
                leaguePanel.refreshTableData();
            });
            editTeamDialog.setLocationRelativeTo(TeamPanel.this);
            editTeamDialog.setVisible(true);
        }
    }

    private class EditTeamAction extends AbstractAction {
        EditTeamAction() {
            putValue(SHORT_DESCRIPTION, "Изменить команду");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        TeamPanel.this,
                        "Для редактирования выберите команду!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Integer selectedTeamId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedTeamName = (String) tableModel.getValueAt(selectedRowIndex, 1);
            League selectedLeague = new League();
            selectedLeague.setId((Integer) tableModel.getValueAt(selectedRowIndex, 2));
            selectedLeague.setName((String) tableModel.getValueAt(selectedRowIndex, 3));
            City selectedCity = new City();
            selectedCity.setId((Integer) tableModel.getValueAt(selectedRowIndex, 4));
            selectedCity.setName((String) tableModel.getValueAt(selectedRowIndex, 5));

            List<Player> currentList = playerDao.findTeamPlayers(selectedTeamId);

            TeamEdit teamEdit = new TeamEdit(selectedTeamName, selectedLeague, selectedCity, currentList);
            teamList.setLeagueList(leagueDao.findAll());
            teamList.setCityList(cityDao.findAll());
            teamList.setPlayerList(playerDao.findAllNotInTeam(selectedTeamId));

            EditTeamDialog editTeamDialog = new EditTeamDialog(teamList, teamEdit, newTeamEdit -> {
                teamDao.update(selectedTeamId, newTeamEdit);
                refreshTableData();
                playerPanel.refreshTableData();
                leaguePanel.refreshTableData();
            });
            editTeamDialog.setLocationRelativeTo(TeamPanel.this);
            editTeamDialog.setVisible(true);
        }
    }

    private class RemoveTeamAction extends AbstractAction {
        RemoveTeamAction() {
            putValue(SHORT_DESCRIPTION, "Удалить команду");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        TeamPanel.this,
                        "Для удаления выберите команду!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedTeamId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedTeamName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            if (JOptionPane.showConfirmDialog(
                    TeamPanel.this,
                    "Удалить команду '" + selectedTeamName + "'?",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                teamDao.deleteTeamById(selectedTeamId);
                refreshTableData();
                playerPanel.refreshTableData();
                leaguePanel.refreshTableData();
            }
        }
    }

    private class FilterTeamAction extends AbstractAction {
        FilterTeamAction() {
            putValue(SHORT_DESCRIPTION, "Фильтровать команды");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_refresh.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTableData();
        }
    }
}
