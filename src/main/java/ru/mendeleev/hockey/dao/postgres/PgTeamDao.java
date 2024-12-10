package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.ITeamDao;
import ru.mendeleev.hockey.editClasses.FullTeam;
import ru.mendeleev.hockey.entity.Team;
import ru.mendeleev.hockey.editClasses.TeamEdit;

import java.util.List;

@Component
@Lazy
public class PgTeamDao extends AbstractDao<Team> implements ITeamDao {

    @Override
    public List<FullTeam> findFullAll() {
        return query("select " +
                "t.id as team_id, " +
                "t.name as team_name, " +
                "l.id as league_id, " +
                "l.name as league_name, " +
                "c.id as city_id, " +
                "c.name as city_name " +
                "from " +
                "team t " +
                "inner join league l on t.league_id = l.id " +
                "inner join city c on t.city_id = c.id " +
                "order by " +
                "t.id", fullTeamRowMapper());
    }

    @Override
    public void deleteTeamById(Integer selectedTeamId) {
        update("delete from team where id = " + selectedTeamId);
    }

    @Override
    public void save(TeamEdit newTeam) {
        update("insert into team(name, league_id, city_id) values " +
                "('" + newTeam.getName() + "', " + newTeam.getLeagueName() + ", " + newTeam.getCity() + ")");
    }

    @Override
    public void update(Integer selectedTeamId, TeamEdit changedTeam) {
        update("update team set name = '" + changedTeam.getName() + "', " +
                "league_id = " + changedTeam.getLeagueName() + ", city_id = " + changedTeam.getCity() +
                " where id = " + selectedTeamId);
    }

    private static RowMapper<FullTeam> fullTeamRowMapper() {
        return (resultSet, i) -> {
            FullTeam fullTeam = new FullTeam();
            fullTeam.setId(resultSet.getInt("team_id"));
            fullTeam.setName(resultSet.getString("team_name"));
            fullTeam.setLeagueId(resultSet.getInt("league_id"));
            fullTeam.setLeagueName(resultSet.getString("league_name"));
            fullTeam.setCityId(resultSet.getInt("city_id"));
            fullTeam.setCityName(resultSet.getString("city_name"));
            return fullTeam;
        };
    }
}
