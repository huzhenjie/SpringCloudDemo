package com.scrat.background.module.mysql.jdbc;

import com.scrat.background.module.mysql.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public Long addBookAndReturnGeneratedKey(Book book) {
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

    @Nullable
    public Book getBook(long bookId) {
        String sql = "select * from book where book_id=? limit 1";
        try {
            return jdbcTemplate.queryForObject(sql, getBookMapper(), bookId);
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public List<Book> getBookLIst(int offset, int size) {
        String sql = "select * from book order by book_id asc limit ?,?";
        return jdbcTemplate.query(sql, getBookMapper(), offset, size);
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
