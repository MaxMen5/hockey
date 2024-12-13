
package ru.eltech.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.eltech.hockey.entity.PlayerRole;

import java.util.List;

/**
 * Интерфейс DAO-класса, работающего с таблицей {@link PlayerRole}.
 */
public interface IPlayerRoleDao extends IDao<PlayerRole> {

    @Override
    default RowMapper<PlayerRole> rowMapper() {
        return (resultSet, i) -> {
            PlayerRole playerRole = new PlayerRole();
            playerRole.setId(resultSet.getInt("id"));
            playerRole.setName(resultSet.getString("name"));
            return playerRole;
        };
    }

    //================================================================================================================//

    @Transactional
    public List<PlayerRole> findAll();
}
