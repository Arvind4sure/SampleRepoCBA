package com.test.catalogue.service;

import com.test.catalogue.dto.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookCatelogueService {

    String addBook(Book book);

    String deleteBookById(Long id);

    void updateBook(Long id, String title, String author, String isbn, LocalDate publishingDate);

    List<Book> getBooks(String title, String author, String isbn);
}
