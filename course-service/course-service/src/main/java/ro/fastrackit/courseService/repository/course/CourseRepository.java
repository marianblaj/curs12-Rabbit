package ro.fastrackit.courseService.repository.course;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fastrackit.courseService.model.entity.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
}
