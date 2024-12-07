package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import ru.mendeleev.hockey.entity.Player;

/**
 * Интерфейс DAO-класса, работающего с таблицей {@link Player}.
 */
public interface IPlayerDao extends IDao<Player> {

    @Override
    default RowMapper<Player> rowMapper() {
        return (resultSet, i) -> {
            Player player = new Player();
            player.setId(resultSet.getInt("id"));
            player.setFio(resultSet.getString("fio"));
            player.setAge(resultSet.getInt("age"));
            player.setPlayerRoleId(resultSet.getInt("player_role_id"));
            player.setWinCount(resultSet.getInt("win_count"));
            player.setLossCount(resultSet.getInt("loss_count"));
            return player;
        };
    }

    //================================================================================================================//

}
