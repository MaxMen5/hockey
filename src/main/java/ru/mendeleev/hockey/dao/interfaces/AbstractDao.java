package ru.mendeleev.hockey.dao.interfaces;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.mendeleev.hockey.entity.IEntity;

import java.util.List;
import java.util.Map;

@Log4j
public abstract class AbstractDao<E extends IEntity> implements IDao<E> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("#{ environment['jdbc.catalog'] }")
    protected String catalog;

    @Value("#{ environment['jdbc.schema'] }")
    protected String schema;

    protected List<E> query(String sql) {
        log.info(sql);
        return jdbcTemplate.query(sql, rowMapper());
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