package ro.fastrackit.courseService.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fastrackit.courseService.filters.CourseFilters;
import ro.fastrackit.courseService.mapper.CourseMapper;
import ro.fastrackit.courseService.model.CollectionResponse;
import ro.fastrackit.courseService.model.dto.CourseDto;
import ro.fastrackit.courseService.model.dto.StudentDto;
import ro.fastrackit.courseService.model.entity.Course;
import ro.fastrackit.courseService.model.entity.CourseStudent;
import ro.fastrackit.courseService.service.course.CourseService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @GetMapping("/courses")
    CollectionResponse<CourseDto> getAll(CourseFilters courseFilters) {
        return courseService.getAll(courseFilters);
    }

    @GetMapping("/courses/{courseId}")
    CourseDto getCoursetId(@PathVariable String courseId) {
        return courseService.getCourseId(courseId);
    }

    @GetMapping("/course/{courseId}/students")
    List<StudentDto> getCourseIdStudents(@PathVariable String courseId) {
        return courseService.getCourseIdStudents(courseId);
    }

    @PostMapping("/courses")
    Course addCourse(@RequestBody Course newCourse) {
        return courseService.addCourse(newCourse);
    }

    @PostMapping("/course/{courseId}/students/{studentId}")
    CourseStudent addStudentCourseId(@PathVariable String studentId, @PathVariable String courseId) {
        return courseService.addStudentIdCourseId(studentId, courseId);
    }
}
