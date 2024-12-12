
package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.hockey.editClasses.TeamEdit;
import ru.mendeleev.hockey.entity.Team;

import java.util.List;

/**
 * Интерфейс DAO-класса, работающего с таблицей {@link Team}.
 */
public interface ITeamDao extends IDao<Team> {

    @Override
    default RowMapper<Team> rowMapper() {
        return (resultSet, i) -> {
            Team team = new Team();
            team.setId(resultSet.getInt("team_id"));
            team.setName(resultSet.getString("team_name"));
            team.setLeagueId(resultSet.getInt("league_id"));
            team.setLeagueName(resultSet.getString("league_name"));
            team.setCityId(resultSet.getInt("city_id"));
            team.setCityName(resultSet.getString("city_name"));
            return team;
        };
    }

    //================================================================================================================//

    @Transactional(readOnly = true)
    List<Team> findAll();

    @Transactional
    void deleteTeamById(Integer selectedTeamId);

    @Transactional
    void save(TeamEdit newTeamName);

    @Transactional
    void update(Integer selectedTeamId, TeamEdit changedTeam);
}
