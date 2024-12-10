package ru.mendeleev.hockey.dao.interfaces;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.mendeleev.hockey.entity.IEntity;

import java.util.List;
import java.util.Map;

@Log4j
public abstract class AbstractDao<E extends IEntity> implements IDao<E> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected List<E> query(String sql) {
        log.info(sql);
        return jdbcTemplate.query(sql, rowMapper());
    }

    protected <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        log.info(sql);
        return jdbcTemplate.query(sql, rowMapper);
    }

    protected E queryForObject(String sql) {
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, rowMapper());
    }

    protected <T> T queryForObject(String sql, Class<T> requiredType) {
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, requiredType);
    }

    protected List<Map<String, Object>> queryForList(String sql) {
        log.info(sql);
        return jdbcTemplate.queryForList(sql);
    }

    protected <T> List<T> queryForList(String sql, Class<T> elementType) {
        log.info(sql);
        return jdbcTemplate.queryForList(sql, elementType);
    }

    protected void update(String sql) {
        log.info(sql);
        jdbcTemplate.update(sql);
    }

}
