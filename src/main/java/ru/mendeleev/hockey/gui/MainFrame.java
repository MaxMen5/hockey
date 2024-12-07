package ru.mendeleev.hockey.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.service.AuthManager;
import ru.mendeleev.hockey.utils.Beans;
import ru.mendeleev.hockey.utils.CommonUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;

@Component
public final class MainFrame extends JFrame {

    private static final String TITLE = "Hockey";

    @Autowired
    private AuthManager authManager;

    @PostConstruct
    public void init() {
        setTitle(TITLE);
        createGUI();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Вы действительно хотите выйти?",
                        "Выйти?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(CommonUtils.prepareWindowSizeWithShifts(0, 40));
        setVisible(true);
    }

    private void createGUI() {
        setJMenuBar(createMenuBar());
        getContentPane().add(createTabbedPane(), BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //File
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        if (authManager.isLoggedIn()) {
            JMenuItem logOutItem = new JMenuItem("Выйти");
            logOutItem.addActionListener(e -> {
                LogOutFrame logOutFrame = Beans.getBean(LogOutFrame.class);
                logOutFrame.setVisible(true);
            });
            fileMenu.add(logOutItem);
        } else {
            JMenuItem logInItem = new JMenuItem("Войти");
            logInItem.addActionListener(e -> {
                LogInFrame logInFrame = Beans.getBean(LogInFrame.class);
                logInFrame.setVisible(true);
            });
            fileMenu.add(logInItem);
        }

        return menuBar;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Лиги", createLeaguePanel());
        tabbedPane.addTab("Команды", createTeamsPanel());
        tabbedPane.addTab("Игроки", createPlayersPanel());

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

    // LEAGUE ==========================================================================================================

    private JPanel createLeaguePanel() {
        JPanel leaguePanel = new JPanel();

        leaguePanel.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createLeagueToolBar(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(createLeagueTable()), BorderLayout.CENTER);

        leaguePanel.add(northPanel, BorderLayout.NORTH);
        leaguePanel.add(centerPanel, BorderLayout.CENTER);

        return leaguePanel;
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

    // TEAM ==========================================================================================================

    private JPanel createTeamsPanel() {
        JPanel jPanel = new JPanel();

        jPanel.setBackground(Color.RED);

        return jPanel; // TODO
    }

    // PLAYER ==========================================================================================================

    private JPanel createPlayersPanel() {
        JPanel jPanel = new JPanel();

        jPanel.setBackground(Color.GREEN);

        return jPanel; // TODO
    }

}
