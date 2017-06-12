package hr.fer.tel.scc.service.messaging;

import java.util.LinkedList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import hr.fer.tel.scc.service.Book;
import hr.fer.tel.scc.service.BookService;

@Component
public class RabbitManager {
	private BookService service;

	public RabbitManager(BookService service) {
		this.service = service;
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
	public void newBook(Book book) {
		service.newBook(book);
	}

	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(),
			exchange = @Exchange(
					value = "${rabbit.exchange.service.name:sensor-service}",
					durable="${rabbit.exchange.service.durable:true}",
					autoDelete="${rabbit.exchange.service.autodelete:false}",
					type="direct"),
			key = "${rabbit.routingKey.service.read:sensor.read}"
	))
	public Reading read() {
		return new Reading(45, "testSensor");
	}
}
