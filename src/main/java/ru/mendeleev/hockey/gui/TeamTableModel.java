package ru.mendeleev.hockey.gui;

import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.entity.Team;
import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;
import ru.mendeleev.hockey.editClasses.FullTeam;

public class TeamTableModel extends AbstractTableModel {

    private static final List<String> COLUMNS = Arrays.asList(
            "id", "Название", "id Лиги", "Лига", "id Города", "Город"
    );
    private static final List<Class<?>> TYPES = Arrays.asList(
            Integer.class, String.class, Integer.class, String.class, Integer.class, String.class
    );

    private List<FullTeam> data;

    public void initWith(List<FullTeam> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
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
        FullTeam fullTeam = data.get(rowIndex);

        switch (columnIndex) {
            case 0: return fullTeam.getId();
            case 1: return fullTeam.getName();
            case 2: return fullTeam.getLeagueId();
            case 3: return fullTeam.getLeagueName();
            case 4: return fullTeam.getCityId();
            case 5: return fullTeam.getCityName();
            default: return null;
        }
    }
}
