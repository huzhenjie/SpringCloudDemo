package com.scrat.background.module.mysql.dao;

import com.scrat.background.module.mysql.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookModelDao {
    private static final Logger log = LoggerFactory.getLogger(BookDao.class.getName());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookModelDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean addBook(Book book) {
        String sql = "insert ignore into book set book_name=:bookName,price=:price";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(book);
        int affectRow = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        return affectRow > 0;
    }

    public long addBookGetId(Book book) {
        String sql = "insert ignore into book set book_name=:bookName,price=:price";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(book);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        Number num = keyHolder.getKey();
        if (num == null) {
            return -1L;
        }
        return num.longValue();
    }

    public Long getTotalBook() {
        String sql = "select count(1) from book";
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
    }

    @Transactional
    public List<Integer> addBookList(List<Book> bookList) {
        String sql = "insert ignore into book set book_name=:bookName,price=:price";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(bookList.toArray());
        int[] affectCntArr = namedParameterJdbcTemplate.batchUpdate(sql, batch);
        List<Integer> failIndex = new ArrayList<>();
        for (int i = 0; i < affectCntArr.length; i++) {
            if (affectCntArr[i] <= 0) {
                failIndex.add(i);
            }
        }
        return failIndex;
    }

    public Book getBook(long bookId) {
        String sql = "select * from book where book_id=:bookId limit 1";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bookId", bookId);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper<>(Book.class));
        } catch (EmptyResultDataAccessException ignore) {
            log.error("Book not found. bookId={}", bookId);
            return null;
        }
    }

    public List<Book> getBookList(String bookName) {
        String sql = "select * from book where book_name like :bookName";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bookName", "%" + bookName + "%");
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(Book.class));
    }

    public boolean updateBook(Book book) {
        String sql = "update book set book_name=:bookName,price=:price where book_id=:bookId limit 1";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(book);
        int updateCnt = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        return updateCnt > 0;
    }
}
