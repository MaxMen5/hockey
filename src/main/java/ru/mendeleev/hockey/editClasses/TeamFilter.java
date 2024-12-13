package ru.mendeleev.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.hockey.entity.City;
import ru.mendeleev.hockey.entity.League;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TeamFilter {

    private String name;
    private String league;
    private String city;
    public TeamFilter(String name, String league, String city) {
        this.name = name;
        this.league = league;
        this.city = city;
    }

}
