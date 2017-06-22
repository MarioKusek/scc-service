package hr.fer.tel.scc.service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.fer.tel.scc.service.BookService;

public class RabbitManager {
	public static final Logger LOG = LoggerFactory.getLogger(RabbitManager.class);
    
	protected BookService service;
    protected RabbitTemplate rabbitTemplate;

	public RabbitManager(BookService service, RabbitTemplate rabbitTemplate) {
		this.service = service;
		this.rabbitTemplate = rabbitTemplate;
	}
}