package ru.mendeleev.hockey.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class League implements IEntity {
    private Integer id;
    private String name;
}
