package hr.fer.tel.scc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.fer.tel.scc.service.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceApplicationTests {

	public static final Logger LOG = LoggerFactory.getLogger(ServiceApplicationTests.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

	@Test
	public void contextLoads() {
		Book book = new Book();
		book.setBookName("foo4");
        Book newBook = (Book) rabbitTemplate.convertSendAndReceive("input", "event", book); 
        LOG.info("newbook = " + newBook.getBookName());
	}

}
