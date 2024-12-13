package ru.eltech.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.eltech.hockey.entity.City;
import ru.eltech.hockey.entity.League;
import ru.eltech.hockey.entity.Player;

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
