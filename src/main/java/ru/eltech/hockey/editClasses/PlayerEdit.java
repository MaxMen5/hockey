package ru.eltech.hockey.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.eltech.hockey.entity.PlayerRole;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlayerEdit {
    private String name;
    private String surname;
    private Integer age;
    private PlayerRole playerRole;
    private Integer countGames;
    private Integer countPoints;
    private Integer effectiveness;
    private Integer playerNumber;

    public PlayerEdit(String name, String surname, Integer age, PlayerRole playerRole,
                      Integer countGames, Integer countPoints, Integer effectiveness, Integer playerNumber) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.playerRole = playerRole;
        this.countGames = countGames;
        this.countPoints = countPoints;
        this.effectiveness = effectiveness;
        this.playerNumber = playerNumber;
    }
}