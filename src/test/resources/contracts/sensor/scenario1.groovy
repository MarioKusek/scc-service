package contracts.sensor

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description("""
Represents scenario 1 from documentation: 
http://cloud.spring.io/spring-cloud-contract/spring-cloud-contract.html#_publisher_side_test_generation

"There is no input message that produces an output one. The output message is triggered by a 
component inside the application (e.g. scheduler)"

```
given:
	rabbit service is running
when:
	bookReturnedTriggered method is called
then:
	message is send
```

""")
	label 'some_label1'
	input {
		triggeredBy('bookReturnedTriggered()')
	}

	outputMessage {
		sentTo('output')
		body('''{ "bookName" : "foo" }''')
		headers {
			messagingContentType(applicationJson())
		}
	}
}
