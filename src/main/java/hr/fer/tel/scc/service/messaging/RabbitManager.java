package hr.fer.tel.scc.service.messaging;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Headers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import hr.fer.tel.scc.service.Book;
import hr.fer.tel.scc.service.BookService;

@Component
public class RabbitManager {

	public static final Logger LOG = LoggerFactory.getLogger(RabbitManager.class);
    
    
	private BookService service;
    private RabbitTemplate rabbitTemplate;

    @Autowired
	public RabbitManager(BookService service, RabbitTemplate rabbitTemplate) {
		this.service = service;
		this.rabbitTemplate = rabbitTemplate;
	}

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(),
			exchange = @Exchange(
					value = "input",
					durable="true",
					autoDelete="false",
					type="topic"),
			key = "event"
	))
	public void newBook(Book book, @Headers Map<String, String> headers) {
		LOG.info("Received new book with bookname = " + book.getBookName());
		LOG.info("Headers = " + headers);
		service.sendBook(book, headers.get("amqp_replyTo"));
	}

    // If we uncomment this scenario2 fails because ContractVerifier interprets the destination
    // as just only the exchange, not the combination of the exchange and the routing key  
    // Also, we cannot make RabbitListener return a value because there is not channel opened, so it fails.
    // However, we can use RabbitTemplate to return a value manually since this has an open channel  
	
	// @RabbitListener(bindings = @QueueBinding(
	// 		value = @Queue(),
	// 		exchange = @Exchange(
	// 				value = "input",
	// 				durable="true",
	// 				autoDelete="false",
	// 				type="topic"),
	// 		key = "event2"
	// ))
	// public void newBook2(Book book, @Headers Map<String, String> headers) {
	// 	LOG.info("newBook2 Received new book with bookname = " + book.getBookName());
	// 	LOG.info("newBook2 Headers = " + headers);
	// 	service.sendBook(book, headers.get("amqp_replyTo"));
	// }
}
