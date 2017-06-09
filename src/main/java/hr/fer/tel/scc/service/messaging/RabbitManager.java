package hr.fer.tel.scc.service.messaging;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitManager {
	
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
