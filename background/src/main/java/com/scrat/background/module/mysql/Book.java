package com.scrat.background.module.mysql;

public class Book {
    private long bookId;
    private String bookName;
    private int price;
    private int colNotInDb;

    public long getBookId() {
        return bookId;
    }

    public Book setBookId(long bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public Book setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Book setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getColNotInDb() {
        return colNotInDb;
    }

    public Book setColNotInDb(int colNotInDb) {
        this.colNotInDb = colNotInDb;
        return this;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                ", colNotInDb=" + colNotInDb +
                '}';
    }
}
