package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import ru.mendeleev.hockey.entity.League;

/**
 * Интерфейс DAO-класса, работающего с таблицей {@link League}.
 */
public interface ILeagueDao extends IDao<League> {

    @Override
    default RowMapper<League> rowMapper() {
        return (resultSet, i) -> {
            League league = new League();
            league.setId(resultSet.getInt("id"));
            league.setName(resultSet.getString("name"));
            return league;
        };
    }

    //================================================================================================================//

}
