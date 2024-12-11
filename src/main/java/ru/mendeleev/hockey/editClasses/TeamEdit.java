package ru.mendeleev.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TeamEdit {

    private String name;
    private String leagueName;
    private String city;

    public TeamEdit(String name, String leagueName, String city) {
        this.name = name;
        this.leagueName = leagueName;
        this.city = city;
    }
}
