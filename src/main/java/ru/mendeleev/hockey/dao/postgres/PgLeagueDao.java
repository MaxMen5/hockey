package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.ILeagueDao;
import ru.mendeleev.hockey.entity.League;

import java.util.List;

@Component
@Lazy
public class PgLeagueDao extends AbstractDao<League> implements ILeagueDao {

    @Override
    public List<League> findAll() {
        return query("select * from league");
    }

    @Override
    public boolean isContainAnyTeam(Integer selectedLeagueId) {
        Integer count = queryForObject("select count(*) from team where league_id = " + selectedLeagueId, Integer.class);
        return count != 0;
    }

    @Override
    public void deleteLeagueById(Integer selectedLeagueId) {
        update("delete from league where id = " + selectedLeagueId);
    }
}
