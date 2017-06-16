package hr.fer.tel.scc.service;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
	public static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

	private List<Book> books;
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	public BookServiceImpl(RabbitTemplate rabbitTemplate) {
		books = new LinkedList<>();
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void sendBook(Book book, String replyTo) {
		LOG.info("Received new book with bookname = " + book.getBookName());
		newBook(book);
		rabbitTemplate.convertAndSend("", replyTo, book); 
	}

	@Override
	public void newBook(Book book) {
		books.add(book);
	}

	@Override
	public Book getBook(int index) {
		return books.get(index);
	}

	@Override
	public int noOfBooks() {
		return books.size();
	}

	@Override
	public List<Book> getBooks() {
		return books;
	}
}
