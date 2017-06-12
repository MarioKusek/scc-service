package hr.fer.tel.scc.service;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectioFactory) {
	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	    factory.setConnectionFactory(connectioFactory);
	    factory.setMessageConverter(new Jackson2JsonMessageConverter());
	    return factory;
	}
	
	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectioFactory) {
        RabbitTemplate template = new RabbitTemplate(connectioFactory);
        template.setMessageConverter(new JsonMessageConverter());
        return template;
    }

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}
