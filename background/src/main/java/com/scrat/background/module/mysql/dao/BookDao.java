package com.scrat.background.module.mysql.dao;

import com.scrat.background.module.mysql.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDao {
    private static final Logger log = LoggerFactory.getLogger(BookDao.class.getName());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private RowMapper<Book> getBookMapper() {
        return (rs, rowNum) -> new Book()
                .setBookId(rs.getLong("book_id"))
                .setBookName(rs.getString("book_name"))
                .setPrice(rs.getInt("price"));
    }

    public Long getTotalCnt() {
        String sql = "select count(1) from book";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public boolean addBook(Book book) {
        String sql = "insert ignore into book set book_name=?,price=?";
        int affectRow = jdbcTemplate.update(sql, book.getBookName(), book.getPrice());
        return affectRow > 0;
    }

    public Long addBookGetId(Book book) {
        String sql = "insert ignore into book set book_name=?,price=?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"book_id"});
            ps.setString(1, book.getBookName());
            ps.setInt(2, book.getPrice());
            return ps;
        }, keyHolder);
        Number num = keyHolder.getKey();
        if (num == null) {
            return -1L;
        }
        return keyHolder.getKey().longValue();
    }

    @Transactional
    public List<Integer> addBookList(List<Book> bookList) {
        String sql = "insert ignore into book set book_name=?,price=?";
        ParameterizedPreparedStatementSetter<Book> setter = (ps, book) -> {
            ps.setString(1, book.getBookName());
            ps.setInt(2, book.getPrice());
        };
        int[][] affectCntArr = jdbcTemplate.batchUpdate(sql, bookList, 5, setter);
        List<Integer> failIndex = new ArrayList<>();
        int index = 0;
        for (int[] arr : affectCntArr) {
            for (int affectCnt : arr) {
                if (affectCnt <= 0) {
                    failIndex.add(index);
                }
                index++;
            }
        }
        return failIndex;
    }

    @Nullable
    public Book getBook(long bookId) {
        String sql = "select * from book where book_id=? limit 1";
        try {
            return jdbcTemplate.queryForObject(sql, getBookMapper(), bookId);
        } catch (EmptyResultDataAccessException ignore) {
            log.error("Book not found. bookId={}", bookId);
            return null;
        }
    }

    public List<Book> getBookList(String bookName) {
        String sql = "select * from book where book_name like ?";
        return jdbcTemplate.query(sql, getBookMapper(), "%" + bookName + "%");
    }

    public List<Book> getBookList(int offset, int size) {
        String sql = "select * from book order by book_id asc limit ?,?";
        return jdbcTemplate.query(sql, getBookMapper(), offset, size);
    }

    public List<Book> getBookList(List<Long> bookIds) {
        String sql = "select * from book where book_id in (:bookIds)";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bookIds", bookIds);
        return namedParameterJdbcTemplate.query(sql, parameters, getBookMapper());
    }

    public boolean updateBook(Book book) {
        String sql = "update book set book_name=?,price=? where book_id=? limit 1";
        int updateCnt = jdbcTemplate.update(sql, book.getBookName(), book.getPrice(), book.getBookId());
        return updateCnt > 0;
    }

    /**
     * Delete book by book_id
     * @param bookId the id of book
     * @return Warning: If the book is not in the table, it will return false
     */
    public boolean deleteBook(long bookId) {
        String sql = "delete from book where book_id=? limit 1";
        int updateCnt = jdbcTemplate.update(sql, bookId);
        return updateCnt > 0;
    }
}
