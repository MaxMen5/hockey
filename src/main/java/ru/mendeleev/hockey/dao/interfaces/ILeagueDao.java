package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.hockey.entity.League;

import java.util.List;

/**
 * Интерфейс DAO-класса, работающего с таблицей {@link League}.
 */
public interface ILeagueDao extends IDao<League> {

    @Override
    default RowMapper<League> rowMapper() {
        return (resultSet, i) -> {
            League league = new League();
            league.setId(resultSet.getInt("id"));
            league.setName(resultSet.getString("name"));
            return league;
        };
    }

    //================================================================================================================//

    @Transactional(readOnly = true)
    List<League> findAll();

    @Transactional(readOnly = true)
    boolean isContainAnyTeam(Integer selectedLeagueId);

    @Transactional
    void deleteLeagueById(Integer selectedLeagueId);

    @Transactional
    void save(String newLeagueName);
}
