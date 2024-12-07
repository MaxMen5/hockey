package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.IPlayerTeamDao;
import ru.mendeleev.hockey.entity.PlayerTeam;

@Component
@Lazy
public class PgPlayerTeamDao extends AbstractDao<PlayerTeam> implements IPlayerTeamDao {

}
