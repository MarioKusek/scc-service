package hr.fer.tel.contracts;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;

import hr.fer.tel.scc.service.Book;
import hr.fer.tel.scc.service.BookService;
import hr.fer.tel.scc.service.ServiceApplication;
import hr.fer.tel.scc.service.messaging.RabbitManager;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class, RabbitManager.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)

@AutoConfigureMessageVerifier
public abstract class SensorBase {
	@Autowired private MessageVerifier verifier;
	
	@Autowired RabbitTemplate rabbitTemplate;
	@Autowired BookService bookService;
	
	public void bookReturnedTriggered() {
		bookService.sendBook(new Book("foo"));
	}
	
	public void bookEventWasReceived() {
		// TODO check that book was received
	}
	
	public void emitGetSensorDataEvent() {
		//rabbitTemplate.convertSendAndReceive("sensor-service", "sensor.read", "");
//		rabbitTemplate.send("sensor-service", "sensor.read",
//              MessageBuilder.withBody("{ \"value\": 45, \"name\": \"testSensor\" }".getBytes()).build());
		rabbitTemplate.send("sensor-service", "sensor.read", 
				MessageBuilder.withBody("".getBytes())
					.setReplyTo("sensor-client")
					.build());
	}
}
