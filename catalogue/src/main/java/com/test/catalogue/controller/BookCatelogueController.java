package com.test.catalogue.controller;

import com.test.catalogue.dto.Book;
import com.test.catalogue.service.BookCatelogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BookCatelogueController {

    Logger logger = LoggerFactory.getLogger(BookCatelogueController.class);
    @Autowired
    private BookCatelogueService bookCatelogueService;


    @GetMapping("/books")
    public List<Book> getBooks(@RequestParam(required = false) Optional<String> title, @RequestParam(required = false) Optional<String> author, @RequestParam(required = false) Optional<String> isbn) {
        logger.info("getting books..");
        String title1 = title.isPresent() ? title.get() : null;
        String author1 = author.isPresent() ? author.get() : null;
        String isbn1 = isbn.isPresent() ? isbn.get() : null;
        return bookCatelogueService.getBooks(title1, author1, isbn1);
    }

    @PostMapping("/book")
    public ResponseEntity<Object> addBook(@Valid @RequestBody Book book) {
        bookCatelogueService.addBook(book);
        URI path = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{tile}")
                .buildAndExpand(book.getTitle())
                .toUri();

        return ResponseEntity.created(path).build();

    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable String id) {
        bookCatelogueService.deleteBookById(new Long(id));
        return "book with Id " + id + " deleted";
    }

    @PutMapping("/updateBook")
    public String updateBook(@RequestBody Book book) {
        bookCatelogueService.updateBook(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishingDate());
        return "book updated!!";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
