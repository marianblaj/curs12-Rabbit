package ro.fastrackit.courseService.repository.courseStudent;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fastrackit.courseService.model.entity.CourseStudent;

import java.util.List;

public interface CourseStudentRepository extends MongoRepository<CourseStudent, String> {

    List<CourseStudent> findAllByCourseId(String courseId);
    void deleteAllByStudentId(String studentId);
}
