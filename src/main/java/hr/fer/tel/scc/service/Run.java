package hr.fer.tel.scc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Run implements CommandLineRunner {
	public static final Logger LOG = LoggerFactory.getLogger(Run.class);
	
	@Autowired BookService bookService;
	@Autowired RabbitTemplate rabbitTemplate;

	@Override
	public void run(String... args) throws Exception {
		//manualTestingScenario1();
		//manualTestingScenario2();
		manualTestingScenario3();
	}

	private void manualTestingScenario1() {
		bookService.sendBook(new Book("foo"));
	}

	private void manualTestingScenario2() {
		// TODO Auto-generated method stub
		
	}

	private void manualTestingScenario3() {
		int noOfBooks = bookService.noOfBooks();
		rabbitTemplate.convertAndSend("input", "event", new Book("scenario3"));
		int timeout = 5000;
		while(timeout > 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeout -= 100;
			if(noOfBooks != bookService.noOfBooks())
				break;
		}
		int newNoOfBooks = bookService.noOfBooks();
		int noBookDifference = newNoOfBooks - noOfBooks;
		LOG.info("Book difference is {}.", noBookDifference);
		if(noBookDifference != 1)
			LOG.error("BOOK DIFFERENCE IS {}.", noBookDifference);
	}
}
