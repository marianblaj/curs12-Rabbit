package ro.fastrackit.courseService.exchangeSubscriber;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitSubscriberConfig {


    @Bean
    Queue sumQueue() {
        return new AnonymousQueue();
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fasttrackit.fanout");
    }

    @Bean
    Queue fanoutQueue() {
        return new AnonymousQueue();//se poate face si return new Queue("oriceNume"); daca vrem un nume al nostru
    }

    @Bean
    Binding fanoutBinding(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);//nu mai trebuie with pt ca le ignora deoarece fanout trimite la toata lumea
    }

    @Bean
    public MessageConverter jsonMessageConverte() {
        return new Jackson2JsonMessageConverter();
    }

}
