package ru.mendeleev.hockey.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.ILeagueDao;
import ru.mendeleev.hockey.dao.interfaces.ITeamDao;
import ru.mendeleev.hockey.dao.interfaces.ICityDao;
import ru.mendeleev.hockey.editClasses.TeamEdit;
import ru.mendeleev.hockey.entity.Team;
import ru.mendeleev.hockey.editClasses.TeamLists;
import ru.mendeleev.hockey.editClasses.FullTeam;
import ru.mendeleev.hockey.service.AuthManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class TeamPanel extends JPanel {

    private final TeamTableModel tableModel = new TeamTableModel();
    private final JTable table = new JTable(tableModel);

    private TeamLists teamList = new TeamLists();

    private final ITeamDao teamDao;
    private final ICityDao cityDao;
    private final ILeagueDao leagueDao;

    @Autowired
    private PlayerPanel playerPanel;

    @Autowired
    private LeaguePanel leaguePanel;

    @Autowired
    private AuthManager authManager;

    public TeamPanel(ITeamDao teamDao, ICityDao cityDao, ILeagueDao leagueDao) {
        this.teamDao = teamDao;
        this.cityDao = cityDao;
        this.leagueDao = leagueDao;
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

    private JToolBar createLeagueToolBar() {
        JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);

        toolBar.setFloatable(false);
        toolBar.add(new JButton(new TeamPanel.AddTeamAction()));
        toolBar.add(new JButton(new TeamPanel.EditTeamAction()));
        toolBar.add(new JButton(new TeamPanel.RemoveTeamAction()));

        return toolBar;
    }

    public void refreshTableData() {
        List<FullTeam> allFullTeams = teamDao.findFullAll();
        tableModel.initWith(allFullTeams);
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
            String selectedLeague = (String) tableModel.getValueAt(selectedRowIndex, 3);
            String selectedCity = (String) tableModel.getValueAt(selectedRowIndex, 5);

            TeamEdit teamEdit = new TeamEdit(selectedTeamName, selectedLeague, selectedCity);
            teamList.setLeagueList(leagueDao.findAll());
            teamList.setCityList(cityDao.findAll());
            EditTeamDialog editTeamDialog = new EditTeamDialog(teamList, teamEdit, newTeamEdit -> {
                teamDao.update(selectedTeamId, newTeamEdit);
                refreshTableData();
                playerPanel.refreshTableData();
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
            }
        }
    }
}
