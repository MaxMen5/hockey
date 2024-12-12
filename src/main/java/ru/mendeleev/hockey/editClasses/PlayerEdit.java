package ru.mendeleev.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlayerEdit {
    private String name;
    private String surname;
    private Integer age;
    private String playerRoleId;
    private Integer countGames;
    private Integer countPoints;
    private Integer effectiveness;
    private Integer playerNumber;

    public PlayerEdit(String name, String surname, Integer age, String playerRoleId,
                      Integer countGames, Integer countPoints, Integer effectiveness, Integer playerNumber) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.playerRoleId = playerRoleId;
        this.countGames = countGames;
        this.countPoints = countPoints;
        this.effectiveness = effectiveness;
        this.playerNumber = playerNumber;
    }
}
