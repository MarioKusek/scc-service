package hr.fer.tel.contracts;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;

import hr.fer.tel.scc.service.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class SensorBase {
	@Autowired
	private MessageVerifier verifier;
	
	@Autowired RabbitTemplate rabbitTemplate;
	
	public void emitGetSensorDataEvent() {
		verifier.send("", new HashedMap<>(), "sensor-service");
		//rabbitTemplate.convertSendAndReceive("sensor-service", "sensor.read", "");
	}
}
