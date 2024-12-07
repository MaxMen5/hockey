package ru.mendeleev.hockey.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlayerTeam implements IEntity {
    private Integer playerId;
    private Integer teamId;
}
