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
        return query("select * from team order by id");
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
}
