package ru.mendeleev.hockey.gui;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class LeaguePanel extends JPanel {

    @PostConstruct
    public void init() {
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createLeagueToolBar(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(createLeagueTable()), BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JToolBar createLeagueToolBar() {
        JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);

        toolBar.setFloatable(false);
        toolBar.add(new JButton(new AddLeagueAction()));
        toolBar.add(new JButton(new EditLeagueAction()));
        toolBar.add(new JButton(new RemoveLeagueAction()));

        return toolBar;
    }

    private JTable createLeagueTable() {
        TableModel tableModel = new DefaultTableModel(
                new String[][]{
                        {"1", "2"},
                        {"3", "4"}
                },
                new String[]{
                        "Col1", "Col2"
                }
        );

        return new JTable(tableModel);
    }

    private class AddLeagueAction extends AbstractAction {
        AddLeagueAction() {
            putValue(SHORT_DESCRIPTION, "Добавить лигу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    private class EditLeagueAction extends AbstractAction {
        EditLeagueAction() {
            putValue(SHORT_DESCRIPTION, "Изменить лигу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    private class RemoveLeagueAction extends AbstractAction {
        RemoveLeagueAction() {
            putValue(SHORT_DESCRIPTION, "Удалить лигу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

}
