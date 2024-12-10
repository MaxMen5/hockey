package ru.mendeleev.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.hockey.entity.League;
import ru.mendeleev.hockey.entity.City;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TeamLists {
    private List<League> leagueList;
    private List<City> cityList;
}
