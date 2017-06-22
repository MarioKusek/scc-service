package hr.fer.tel.scc.service.messaging;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import hr.fer.tel.scc.service.Book;
import hr.fer.tel.scc.service.BookService;

@Component
public class NewBook extends RabbitManager {
	public NewBook(BookService service, RabbitTemplate rabbitTemplate) {
		super(service, rabbitTemplate);
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
}