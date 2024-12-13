package ru.eltech.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.eltech.hockey.dao.interfaces.AbstractDao;
import ru.eltech.hockey.dao.interfaces.IPlayerDao;
import ru.eltech.hockey.editClasses.PlayerEdit;
import ru.eltech.hockey.entity.Player;

import java.util.List;

@Component
@Lazy
public class PgPlayerDao extends AbstractDao<Player> implements IPlayerDao {

    @Override
    public List<Player> findAll() {
        return query("select " +
                "p.id as id, " +
                "p.name as name, " +
                "p.surname as surname, " +
                "p.age as age, " +
                "pr.id as player_role_id, " +
                "pr.name as player_role_name, " +
                "p.count_games as count_games, " +
                "p.count_points as count_points, " +
                "p.effectiveness as effectiveness, " +
                "p.player_number as player_number, " +
                "array_to_string(array(select t.name from team t inner join player_team pt on t.id = pt.team_id where pt.player_id = p.id order by t.name), ', ')  as player_teams " +
                "from " +
                "player p " +
                "inner join player_role pr on p.player_role_id = pr.id " +
                "order by " +
                "p.id");
    }

    @Override
    public List<Player> findAllNotInTeam(Integer teamId) {
        return query("select " +
                "p.id as id, " +
                "p.name as name, " +
                "p.surname as surname, " +
                "p.age as age, " +
                "p.player_role_id as player_role_id, " +
                "null as player_role_name, " +
                "p.count_games as count_games, " +
                "p.count_points as count_points, " +
                "p.effectiveness as effectiveness, " +
                "p.player_number as player_number, " +
                "null as player_teams " +
                "from player p " +
                "where id not in(select player_id from player_team where team_id = " + teamId + ")");
    }

    @Override
    public void deletePlayerById(Integer selectedPlayerId) {
        update("delete from player where id = " + selectedPlayerId);
    }

    @Override
    public void save(PlayerEdit newPlayer) {
        update("insert into player(name, surname, age, player_role_id, count_games, count_points," +
                " effectiveness, player_number) values ('" +
                newPlayer.getName() + "', '"
                + newPlayer.getSurname() + "', "
                + newPlayer.getAge() + ", "
                + newPlayer.getPlayerRole().getId() + ", "
                + newPlayer.getCountGames() + ", "
                + newPlayer.getCountPoints() + ", "
                + newPlayer.getEffectiveness() + ", "
                + newPlayer.getPlayerNumber() + ");");
    }

    @Override
    public void update(Integer selectedPlayerId, PlayerEdit changedPlayer) {
        update("update player set name = '" + changedPlayer.getName() + "', " +
                "surname = '" + changedPlayer.getSurname() +
                "', age = " + changedPlayer.getAge() +
                ", player_role_id = " + changedPlayer.getPlayerRole().getId() +
                ", count_games = " + changedPlayer.getCountGames() +
                ", count_points = " + changedPlayer.getCountPoints() +
                ", effectiveness = " + changedPlayer.getEffectiveness() +
                ", player_number = " + changedPlayer.getPlayerNumber() +
                " where id = " + selectedPlayerId);
    }

    @Override
    public List<Player> findTeamPlayers(Integer teamId) {
        return query("select " +
                "p.id as id, " +
                "p.name as name, " +
                "p.surname as surname, " +
                "p.age as age, " +
                "p.player_role_id as player_role_id, " +
                "null as player_role_name, " +
                "p.count_games as count_games, " +
                "p.count_points as count_points, " +
                "p.effectiveness as effectiveness, " +
                "p.player_number as player_number, " +
                "null as player_teams " +
                "from player p " +
                "where id in(select player_id from player_team where team_id = " + teamId + ")");
    }
}
