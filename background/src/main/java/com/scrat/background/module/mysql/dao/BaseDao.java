package com.scrat.background.module.mysql.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BaseDao {
    protected final NamedParameterJdbcTemplate template;

    public BaseDao(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public long insertAndGetId(String sql, Object val) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(val);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, sqlParameterSource, keyHolder);
        Number num = keyHolder.getKey();
        if (num == null) {
            return -1L;
        }
        return num.longValue();
    }

    public <T> T getObject(String sql, MapSqlParameterSource parameters, Class<T> mappedClass, T defaultValue) {
        try {
            return template.queryForObject(sql, parameters, new BeanPropertyRowMapper<>(mappedClass));
        } catch (EmptyResultDataAccessException e) {
            return defaultValue;
        }
    }

    public long getLong(String sql, MapSqlParameterSource parameters, long defaultValue) {
        try {
            Long res = template.queryForObject(sql, parameters, Long.class);
            if (res == null) {
                return defaultValue;
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return defaultValue;
        }
    }

    public <T> List<T> getList(String sql, MapSqlParameterSource parameters, Class<T> mappedClass) {
        return template.query(sql, parameters, new BeanPropertyRowMapper<>(mappedClass));
    }

    public boolean insert(String sql, Object object) {
        return update(sql, object);
    }

    public boolean update(String sql, Object object) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(object);
        int affectRow = template.update(sql, sqlParameterSource);
        return affectRow > 0;
    }
}
