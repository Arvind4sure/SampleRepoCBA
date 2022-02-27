package com.test.catalogue.service;

import com.test.catalogue.dto.Book;
import com.test.catalogue.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookCatelogueServiceImpl implements BookCatelogueService {

    Logger logger = LoggerFactory.getLogger(BookCatelogueServiceImpl.class);
    @Autowired
    private BookRepository bookRepository;

    @Override
    public String addBook(Book book) {
        bookRepository.save(book);
        logger.info("book added - {}", book);
        return "success";

    }

    @Override
    public String deleteBookById(Long id) {
        bookRepository.deleteById(new Long(id));
        logger.info("book with {} deleted from the DB", id);
        return "success";

    }

    @Override
    public void updateBook(Long id, String title, String author, String isbn, LocalDate publishingDate) {
        bookRepository.updateBook(id, title, author, isbn, publishingDate);

    }

    @Override
    public List<Book> getBooks(String title, String author, String isbn) {
        List<Book> bookList = null;
        Book book = new Book(title, isbn);
        if (!(author == null)) {
            bookList = bookRepository.findAll().stream().filter(book1 -> book1.getAuthor().toUpperCase().contains(author.toUpperCase())).collect(Collectors.toList());
            if (!(title == null)) {
                bookList = bookList.stream().filter(book1 -> book1.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
            }
            if (!(isbn == null)) {
                bookList = bookList.stream().filter(book1 -> book1.getIsbn().equals(isbn)).collect(Collectors.toList());
            }
        } else {
            bookList = bookRepository.findAll(Example.of(book));
        }
        return bookList;
    }
}
