package com.scrat.background;

import com.scrat.background.module.mysql.Book;
import com.scrat.background.module.mysql.jdbc.BookDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class MySQLTests {

    private static final Logger log = LoggerFactory.getLogger(MySQLTests.class.getName());

    @Autowired
    private BookDao bookDao;

    @Test
    void testGetCnt() {
        Long cnt = bookDao.getTotalCnt();
        log.info("cnt={}", cnt);
    }

    @Test
    void testAddBook() {
        Book book = new Book()
                .setBookName("Unit Test")
                .setPrice(123);
        boolean addSuccess = bookDao.addBook(book);
        Assert.isTrue(addSuccess, "Add book fail");
    }

    @Test
    void testAddBookReturnId() {
        Book book = new Book()
                .setBookName("Unit Test")
                .setPrice(123);
        Long id = bookDao.addBookAndReturnGeneratedKey(book);
        Assert.isTrue(id > 0L, "Add book fail");
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getBook(1L);
        log.info("{}", book);

        Book bookNotFound = bookDao.getBook(1000L);
        log.info("{}", bookNotFound);
    }

    @Test
    void testGetBookList() {
        List<Book> bookList = bookDao.getBookLIst(0, 5);
        log.info("{}", bookList);

        List<Book> bookListEmpty = bookDao.getBookLIst(1000, 5);
        log.info("{}", bookListEmpty);
    }

    @Test
    void testUpdateBook() {
        Book book = bookDao.getBook(1L);
        Assert.notNull(book, "Book is null");
        book.setPrice(12345);
        boolean updateSuccess = bookDao.updateBook(book);
        Assert.isTrue(updateSuccess, "Update Book Fail");
    }

    @Test
    void testDeleteBook() {
        Book book = new Book()
                .setBookName("Unit Test")
                .setPrice(123);
        Long bookId = bookDao.addBookAndReturnGeneratedKey(book);
        Assert.isTrue(bookId > 0L, "Add book fail");

        boolean deleteSuccess = bookDao.deleteBook(bookId);
        Assert.isTrue(deleteSuccess, "Delete Book Fail");
    }
}
