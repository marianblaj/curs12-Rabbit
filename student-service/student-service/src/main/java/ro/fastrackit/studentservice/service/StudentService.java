package ro.fastrackit.studentservice.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ro.fastrackit.studentservice.exception.ResourceNotFoundException;
import ro.fastrackit.studentservice.exchange.publisher.PublisherService;
import ro.fastrackit.studentservice.mapper.StudentMapper;
import ro.fastrackit.studentservice.model.CollectionResponse;
import ro.fastrackit.studentservice.model.PageInfo;
import ro.fastrackit.studentservice.model.dto.StudentDto;
import ro.fastrackit.studentservice.model.entity.Student;
import ro.fastrackit.studentservice.model.filters.StudentFilters;
import ro.fastrackit.studentservice.repository.StudentDao;
import ro.fastrackit.studentservice.repository.StudentRepository;

@Service
@Builder
@RequiredArgsConstructor
public class StudentService {
    private final StudentDao studentDao;
    private final StudentRepository repo;
    private final StudentValidator studentValidator;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PublisherService publisherService;

    public CollectionResponse<StudentDto> getAll(StudentFilters studentFilters) {

        Page<Student> page = studentDao.getAllWithFilters(studentFilters);

        return CollectionResponse.<StudentDto>builder()
                .content(studentMapper.toDtoList(page.getContent()))
                .pageInfo(PageInfo.builder()
                        .totalPages(page.getTotalPages())
                        .totalElements(page.getNumberOfElements())
                        .crtPage(page.getNumber())
                        .pageSize(page.getSize())
                        .build())
                .build();

    }

    public Student addStudent(Student newStudent) {
        return repo.save(newStudent);
    }

    public StudentDto getStudentId(String studentId) {
        studentValidator.validateExistsOrThrow(studentId);
        Student dbStudent = repo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find student with id " + studentId));
        return StudentDto.builder()
                .name(dbStudent.getName())
                .age(dbStudent.getAge()).build();
    }

    public void deleteStudent(String studentId) {
        studentValidator.validateExistsOrThrow(studentId);
         repo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find student with id " + studentId));
        repo.deleteById(studentId);

        publisherService.publishToFanout(studentId);
    }
}

