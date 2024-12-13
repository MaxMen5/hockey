package ru.eltech.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.eltech.hockey.dao.interfaces.AbstractDao;
import ru.eltech.hockey.dao.interfaces.IPlayerRoleDao;
import ru.eltech.hockey.entity.PlayerRole;

import java.util.List;

@Component
@Lazy
public class PgPlayerRoleDao extends AbstractDao<PlayerRole> implements IPlayerRoleDao {

    @Override
    public List<PlayerRole> findAll()  {
        return query("select * from player_role order by id");
    }
}
