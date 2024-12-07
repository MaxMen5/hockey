package ru.mendeleev.hockey.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Player implements IEntity {
    private Integer id;
    private String fio;
    private Integer age;
    private Integer playerRoleId;
    private Integer winCount;
    private Integer lossCount;
}
