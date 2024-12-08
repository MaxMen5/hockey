package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.ITeamDao;
import ru.mendeleev.hockey.entity.Team;

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
    public void save(String newTeamName) {
        update("insert into team(name) values ('" + newTeamName + "')");
    }

    @Override
    public void update(Integer selectedTeamId, String changedTeamName) {
        update("update team set name = '" + changedTeamName + "' where id = " + selectedTeamId);
    }
}
