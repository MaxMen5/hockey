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
    private final PlayerPanel playersPanel;
    private final AuthManager authManager;

    public MainFrame(LeaguePanel leaguePanel, AuthManager authManager, TeamPanel teamPanel, PlayerPanel playerPanel) {
        this.leaguePanel = leaguePanel;
        this.teamPanel = teamPanel;
        this.playersPanel = playerPanel;
        this.authManager = authManager;
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

        if (!authManager.isLoggedIn()) {
            authorization.addActionListener(e -> {
                LogInDialog logInDialog = new LogInDialog();
                logInDialog.setLocationRelativeTo(MainFrame.this);
                logInDialog.setVisible(true);

                if (authManager.isLoggedIn()) authorization.setText("Выйти");
            });
        } else { authorization.addActionListener(e -> {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Вы действительно хотите выйти?",
                        "Вопрос",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    authManager.setLoggedIn(false);
                    authorization.setText("Войти");
                }
            });
        }

        return menuBar;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Игроки", playersPanel);
        tabbedPane.addTab("Команды", teamPanel);
        tabbedPane.addTab("Лиги", leaguePanel);

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

}
