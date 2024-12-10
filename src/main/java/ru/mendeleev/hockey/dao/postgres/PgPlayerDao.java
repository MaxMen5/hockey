package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.IPlayerDao;
import ru.mendeleev.hockey.editClasses.PlayerEdit;
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
    public void save(PlayerEdit newPlayer) {
        update("insert into player(name, surname, age, player_role_id, count_games, count_points," +
                " effectiveness, player_number) values ('" + newPlayer.getName() + "', '" + newPlayer.getSurname() +
                "', " + newPlayer.getAge() + ", " + newPlayer.getPlayerRoleId() + ", " + newPlayer.getCountGames() + ", "
                + newPlayer.getCountPoints() + ", " + newPlayer.getEffectiveness() + ", " + newPlayer.getPlayerNumber() + ");");
    }

    @Override
    public void update(Integer selectedPlayerId, PlayerEdit changedPlayer) {
        update("update player set name = '" + changedPlayer.getName() + "', " +
                "surname = '" + changedPlayer.getSurname() + "', age = " + changedPlayer.getAge() +
                ", player_role_id = " + changedPlayer.getPlayerRoleId() + ", count_games = " + changedPlayer.getCountGames() +
                ", count_points = " + changedPlayer.getCountPoints() + ", effectiveness = " + changedPlayer.getEffectiveness() +
                ", player_number = " + changedPlayer.getPlayerNumber() + " where id = " + selectedPlayerId);
    }
}
