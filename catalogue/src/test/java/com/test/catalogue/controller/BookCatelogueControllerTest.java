package com.test.catalogue.controller;

import com.test.catalogue.dto.Book;
import com.test.catalogue.service.BookCatelogueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookCatelogueController.class)
public class BookCatelogueControllerTest {
    long num1 = 1L;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookCatelogueService bookCatelogueService;
    private Book sampleBook = new Book(num1, "test", "test1", "1234567891012", LocalDate.parse("2020-01-08"));


    @Test
    public void testGetBooks() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("title", "test");
        requestParams.add("author", "test1");
        requestParams.add("isbn", "1234567891012");
        List<Book> bookArrayList = new ArrayList<>();
        bookArrayList.add(sampleBook);
        when(
                bookCatelogueService.getBooks(anyString(), anyString(), anyString())).thenReturn(bookArrayList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/books").params(requestParams);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print());

    }

    @Test
    public void testAddBook() throws Exception {
        String sampleBookString = "{\"title\": \"book8\",\"author\": \"Rahul Ramji, Arvind Kumar, Anand\",\"isbn\": \"1234567891017\",\"publishingDate\": \"2022-12-01\"}";
        when(bookCatelogueService.addBook(Mockito.any(Book.class))).thenReturn("success");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/book").accept(MediaType.APPLICATION_JSON).content(sampleBookString)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(print());

    }

    @Test
    public void testAddBookInvalidISBN() throws Exception {
        String sampleBookString = "{\"title\": \"book8\",\"author\": \"Rahul Ramji, Arvind Kumar, Anand\",\"isbn\": \"1234567\",\"publishingDate\": \"2022-12-01\"}";
        when(bookCatelogueService.addBook(Mockito.any(Book.class))).thenReturn("success");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/book").accept(MediaType.APPLICATION_JSON).content(sampleBookString)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andDo(print());

    }

    @Test
    public void testAddBookInvalidPublishingDate() throws Exception {
        String sampleBookString = "{\"title\": \"book8\",\"author\": \"Rahul Ramji, Arvind Kumar, Anand\",\"isbn\": \"1234567\",\"publishingDate\": \"2022-14-01\"}";
        when(bookCatelogueService.addBook(Mockito.any(Book.class))).thenReturn("success");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/book").accept(MediaType.APPLICATION_JSON).content(sampleBookString)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andDo(print());

    }

    @Test
    public void testDeleteBook() throws Exception {
        when(bookCatelogueService.deleteBookById(anyLong())).thenReturn("success");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateBook() throws Exception {
        String sampleBookString = "{\"id\": 4,\"title\": \"book8\",\"author\": \"Rahul Ramji, Arvind Kumar, Anand\",\"isbn\": \"1234567891017\",\"publishingDate\": \"2022-12-01\"}";
        when(bookCatelogueService.addBook(Mockito.any(Book.class))).thenReturn("success");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                        "/updateBook").accept(MediaType.APPLICATION_JSON).content(sampleBookString)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print());

    }


}
