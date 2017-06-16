package hr.fer.tel.scc.service;

import java.util.List;

public interface BookService {
	void sendBook(Book book, String replyTo);

	void newBook(Book book);
	
	Book getBook(int index);
	
	int noOfBooks();

	List<Book> getBooks();

}
