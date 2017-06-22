package hr.fer.tel.scc.service.messaging;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfiguration {
	@Bean
	public ConnectionFactory connectionFactory() throws Exception {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		// connectionFactory.setPublisherConfirms(true);
		// connectionFactory.setPublisherReturns(true);
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
	}

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
}
