package ru.mendeleev.hockey.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.ILeagueDao;
import ru.mendeleev.hockey.entity.League;
import ru.mendeleev.hockey.service.AuthManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class LeaguePanel extends JPanel {

    private final LeagueTableModel tableModel = new LeagueTableModel();
    private final JTable table = new JTable(tableModel);
    private final JTextField filterField = new JTextField();
    private final ILeagueDao leagueDao;

    @Autowired
    private TeamPanel teamPanel;

    @Autowired
    private AuthManager authManager;

    public LeaguePanel(ILeagueDao leagueDao) {
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

        refreshTableData();
    }

    private JToolBar createLeagueToolBar() {
        JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);

        toolBar.setFloatable(false);
        toolBar.add(new JButton(new AddLeagueAction()));
        toolBar.add(new JButton(new EditLeagueAction()));
        toolBar.add(new JButton(new RemoveLeagueAction()));

        toolBar.add(new JLabel("Название"));
        toolBar.add(filterField);

        toolBar.add(new JButton(new LeaguePanel.FilterTeamAction()));

        return toolBar;
    }

    public void refreshTableData() {
        List<League> allLeagues = leagueDao.findAll();
        tableModel.initWith(allLeagues);
        table.revalidate();
        table.repaint();
    }

    private class AddLeagueAction extends AbstractAction {
        AddLeagueAction() {
            putValue(SHORT_DESCRIPTION, "Добавить лигу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EditLeagueDialog editLeagueDialog = new EditLeagueDialog(newLeagueName -> {
                leagueDao.save(newLeagueName);
                refreshTableData();
                teamPanel.refreshTableData();
            });
            editLeagueDialog.setLocationRelativeTo(LeaguePanel.this);
            editLeagueDialog.setVisible(true);
        }
    }

    private class EditLeagueAction extends AbstractAction {
        EditLeagueAction() {
            putValue(SHORT_DESCRIPTION, "Изменить лигу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        LeaguePanel.this,
                        "Для редпктирования выберите лигу!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedLeagueId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedLeagueName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            EditLeagueDialog editLeagueDialog = new EditLeagueDialog(selectedLeagueName, changedLeagueName -> {
                leagueDao.update(selectedLeagueId, changedLeagueName);
                refreshTableData();
                teamPanel.refreshTableData();
            });
            editLeagueDialog.setLocationRelativeTo(LeaguePanel.this);
            editLeagueDialog.setVisible(true);
        }
    }

    private class RemoveLeagueAction extends AbstractAction {
        RemoveLeagueAction() {
            putValue(SHORT_DESCRIPTION, "Удалить лигу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        LeaguePanel.this,
                        "Для удаления выберите лигу!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedLeagueId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedLeagueName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            boolean containLeagueId = leagueDao.isContainAnyTeam(selectedLeagueId);
            if (containLeagueId) {
                JOptionPane.showMessageDialog(
                        LeaguePanel.this,
                        "Нельзя удалять лигу, к которой уже относятся какие-то команды!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
            } else if (JOptionPane.showConfirmDialog(
                    LeaguePanel.this,
                    "Удалить лигу '" + selectedLeagueName + "'?",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                leagueDao.deleteLeagueById(selectedLeagueId);
                refreshTableData();
                teamPanel.refreshTableData();
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

        }
    }
}
