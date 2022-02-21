package com.techreturners.bookmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techreturners.bookmanager.exception.ApiRequestException;
import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.model.Genre;
import com.techreturners.bookmanager.service.BookManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class BookManagerExceptionTests {
    @Mock
    private BookManagerServiceImpl mockBookManagerServiceImpl;

    @InjectMocks
    private BookManagerController bookManagerController;

    @Autowired
    private MockMvc mockMvcController;



    @BeforeEach
    public void setup() {

        mockMvcController = MockMvcBuilders.standaloneSetup(bookManagerController).build();
        ObjectMapper  mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllBooksEmptylist() throws Exception {

        List<Book> books = new ArrayList<>();

        when(mockBookManagerServiceImpl.getAllBooks()).thenReturn(books);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/book/"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testBookByIdNotFound() throws Exception {

        Book nonexistentBook = new Book();
        nonexistentBook.setId(40L);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/book/" + nonexistentBook.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateNonExistentBookById() throws Exception {

        Book nonexistentBook = new Book();
        nonexistentBook.setId(44L);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.put("/api/v1/book/" + nonexistentBook.getId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
