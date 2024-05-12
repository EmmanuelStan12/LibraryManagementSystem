package com.bytebard.librarymanagementsystem.services;

import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.CreatePatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.UpdatePatronDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.models.enums.Genre;
import com.bytebard.librarymanagementsystem.repository.PatronRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PatronServiceTest {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private PatronService patronService;

    @Test
    public void test_GetPatronById() throws NotFoundException {
        Long id = createTestPatron();
        PatronDTO patron = patronService.getPatronById(id);
        assertEquals(patron.getId(), id);
    }

    @Test
    public void test_GetPatronById_ThrowsException() {
        Long id = createTestPatron();
        assertThrows(NotFoundException.class, () -> patronService.getPatronById(id + 1));
    }

    @Test
    public void test_GetPatrons() {
        createTestPatron();
        List<PatronDTO> patrons = patronService.getAllPatrons();
        assertFalse(patrons.isEmpty());
        assertEquals(1, patrons.size());
    }

    @Test
    public void test_SavePatron() {
        CreatePatronDTO createPatronDTO = new CreatePatronDTO(
                "test.firstname",
                "test.lastname",
                "test.email",
                "test.phone"
        );
        PatronDTO patron = patronService.createPatron(createPatronDTO);
        assertNotNull(patron.getId());
        assertEquals("test.firstname", patron.getFirstName());
    }

    @Test
    public void test_UpdatePatron() {
        Long id = createTestPatron();
        UpdatePatronDTO patronDTO = new UpdatePatronDTO(
                "test.firstname",
                "test.lastname",
                "test.email",
                "test.phone.updated"
        );
        assertDoesNotThrow(() -> {
            PatronDTO patron = patronService.updatePatron(patronDTO, id);
            assertEquals(patron.getId(), id);
            assertEquals("test.phone.updated", patron.getPhone());
        });
    }

    @Test
    public void test_UpdatePatron_NonExistentPatron() {
        Long id = createTestPatron();
        UpdatePatronDTO patronDTO = new UpdatePatronDTO(
                "test.firstname",
                "test.lastname",
                "test.email",
                "test.phone.updated"
        );
        assertThrows(NotFoundException.class, () -> patronService.updatePatron(patronDTO, id + 1));
    }

    @Test
    public void test_DeletePatron_NonExistentPatron() {
        Long id = createTestPatron();
        assertThrows(NotFoundException.class, () -> patronService.deletePatron(id + 1));
    }

    @Test
    public void test_DeletePatron() {
        Long id = createTestPatron();
        assertDoesNotThrow(() -> patronService.deletePatron(id));
        assertThrows(NotFoundException.class, () -> patronService.getPatronById(id));
    }

    private Long createTestPatron() {
        Patron patron = new Patron(
                null,
                "test.firstname",
                "test.lastname",
                "test.email",
                "test.phone",
                LocalDate.now(),
                List.of()
        );

        patron = patronRepository.save(patron);
        return patron.getId();
    }
}
