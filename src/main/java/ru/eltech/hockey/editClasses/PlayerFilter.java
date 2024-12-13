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
public class PlayerFilter {
    private String name;
    private String surname;
    private String age;
    private String playerRole;
    private String countGames;
    private String countPoints;
    private String effectiveness;
    private String playerNumber;

    public PlayerFilter(String name, String surname, String age, String playerRole, String countGames, String countPoints, String effectiveness, String playerNumber ) {
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
