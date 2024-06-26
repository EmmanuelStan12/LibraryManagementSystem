package com.bytebard.librarymanagementsystem.controllers;

import com.bytebard.librarymanagementsystem.config.security.JwtAuthenticationTokenProvider;
import com.bytebard.librarymanagementsystem.config.security.JwtUtil;
import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.Book;
import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.models.enums.Genre;
import com.bytebard.librarymanagementsystem.services.BookService;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.awaitility.Awaitility.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void test_getBooksRequest() throws Exception {
        createTestBook();

        mockMvc.perform(
                    get("/api/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + loginAndGetToken())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data.[0].title", is("test.title")));
    }

    @Test
    public void test_getBookByIdRequest() throws Exception {
        createTestBook();
        mockMvc.perform(
                        get("/api/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginAndGetToken())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.data.title", is("test.title")));
    }

    private String loginAndGetToken() throws AlreadyExistsException {
        User user = new User(
                1L,
                "test.firstname",
                "test.lastname",
                "test.email",
                "test.password",
                "test.username"
        );

        Mockito.when(userService.register(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userService.findByUsername("test.username")).thenReturn(user);
        return jwtUtil.generateToken("test.username");
    }

    private void createTestBook() throws NotFoundException {
        BookDTO book = new BookDTO(
                1L,
                "test.title",
                "test.author",
                LocalDate.now(),
                "test.description",
                12000,
                "Art",
                "test.isbn"
        );

        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(book));
        Mockito.when(bookService.getBookById(1L)).thenReturn(book);
    }
}
