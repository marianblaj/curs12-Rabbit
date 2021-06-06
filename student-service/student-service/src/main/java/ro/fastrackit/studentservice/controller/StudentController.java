package ro.fastrackit.studentservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fastrackit.studentservice.model.CollectionResponse;
import ro.fastrackit.studentservice.model.dto.StudentDto;
import ro.fastrackit.studentservice.model.entity.Student;
import ro.fastrackit.studentservice.model.filters.StudentFilters;
import ro.fastrackit.studentservice.service.StudentService;


@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    CollectionResponse<StudentDto> getAll(StudentFilters studentFilters) {
        return studentService.getAll(studentFilters);
    }

    @GetMapping("/{studentId}")
    StudentDto getStudentId(@PathVariable String studentId) {
        return studentService.getStudentId(studentId);
    }

    @PostMapping
    Student addStudent(@RequestBody Student newStudent) {
        return studentService.addStudent(newStudent);
    }

    @DeleteMapping("/{studentId}")
    void deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);
    }

}