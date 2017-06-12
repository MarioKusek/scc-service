package hr.fer.tel.scc.service;

public interface BookService {
	void sendBook(Book book);

	void newBook(Book book);
	
	Book getBook(int index);
	
	int noOfBooks();
}
