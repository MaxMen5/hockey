package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.IPlayerDao;
import ru.mendeleev.hockey.entity.Player;

import java.util.List;

@Component
@Lazy
public class PgPlayerDao extends AbstractDao<Player> implements IPlayerDao {

    @Override
    public List<Player> findAll() {
        return query("select * from player order by id");
    }

    @Override
    public void deletePlayerById(Integer selectedPlayerId) {
        update("delete from player where id = " + selectedPlayerId);
    }

    @Override
    public void save(String newPlayerName) {
        update("insert into player(name) values ('" + newPlayerName + "')");
    }

    @Override
    public void update(Integer selectedPlayerId, String changedPlayerName) {
        update("update player set name = '" + changedPlayerName + "' where id = " + selectedPlayerId);
    }
}
