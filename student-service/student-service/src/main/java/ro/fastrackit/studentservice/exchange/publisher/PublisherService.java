package ro.fastrackit.studentservice.exchange.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    public void publishToFanout(String studentId){
        rabbitTemplate.convertAndSend(
                fanoutExchange.getName(),
                "",
                studentId);
    }
}
