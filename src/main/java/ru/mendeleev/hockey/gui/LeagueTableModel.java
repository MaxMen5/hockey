package ru.mendeleev.hockey.gui;

import ru.mendeleev.hockey.entity.League;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class LeagueTableModel extends AbstractTableModel {

    private static final List<String> COLUMNS = Arrays.asList(
            "id", "Название"
    );
    private static final List<Class<?>> TYPES = Arrays.asList(
            Integer.class, String.class
    );

    private List<League> data;

    public void initWith(List<League> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
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
        League league = data.get(rowIndex);

        switch (columnIndex) {
            case 0: return league.getId();
            case 1: return league.getName();
            default: return null;
        }
    }

}
