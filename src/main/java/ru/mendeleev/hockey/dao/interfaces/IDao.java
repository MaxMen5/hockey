package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import ru.mendeleev.hockey.entity.IEntity;

/**
 * Интерфейс всех DAO-классов.
 *
 * @param <E> тип entity конкретного dao-класса
 */
public interface IDao<E extends IEntity> {
    RowMapper<E> rowMapper();
}
