package ru.mendeleev.hockey.gui;

import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.utils.CommonUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

/**
 * Главная форма приложения.
 */
@Component
public final class MainFrame extends JFrame {

    private static final String TITLE = "Hockey";

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
        setJMenuBar(createMenuBar()); // TODO
        getContentPane().add(createTabbedPane(), BorderLayout.CENTER);
    }

    /**
     * Добавление панелей с разделителями.
     */
    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Лиги", new LeaguePanel());
        tabbedPane.addTab("Команды", createTeamsPanel());
        tabbedPane.addTab("Игроки", createPlayersPanel());

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

    private JPanel createTeamsPanel() {
        return new JPanel(); // TODO
    }

    private JPanel createPlayersPanel() {
        return new JPanel(); // TODO
    }

    /**
     * Создание меню формы.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //File
//        JMenu fileMenu = new JMenu(i18n("mainFrame.menu.file"));
//        menuBar.add(fileMenu);
//
//        if (userAccessManager.canWatchTableData()) {
//            JMenuItem sqlExecutionItem = new JMenuItem(i18n("mainFrame.menu.file.sqlExecution"));
//            sqlExecutionItem.addActionListener(e -> {
//                SqlExecutionFrame sqlExecutionFrame = Beans.getBean(SqlExecutionFrame.class);
//                sqlExecutionFrame.setVisible(true);
//            });
//            fileMenu.add(sqlExecutionItem);
//        }
//
//        if (userAccessManager.canRegisterUsers()) {
//            JMenuItem registrationItem = new JMenuItem(i18n("mainFrame.menu.file.registration"));
//            registrationItem.addActionListener(e -> Beans.getBean(RegistrationFrame.class));
//            fileMenu.add(registrationItem);
//        }
//
//        if (userAccessManager.canManageUserRights()) {
//            JMenuItem rightsManagementItem = new JMenuItem(i18n("mainFrame.menu.file.rightsManagement"));
//            rightsManagementItem.addActionListener(e -> {
//                UserAccessFrame userAccessFrame = Beans.getBean(UserAccessFrame.class);
//                userAccessFrame.init();
//            });
//            fileMenu.add(rightsManagementItem);
//        }
//
//        JMenuItem logOutItem = new JMenuItem(i18n("mainFrame.menu.file.logOut"));
//        logOutItem.addActionListener(e -> {
//            if (JOptionPane.showConfirmDialog(
//                    MainFrame.this,
//                    i18n("mainFrame.menu.file.logOut.confirmation.message"),
//                    i18n("mainFrame.menu.file.logOut.confirmation.title"),
//                    JOptionPane.YES_NO_OPTION,
//                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//                userAccessManager.setCurrentUser(null);
//                Beans.getBean(AuthenticationFrame.class);
//                dispose();
//            }
//        });
//        fileMenu.add(logOutItem);
//
//        fileMenu.addSeparator();
//
//        JMenuItem exitItem = new JMenuItem(i18n("mainFrame.menu.file.exit"));
//        exitItem.addActionListener(e -> dispose());
//        fileMenu.add(exitItem);
//
//        //Help
//        JMenu helpMenu = new JMenu(i18n("mainFrame.menu.help"));
//        menuBar.add(helpMenu);
//
//        JMenuItem aboutItem = new JMenuItem(i18n("mainFrame.menu.help.about")); //TODO add here information about application!
//        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
//                MainFrame.this,
//                i18n("mainFrame.menu.help.about.message"),
//                i18n("mainFrame.menu.help.about.title"),
//                JOptionPane.INFORMATION_MESSAGE));
//        helpMenu.add(aboutItem);

        return menuBar;
    }

}
