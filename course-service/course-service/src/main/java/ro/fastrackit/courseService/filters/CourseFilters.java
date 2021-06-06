package ro.fastrackit.courseService.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class CourseFilters {
    String studentId;
}
