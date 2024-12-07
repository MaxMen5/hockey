package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.ITeamDao;
import ru.mendeleev.hockey.entity.Team;

@Component
@Lazy
public class PgTeamDao extends AbstractDao<Team> implements ITeamDao {

}
