package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.IPlayerDao;
import ru.mendeleev.hockey.entity.Player;

@Component
@Lazy
public class PgPlayerDao extends AbstractDao<Player> implements IPlayerDao {

}
