package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.hockey.editClasses.PlayerEdit;
import ru.mendeleev.hockey.entity.League;
import ru.mendeleev.hockey.entity.Player;

import java.util.List;

public interface IPlayerDao extends IDao<Player> {

    @Override
    default RowMapper<Player> rowMapper() {
        return (resultSet, i) -> {
            Player player = new Player();
            player.setId(resultSet.getInt("id"));
            player.setName(resultSet.getString("name"));
            player.setSurname(resultSet.getString("surname"));
            player.setAge(resultSet.getInt("age"));
            player.setPlayerRoleId(resultSet.getInt("player_role_id"));
            player.setPlayerRoleName(resultSet.getString("player_role_name"));
            player.setCountGames(resultSet.getInt("count_games"));
            player.setCountPoints(resultSet.getInt("count_points"));
            player.setEffectiveness(resultSet.getInt("effectiveness"));
            player.setPlayerNumber(resultSet.getInt("player_number"));
            return player;
        };
    }

    //================================================================================================================//

    @Transactional(readOnly = true)
    List<Player> findAll();

    @Transactional
    void deletePlayerById(Integer selectedPlayerId);

    @Transactional
    void save(PlayerEdit newPlayer);

    @Transactional
    void update(Integer selectedPlayerId, PlayerEdit changedPlayer);
}
