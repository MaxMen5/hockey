package ru.mendeleev.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.hockey.entity.League;
import ru.mendeleev.hockey.entity.City;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TeamEdit {

    private String name;
    private League leagueName;
    private City city;

    public TeamEdit(String name, League leagueName, City city) {
        this.name = name;
        this.leagueName = leagueName;
        this.city = city;
    }
}
