package ro.fastrackit.studentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fastrackit.studentservice.exception.ValidationException;
import ro.fastrackit.studentservice.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class StudentValidator {
    private final StudentRepository repo;

    private Optional<ValidationException> exists(String studentId) {
        return repo.existsById(studentId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Id " + studentId + " does not exist.")));
    }

    public void validateExistsOrThrow(String studentId) {
        exists(studentId).ifPresent(ex -> {
            throw ex;
        });
    }
}