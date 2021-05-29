package com.scrat.background;

import com.scrat.background.module.mysql.Book;
import com.scrat.background.module.mysql.dao.BookDao;
import com.scrat.background.module.mysql.dao.BookModelDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MySQLTests {

    private static final Logger log = LoggerFactory.getLogger(MySQLTests.class.getName());

    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookModelDao bookModelDao;

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

        boolean useJdbcTemplate = bookDao.addBook(book);
        Assert.isTrue(useJdbcTemplate, "Add book fail");

        boolean useNamedParameterJdbcTemplate = bookModelDao.addBook(book);
        Assert.isTrue(useNamedParameterJdbcTemplate, "Add book fail");
    }

    @Test
    void testAddBookList() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book().setPrice(222).setBookName("book_list"));
        bookList.add(new Book().setPrice(222).setBookName("book_list"));
        bookList.add(new Book().setPrice(222).setBookName("book_list"));
        bookList.add(new Book().setPrice(222).setBookName("book_list"));
        bookList.add(new Book().setPrice(222).setBookName("book_list"));
        bookList.add(new Book().setPrice(222).setBookName("book_list"));
        bookList.add(new Book().setPrice(222).setBookName("book_list"));
        List<Integer> failList = bookDao.addBookList(bookList);
        Assert.isTrue(failList.isEmpty(), "Something wrong");
        failList = bookModelDao.addBookList(bookList);
        Assert.isTrue(failList.isEmpty(), "Something wrong");
    }

    @Test
    void testAddBookReturnId() {
        Book book = new Book()
                .setBookName("Unit Test")
                .setPrice(123);
        Long id = bookDao.addBookGetId(book);
        Assert.isTrue(id > 0L, "Add book fail");
        id = bookModelDao.addBookGetId(book);
        Assert.isTrue(id > 0L, "Add book fail");
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getBook(1L);
        Assert.notNull(book, "Book not found");
        log.info("{}", book);

        Book notFoundBook = bookDao.getBook(1000L);
        Assert.isNull(notFoundBook, "Book founded ???");

        Book bookByNamedParameter = bookModelDao.getBook(1L);
        Assert.notNull(bookByNamedParameter, "Book not found");
        log.info("{}", bookByNamedParameter);

        Book notFoundBookByNamedParameter = bookModelDao.getBook(1000L);
        Assert.isNull(notFoundBookByNamedParameter, "Book founded ???");

        List<Book> bookList = bookDao.getBookList("Test");
        Assert.notEmpty(bookList, "Get book list fail");
        log.info("{}", bookList);
        bookList = bookModelDao.getBookList("Test");
        Assert.notEmpty(bookList, "Get book list fail");
        log.info("{}", bookList);
    }

    @Test
    void testGetBookList() {
        List<Book> bookList = bookDao.getBookList(0, 5);
        log.info("{}", bookList);

        List<Book> bookListEmpty = bookDao.getBookList(1000, 5);
        log.info("{}", bookListEmpty);

        List<Long> bookIds = Arrays.asList(1L, 2L);
        List<Book> bookListByArray = bookDao.getBookList(bookIds);
        log.info("{}", bookListByArray);

    }

    @Test
    void testUpdateBook() {
        Book book = bookDao.getBook(1L);
        Assert.notNull(book, "Book is null");
        book.setPrice(12345);

        boolean updateSuccess = bookDao.updateBook(book);
        Assert.isTrue(updateSuccess, "Update Book Fail");

        updateSuccess = bookModelDao.updateBook(book);
        Assert.isTrue(updateSuccess, "Update Book Fail");
    }

    @Test
    void testDeleteBook() {
        Book book = new Book()
                .setBookName("Unit Test")
                .setPrice(123);
        Long bookId = bookDao.addBookGetId(book);
        Assert.isTrue(bookId > 0L, "Add book fail");

        boolean deleteSuccess = bookDao.deleteBook(bookId);
        Assert.isTrue(deleteSuccess, "Delete Book Fail");
    }
}
