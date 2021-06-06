package ro.fastrackit.courseService.repository.student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fastrackit.courseService.model.entity.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

}
