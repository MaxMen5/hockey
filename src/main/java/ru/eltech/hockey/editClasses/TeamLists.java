package ru.eltech.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.eltech.hockey.entity.League;
import ru.eltech.hockey.entity.City;
import ru.eltech.hockey.entity.Player;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TeamLists {
    private List<League> leagueList;
    private List<City> cityList;
    private List<Player> playerList;
}
