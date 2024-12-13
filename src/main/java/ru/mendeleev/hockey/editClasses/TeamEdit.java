package ru.mendeleev.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.hockey.entity.League;
import ru.mendeleev.hockey.entity.City;
import ru.mendeleev.hockey.entity.Player;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TeamEdit {

    private String name;
    private League leagueName;
    private City city;
    private List<Player> listPlayer;

    public TeamEdit(String name, League leagueName, City city, List<Player> listPlayer) {
        this.name = name;
        this.leagueName = leagueName;
        this.city = city;
        this.listPlayer = listPlayer;
    }
}
