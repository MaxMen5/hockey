package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.IPlayerRoleDao;
import ru.mendeleev.hockey.entity.PlayerRole;

@Component
@Lazy
public class PgPlayerRoleDao extends AbstractDao<PlayerRole> implements IPlayerRoleDao {

}
