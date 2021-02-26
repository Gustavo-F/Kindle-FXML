package entities;

import java.util.ArrayList;

public class Library {

	private ArrayList<Book> books;
	
	public Library() {
		books = new ArrayList<Book>();
	}

	public ArrayList<Book> getBooks() {
		return books;
	}
}
