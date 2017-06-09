package contracts.sensor

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description("""
Represents a successful scenario of getting a sensor data

```
given:
	one sensor reading is available
when:
	get request is send
then:
	sensor data is returned
```

""")
	label 'get_sensor_data'
	input {
		//messageFrom('rabbitManager')
		//messageBody()
		//messageHeaders { }
		
		triggeredBy('emitGetSensorDataEvent()')
	}

	outputMessage {
		// destination to which the output message will be sent
		sentTo('sensor-service')
		// the body of the output message
		body('''{ 
			"name": "testSensor",
			"value": 45
		}''')
		headers { }
	}
}
