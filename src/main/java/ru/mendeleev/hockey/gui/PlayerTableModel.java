package ru.mendeleev.hockey.gui;

import ru.mendeleev.hockey.entity.Player;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class PlayerTableModel extends AbstractTableModel {
    private static final List<String> COLUMNS = Arrays.asList(
            "id", "Имя", "Фамилия", "Амплуа", "Возраст", "Кол-во игр", "Кол-во очков", "Эффективность", "Номер игрока"
    );
    private static final List<Class<?>> TYPES = Arrays.asList(
            Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class
    );

    private List<Player> data;

    public void initWith(List<Player> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMNS.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TYPES.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Player player = data.get(rowIndex);

        switch (columnIndex) {
            case 0: return player.getId();
            case 1: return player.getName();
            case 2: return player.getSurname();
            case 3: return player.getPlayerRoleId();
            case 4: return player.getAge();
            case 5: return player.getCountGames();
            case 6: return player.getCountPoints();
            case 7: return player.getEffectiveness();
            case 8: return player.getPlayerNumber();
            default: return null;
        }
    }
}