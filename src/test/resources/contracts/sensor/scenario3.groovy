package contracts.sensor

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description("""
Represents scenario 3 from documentation: 
http://cloud.spring.io/spring-cloud-contract/spring-cloud-contract.html#_publisher_side_test_generation

"The input message is consumed and there is no output message."

```
given:
	rabbit service is running
when:
	input message is received
then:
	assert that book event was received
```

""")
	label 'some_label3'
	input {
		messageFrom('input')
		messageBody([
				bookName: 'foo3'
		])
		messageHeaders { 
			messagingContentType(applicationJson())
		}
		assertThat('bookEventWasReceived()')
	}
}
