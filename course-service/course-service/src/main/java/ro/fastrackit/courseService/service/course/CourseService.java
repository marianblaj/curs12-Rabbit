package ro.fastrackit.courseService.service.course;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ro.fastrackit.courseService.exception.ResourceNotFoundException;
import ro.fastrackit.courseService.filters.CourseFilters;
import ro.fastrackit.courseService.mapper.CourseMapper;
import ro.fastrackit.courseService.mapper.StudentMapper;
import ro.fastrackit.courseService.model.CollectionResponse;
import ro.fastrackit.courseService.model.dto.StudentDto;
import ro.fastrackit.courseService.model.entity.Course;
import ro.fastrackit.courseService.model.dto.CourseDto;
import ro.fastrackit.courseService.model.PageInfo;
import ro.fastrackit.courseService.model.entity.CourseStudent;
import ro.fastrackit.courseService.model.entity.Student;
import ro.fastrackit.courseService.repository.course.CourseDao;
import ro.fastrackit.courseService.repository.course.CourseRepository;
import ro.fastrackit.courseService.repository.courseStudent.CourseStudentRepository;
import ro.fastrackit.courseService.repository.student.StudentRepository;
import ro.fastrackit.courseService.service.student.StudentValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseDao courseDao;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseValidator courseValidator;
    private final StudentValidator studentvalidator;
    private final StudentMapper studentMapper;
    private final CourseStudentRepository courseStudentRepository;
    private final CourseMapper courseMapper;

    public CollectionResponse<CourseDto> getAll(CourseFilters courseFilters) {
        Page<Course> page = courseDao.getAllWithFilters(courseFilters);

        return CollectionResponse.<CourseDto>builder()
                .content(courseMapper.toDtoList(page.getContent()))
                .pageInfo(PageInfo.builder()
                        .totalPages(page.getTotalPages())
                        .totalElements(page.getNumberOfElements())
                        .crtPage(page.getNumber())
                        .pageSize(page.getSize())
                        .build())
                .build();
    }

    public Course addCourse(Course newCourse) {
        return courseRepository.save(newCourse);
    }

    public CourseDto getCourseId(String courseId) {
        courseValidator.validateExistsOrThrow(courseId);
        Course dbCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find course with id " + courseId));
        return new CourseDto(dbCourse.getDiscipline());
    }

    public List<StudentDto> getCourseIdStudents(String courseId) {
        courseValidator.validateExistsOrThrow(courseId);

        List<CourseStudent> studentsId = courseStudentRepository.findAllByCourseId(courseId);
        List<Student> students = studentsId.stream()
                .map(element -> studentRepository.findById(element.getStudentId()).orElseThrow())
                .collect(Collectors.toList());

        return studentMapper.toDtoList(students);
    }

    public CourseStudent addStudentIdCourseId(String studentId, String courseId) {
        studentvalidator.validateExistsOrThrow(studentId);
        courseValidator.validateExistsOrThrow(courseId);

        return courseStudentRepository.save(
                CourseStudent.builder()
                        .courseId(courseId)
                        .studentId(studentId)
                        .build());
    }
}
