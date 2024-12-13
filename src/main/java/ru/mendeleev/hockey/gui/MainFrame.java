package ru.mendeleev.hockey.gui;

import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.service.AuthManager;
import ru.mendeleev.hockey.utils.Beans;
import ru.mendeleev.hockey.utils.CommonUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

@Component
public final class MainFrame extends JFrame {

    private static final String TITLE = "Hockey";

    private final LeaguePanel leaguePanel;
    private final TeamPanel teamPanel;
    private final PlayerPanel playerPanel;
    private final AuthManager authManager;
    private final LogInDialog logInDialog;

    public MainFrame(LeaguePanel leaguePanel, AuthManager authManager, TeamPanel teamPanel, PlayerPanel playerPanel, LogInDialog logInDialog) {
        this.leaguePanel = leaguePanel;
        this.teamPanel = teamPanel;
        this.playerPanel = playerPanel;
        this.authManager = authManager;
        this.logInDialog = logInDialog;
    }

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
        JButton authorization = new JButton("Войти");
        menuBar.add(authorization);

        authorization.addActionListener(e -> {
            if (!authManager.isLoggedIn()) {
                logInDialog.setLocationRelativeTo(MainFrame.this);
                logInDialog.setVisible(true);
                if (authManager.isLoggedIn()) authorization.setText("Выйти");
            } else {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Вы действительно хотите выйти?",
                        "Вопрос",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    authManager.setLoggedIn(false);
                    playerPanel.refreshTableData();
                    teamPanel.refreshTableData();
                    leaguePanel.refreshTableData();
                    authorization.setText("Войти");
                }
            }
        });

        return menuBar;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Игроки", playerPanel);
        tabbedPane.addTab("Команды", teamPanel);
        tabbedPane.addTab("Лиги", leaguePanel);

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

}
