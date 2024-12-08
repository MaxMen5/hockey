package ru.mendeleev.hockey.gui;

import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.ITeamDao;
import ru.mendeleev.hockey.entity.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class TeamPanel extends JPanel {

    private final TeamTableModel tableModel = new TeamTableModel();
    private final JTable table = new JTable(tableModel);

    private final ITeamDao teamDao;

    public TeamPanel(ITeamDao teamDao) {
        this.teamDao = teamDao;
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

    private void refreshTableData() {
        List<Team> allTeams = teamDao.findAll();
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
            EditTeamFrame editTeamFrame = new EditTeamFrame(newTeamName -> {
                teamDao.save(newTeamName);
                refreshTableData();
            });
            editTeamFrame.setLocationRelativeTo(TeamPanel.this);
            editTeamFrame.setVisible(true);
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
            String selectedLeagueId = (String) tableModel.getValueAt(selectedRowIndex, 2);
            String selectedCityID = (String) tableModel.getValueAt(selectedRowIndex, 3);

            EditTeamFrame editTeamFrame = new EditTeamFrame(selectedTeamName, selectedLeagueId, selectedCityID, changedTeamName -> {
                teamDao.update(selectedTeamId, changedTeamName);
                refreshTableData();
            });
            editTeamFrame.setLocationRelativeTo(TeamPanel.this);
            editTeamFrame.setVisible(true);
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
            }
        }
    }
}
