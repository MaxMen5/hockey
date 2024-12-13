package ru.mendeleev.hockey.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Team implements IEntity {
    private Integer id;
    private String name;
    private Integer leagueId;
    private String leagueName;
    private Integer cityId;
    private String cityName;
    private String listPlayers;
}
