package com.test.catalogue.repository;

import com.test.catalogue.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select u from Book u where u.title=?1")
    List<Book> getBookByTitle(String title);

    @Query("select u from Book u where u.isbn=?1")
    List<Book> getBookByISBN(String isbn);

    @Transactional
    @Modifying
    @Query("update Book u set u.title=?2, u.author=?3, u.isbn=?4, u.publishingDate=?5 where u.id=?1")
    void updateBook(Long id, String title, String author, String isbn, LocalDate publishingDate);

    @Query("select u from Book u where u.author like '%+?1+%'")
    List<Book> getBookByAuthor(String author);
}
