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
    private final AuthManager authManager;

    public MainFrame(LeaguePanel leaguePanel, AuthManager authManager) {
        this.leaguePanel = leaguePanel;
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

        tabbedPane.addTab("Лиги", leaguePanel);
        tabbedPane.addTab("Команды", createTeamsPanel());
        tabbedPane.addTab("Игроки", createPlayersPanel());

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
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
