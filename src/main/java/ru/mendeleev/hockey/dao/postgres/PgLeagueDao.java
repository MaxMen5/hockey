package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.ILeagueDao;
import ru.mendeleev.hockey.entity.League;

@Component
@Lazy
public class PgLeagueDao extends AbstractDao<League> implements ILeagueDao {

}
