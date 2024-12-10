package ru.mendeleev.hockey.gui;

import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.IPlayerDao;
import ru.mendeleev.hockey.dao.interfaces.IPlayerRoleDao;
import ru.mendeleev.hockey.editClasses.PlayerEdit;
import ru.mendeleev.hockey.entity.Player;
import ru.mendeleev.hockey.entity.PlayerRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class PlayerPanel extends JPanel {
    private final PlayerTableModel tableModel = new PlayerTableModel();
    private final JTable table = new JTable(tableModel);

    private final IPlayerDao playerDao;
    private final IPlayerRoleDao playerRoleDao;

    private List<PlayerRole> playerRoles;

    public PlayerPanel(IPlayerDao playerDao, IPlayerRoleDao playerRoleDao) {
        this.playerDao = playerDao;
        this.playerRoleDao = playerRoleDao;
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createPlayerToolBar(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        refreshTableData();
    }

    private JToolBar createPlayerToolBar() {
        JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);

        toolBar.setFloatable(false);
        toolBar.add(new JButton(new PlayerPanel.AddPlayerAction()));
        toolBar.add(new JButton(new PlayerPanel.EditPlayerAction()));
        toolBar.add(new JButton(new PlayerPanel.RemovePlayerAction()));

        return toolBar;
    }

    private void refreshTableData() {
        List<Player> allPlayers = playerDao.findAll();
        tableModel.initWith(allPlayers);
        table.revalidate();
        table.repaint();
    }

    private class AddPlayerAction extends AbstractAction {
        AddPlayerAction() {
            putValue(SHORT_DESCRIPTION, "Добавить игрока");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            playerRoles = playerRoleDao.findAll();
            EditPlayerDialog editPlayerDialog = new EditPlayerDialog(playerRoles, newPlayer -> {
                playerDao.save(newPlayer);
                refreshTableData();
            });
            editPlayerDialog.setLocationRelativeTo(PlayerPanel.this);
            editPlayerDialog.setVisible(true);
        }
    }

    private class EditPlayerAction extends AbstractAction {
        EditPlayerAction() {
            putValue(SHORT_DESCRIPTION, "Изменить игрока");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        PlayerPanel.this,
                        "Для редaктирования выберите игрока!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedPlayerId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedPlayerName = (String) tableModel.getValueAt(selectedRowIndex, 1);
            String selectedPlayerSurName = (String) tableModel.getValueAt(selectedRowIndex, 2);
            Integer selectedPlayerRoleId = (Integer) tableModel.getValueAt(selectedRowIndex, 3);
            Integer selectedPlayerAge = (Integer) tableModel.getValueAt(selectedRowIndex, 4);
            Integer selectedPlayerCountGames = (Integer) tableModel.getValueAt(selectedRowIndex, 5);
            Integer selectedPlayerIdCountPoints = (Integer) tableModel.getValueAt(selectedRowIndex, 6);
            Integer selectedPlayerEffectiveness = (Integer) tableModel.getValueAt(selectedRowIndex, 7);
            Integer selectedPlayerNumber = (Integer) tableModel.getValueAt(selectedRowIndex, 8);

            PlayerEdit playerEdit = new PlayerEdit(
                    selectedPlayerName,
                    selectedPlayerSurName,
                    selectedPlayerAge,
                    selectedPlayerRoleId,
                    selectedPlayerCountGames,
                    selectedPlayerIdCountPoints,
                    selectedPlayerEffectiveness,
                    selectedPlayerNumber
            );
            playerRoles = playerRoleDao.findAll();

            EditPlayerDialog editPlayerDialog = new EditPlayerDialog(playerRoles, playerEdit, changedPlayer -> {
                playerDao.update(selectedPlayerId, changedPlayer);
                refreshTableData();
            });
            editPlayerDialog.setLocationRelativeTo(PlayerPanel.this);
            editPlayerDialog.setVisible(true);
        }
    }

    private class RemovePlayerAction extends AbstractAction {
        RemovePlayerAction() {
            putValue(SHORT_DESCRIPTION, "Удалить игрока");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        PlayerPanel.this,
                        "Для удаления выберите игрока!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedPlayerId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedPlayerName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            if (JOptionPane.showConfirmDialog(
                    PlayerPanel.this,
                    "Удалить игрока '" + selectedPlayerName + "'?",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                playerDao.deletePlayerById(selectedPlayerId);
                refreshTableData();
            }
        }
    }
}
