package ro.fastrackit.studentservice;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ro.fastrackit.studentservice.model.entity.Student;
import ro.fastrackit.studentservice.repository.StudentRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentServiceTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    private StudentRepository repository;

    @SneakyThrows
    @Test
    @DisplayName("GET /students")
    void getStudentsTest() {
        repository.saveAll(List.of(
                Student.builder()
                        .name("Alina")
                        .age(24).build(),
                Student.builder()
                        .name("Sandu")
                        .age(21).build()
        ));
        mvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.[1].name", CoreMatchers.is("Sandu")));
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }
}
