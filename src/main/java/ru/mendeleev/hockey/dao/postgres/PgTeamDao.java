package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.ITeamDao;
import ru.mendeleev.hockey.entity.Team;
import ru.mendeleev.hockey.editClasses.TeamEdit;

import java.util.List;

@Component
@Lazy
public class PgTeamDao extends AbstractDao<Team> implements ITeamDao {

    @Override
    public List<Team> findAll() {
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
                "t.id");
    }

    @Override
    public void deleteTeamById(Integer selectedTeamId) {
        update("delete from team where id = " + selectedTeamId);
    }

    @Override
    public void save(TeamEdit newTeam) {
        update("insert into team(name, league_id, city_id) values " +
                "('" + newTeam.getName() + "', " + newTeam.getLeagueName().getId() + ", " + newTeam.getCity().getId() + ")");
    }

    @Override
    public void update(Integer selectedTeamId, TeamEdit changedTeam) {
        update("update team set name = '" + changedTeam.getName() + "', " +
                "league_id = " + changedTeam.getLeagueName().getId() +
                ", city_id = " + changedTeam.getCity().getId() +
                " where id = " + selectedTeamId);
    }

}
