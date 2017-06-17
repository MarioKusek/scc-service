package contracts.sensor

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description("""
Represents scenario 2 from documentation: 
http://cloud.spring.io/spring-cloud-contract/spring-cloud-contract.html#_publisher_side_test_generation

"The input message triggers an output message."

```
given:
	rabbit service is running
when:
	input message is received
then:
	message is send
```

""")
	label 'some_label2'
	input {
		messageFrom('input')
		messageBody([
				bookName: 'foo2'
		])
		messageHeaders { 
			messagingContentType(applicationJson())
			header('amqp_replyTo', 'amq.rabbitmq.reply-to')
			header('bill', 'bill')
		}
	}

	outputMessage {
		sentTo('')
		body('''{ "bookName" : "foo2" }''')
		headers {
			messagingContentType(applicationJson())
		}
	}
}
