package com.bytebard.librarymanagementsystem.controllers;

import com.bytebard.librarymanagementsystem.config.security.JwtUtil;
import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.services.PatronService;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PatronControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private PatronController patronController;

    @MockBean
    private PatronService patronService;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    public void test_getPatronsRequest() throws Exception {
        createTestPatron();
        mockMvc.perform(
                    get("/api/patrons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + loginAndGetToken())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data.[0].email", is("test.email")));
    }

    @Test
    public void test_getPatronByIdRequest() throws Exception {
        createTestPatron();
        mockMvc.perform(
                        get("/api/patrons/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + loginAndGetToken())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.data.email", is("test.email")));
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

    private void createTestPatron() throws NotFoundException {
        PatronDTO patron = new PatronDTO(
                1L,
                "test.firstname",
                "test.lastname",
                "test.email",
                "test.phone",
                LocalDate.now(),
                List.of()
        );

        Mockito.when(patronService.getAllPatrons()).thenReturn(List.of(patron));
        Mockito.when(patronService.getPatronById(1L)).thenReturn(patron);
    }
}
