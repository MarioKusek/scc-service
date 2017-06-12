package hr.fer.tel.scc.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
	private List<Book> books;
	private RabbitTemplate rabbitTemplate;
	
	public BookServiceImpl(RabbitTemplate rabbitTemplate) {
		books = new LinkedList<>();
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void sendBook(Book book) {
		rabbitTemplate.convertAndSend("output", "book", book); 
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
}
