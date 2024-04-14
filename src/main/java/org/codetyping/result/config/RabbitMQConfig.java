package org.codetyping.result.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue resultsQueue() {
        return new Queue("results-queue", true);
    }

    @Bean
    public FanoutExchange authExchange() {
        return new FanoutExchange("auth-exchange");
    }

    @Bean
    public Binding deliveryBinding(Queue resultsQueue, FanoutExchange authExchange) {
        return BindingBuilder.bind(resultsQueue).to(authExchange);
    }
}
