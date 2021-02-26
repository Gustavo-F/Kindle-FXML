package entities;

import java.util.ArrayList;

public class Book {
	
	private int id;
	private String title;
	private int pages;
	private ArrayList<Writer> writers;
	private ArrayList<Genre> genres;
	private Publisher publisher;
	
	public Book() {
		this.writers = new ArrayList<Writer>();
		this.genres = new ArrayList<Genre>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPages() {
		return pages;
	}

	public Boolean setPages(int pages) {
		if(pages < 0)
			return false;
		
		this.pages = pages;
		return true;
	}

	public ArrayList<Writer> getWriters() {
		return writers;
	}

	public void setWriters(ArrayList<Writer> writers) {
		this.writers = writers;
	}

	public ArrayList<Genre> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
}
