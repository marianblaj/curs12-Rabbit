package ro.fastrackit.courseService.exchangeSubscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ro.fastrackit.courseService.repository.courseStudent.CourseStudentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberListener {

    private final CourseStudentRepository courseStudentRepository;

    @RabbitListener(queues = "#{fanoutQueue.name}")
    public void fanoutListener(String studentId){

        log.info("Received fanout event "+studentId);
        courseStudentRepository.deleteAllByStudentId(studentId);
    }
}
