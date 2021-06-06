package ro.fastrackit.courseService.service.student;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ro.fastrackit.courseService.filters.StudentFilters;
import ro.fastrackit.courseService.mapper.StudentMapper;
import ro.fastrackit.courseService.model.CollectionResponse;
import ro.fastrackit.courseService.model.PageInfo;
import ro.fastrackit.courseService.model.dto.StudentDto;
import ro.fastrackit.courseService.model.entity.Student;
import ro.fastrackit.courseService.repository.course.CourseRepository;
import ro.fastrackit.courseService.repository.courseStudent.CourseStudentRepository;
import ro.fastrackit.courseService.repository.student.StudentDao;
import ro.fastrackit.courseService.repository.student.StudentRepository;

@Service
@Builder
@RequiredArgsConstructor
public class StudentService {
    private final StudentDao studentDao;
    private final StudentRepository repo;
    private final StudentValidator studentValidator;
    private final CourseStudentRepository courseStudentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;


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
}

