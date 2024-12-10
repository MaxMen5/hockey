package ru.mendeleev.hockey.gui;

import ru.mendeleev.hockey.entity.PlayerRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import java.util.List;
import ru.mendeleev.hockey.editClasses.PlayerEdit;
import ru.mendeleev.hockey.utils.CommonUtils;

import static ru.mendeleev.hockey.utils.CommonUtils.isBlank;

public class EditPlayerDialog extends JDialog {
    private static final String TITLEADD = "Добавление игрока";
    private static final String TITLEEDIT = "Редактирование игрока";

    private final JComboBox playerRoleId = new JComboBox();
    private final JTextField nameField = new JTextField();
    private final JTextField surnameField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField countGamesField = new JTextField();
    private final JTextField countPointsField = new JTextField();
    private final JTextField effectivenessField = new JTextField();
    private final JTextField playerNumberField = new JTextField();

    private final List<PlayerRole> playerRoleList;
    private final PlayerEdit prevData;
    private final Consumer<PlayerEdit> newPlayerNameConsumer;

    public EditPlayerDialog(List<PlayerRole> playerRoleList, Consumer<PlayerEdit> newPlayerNameConsumer) {
        this(playerRoleList, null, newPlayerNameConsumer);
    }

    public EditPlayerDialog(List<PlayerRole> playerRoleList, PlayerEdit playerEdit, Consumer<PlayerEdit> newPlayerNameConsumer) {
        this.newPlayerNameConsumer = newPlayerNameConsumer;
        this.playerRoleList = playerRoleList;
        prevData = playerEdit;

        if (prevData != null) {setTitle(TITLEEDIT);}
        else setTitle(TITLEADD);

        for (int i = 0; i < playerRoleList.size(); i++) {
            playerRoleId.addItem(playerRoleList.get(i).getId());
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel firstPanel = new JPanel(new BorderLayout());
        JPanel secondPanel = new JPanel(new BorderLayout());
        JPanel thirdPanel = new JPanel(new BorderLayout());

        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel surnamePanel = new JPanel(new BorderLayout());
        JPanel agePanel = new JPanel(new BorderLayout());
        JPanel rolePanel = new JPanel(new BorderLayout());
        JPanel countGamesPanel = new JPanel(new BorderLayout());
        JPanel countPointsPanel = new JPanel(new BorderLayout());
        JPanel effectivenessPanel = new JPanel(new BorderLayout());
        JPanel playerNumberPanel = new JPanel(new BorderLayout());

        namePanel.add(new JLabel("Имя:"), BorderLayout.WEST);
        surnamePanel.add(new JLabel("Фамилия:"), BorderLayout.WEST);
        agePanel.add(new JLabel("Возраст:"), BorderLayout.WEST);
        rolePanel.add(new JLabel("Амплуа:"), BorderLayout.WEST);
        countGamesPanel.add(new JLabel("Кол-во игр:"), BorderLayout.WEST);
        countPointsPanel.add(new JLabel("Кол-во очков:"), BorderLayout.WEST);
        effectivenessPanel.add(new JLabel("Эффективность:"), BorderLayout.WEST);
        playerNumberPanel.add(new JLabel("Номер игрока:"), BorderLayout.WEST);

        if (prevData != null) {
            nameField.setText(prevData.getName());
            surnameField.setText(prevData.getSurname());
            ageField.setText(CommonUtils.toStringSafe(prevData.getAge()));
            countGamesField.setText(CommonUtils.toStringSafe(prevData.getCountGames()));
            countPointsField.setText(CommonUtils.toStringSafe(prevData.getCountPoints()));
            effectivenessField.setText(CommonUtils.toStringSafe(prevData.getEffectiveness()));
            playerNumberField.setText(CommonUtils.toStringSafe(prevData.getPlayerNumber()));
            playerRoleId.setSelectedItem(prevData.getPlayerRoleId());
        }

        namePanel.add(nameField, BorderLayout.CENTER);
        surnamePanel.add(surnameField, BorderLayout.CENTER);
        agePanel.add(ageField, BorderLayout.CENTER);
        countGamesPanel.add(countGamesField, BorderLayout.CENTER);
        countPointsPanel.add(countPointsField, BorderLayout.CENTER);
        effectivenessPanel.add(effectivenessField, BorderLayout.CENTER);
        playerNumberPanel.add(playerNumberField, BorderLayout.CENTER);
        rolePanel.add(playerRoleId, BorderLayout.CENTER);

        firstPanel.add(namePanel, BorderLayout.NORTH);
        firstPanel.add(surnamePanel, BorderLayout.CENTER);
        firstPanel.add(agePanel, BorderLayout.SOUTH);

        secondPanel.add(rolePanel, BorderLayout.NORTH);
        secondPanel.add(countGamesPanel, BorderLayout.CENTER);
        secondPanel.add(countPointsPanel, BorderLayout.SOUTH);

        thirdPanel.add(effectivenessPanel, BorderLayout.NORTH);
        thirdPanel.add(playerNumberPanel, BorderLayout.CENTER);
        thirdPanel.add(new JButton(new EditPlayerDialog.SaveAction()), BorderLayout.SOUTH);

        mainPanel.add(firstPanel, BorderLayout.NORTH);
        mainPanel.add(secondPanel, BorderLayout.CENTER);
        mainPanel.add(thirdPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(360, 240);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevData != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isBlank(nameField.getText())
                    || isBlank(surnameField.getText())
                    || isBlank(ageField.getText())
                    || isBlank(countGamesField.getText())
                    || isBlank(countPointsField.getText())
                    || isBlank(effectivenessField.getText())
                    || isBlank(playerNumberField.getText())
                    || playerRoleId.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(
                        EditPlayerDialog.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            PlayerEdit playerEdit = new PlayerEdit(
                    nameField.getText(),
                    surnameField.getText(),
                    Integer.parseInt(ageField.getText()),
                    (Integer)(playerRoleId.getSelectedItem()),
                    Integer.parseInt(countGamesField.getText()),
                    Integer.parseInt(countPointsField.getText()),
                    Integer.parseInt(effectivenessField.getText()),
                    Integer.parseInt(playerNumberField.getText())
            );
            newPlayerNameConsumer.accept(playerEdit);
            dispose();
        }
    }
}
