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
				bookName: 'foo'
		])
		messageHeaders { 
			messagingContentType(applicationJson())
		}
	}

	outputMessage {
		sentTo('output')
		body('''{ "bookName" : "foo" }''')
		headers {
			messagingContentType(applicationJson())
		}
	}
}
