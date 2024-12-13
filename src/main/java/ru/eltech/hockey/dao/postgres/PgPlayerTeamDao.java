package ru.eltech.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.eltech.hockey.dao.interfaces.AbstractDao;
import ru.eltech.hockey.dao.interfaces.IPlayerTeamDao;
import ru.eltech.hockey.entity.PlayerTeam;

@Component
@Lazy
public class PgPlayerTeamDao extends AbstractDao<PlayerTeam> implements IPlayerTeamDao {

}
