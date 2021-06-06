package ro.fastrackit.courseService.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fastrackit.courseService.exception.ValidationException;
import ro.fastrackit.courseService.repository.course.CourseRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class CourseValidator {
    private final CourseRepository repo;

    private Optional<ValidationException> exists(String courseId) {
        return repo.existsById(courseId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Id " + courseId + " does not exist.")));
    }

    public void validateExistsOrThrow(String courseId) {
        exists(courseId).ifPresent(ex -> {
            throw ex;
        });
    }
}
