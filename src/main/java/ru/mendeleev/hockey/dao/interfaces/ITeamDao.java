
package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import ru.mendeleev.hockey.entity.Team;

/**
 * Интерфейс DAO-класса, работающего с таблицей {@link Team}.
 */
public interface ITeamDao extends IDao<Team> {

    @Override
    default RowMapper<Team> rowMapper() {
        return (resultSet, i) -> {
            Team team = new Team();
            team.setId(resultSet.getInt("id"));
            team.setName(resultSet.getString("name"));
            team.setLeagueId(resultSet.getInt("league_id"));
            team.setCityId(resultSet.getInt("city_id"));
            return team;
        };
    }

    //================================================================================================================//

}
