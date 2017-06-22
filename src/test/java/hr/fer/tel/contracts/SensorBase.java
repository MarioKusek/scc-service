package hr.fer.tel.contracts;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.fer.tel.scc.service.Book;
import hr.fer.tel.scc.service.BookService;
import hr.fer.tel.scc.service.BookServiceImpl;
import hr.fer.tel.scc.service.messaging.NewBook;
import hr.fer.tel.scc.service.messaging.RabbitConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RabbitConfiguration.class, NewBook.class, BookServiceImpl.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)

@AutoConfigureMessageVerifier
public abstract class SensorBase {

	public static final Logger LOG = LoggerFactory.getLogger(SensorBase.class);
	@Autowired private MessageVerifier verifier;
	
	@Autowired RabbitTemplate rabbitTemplate;
	@Autowired BookService bookService;
	
	public void bookReturnedTriggered() {
		bookService.sendBook(new Book("foo1"), "output");
	}
	
	public void bookEventWasReceived() {

		boolean foundBook = false;
		List<Book> booklist =  bookService.getBooks();
		LOG.info("Bookservice has " + bookService.noOfBooks() + " books");

		for(Book book : booklist) {
	        if(book != null && book.getBookName().equals("foo3")) {
	                foundBook = true;
	            
	        }
	    }

		assertEquals(foundBook, true);
	}
}
