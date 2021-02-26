package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.gustavoferreira.kindle_fxml.AlertUtil;
import entities.Book;
import entities.Genre;
import entities.Publisher;
import entities.Writer;

public class BookDAO {
	
	public void add(Book reference, String publisherName) {
		int publisherId = PublisherDAO.getPublisher(publisherName).getId();
		
		String sql = "INSERT INTO book (title, pages, publisher_id) VALUES ('" + reference.getTitle() + "', "
				+ "" + reference.getPages() + ", " + publisherId + ")";
		UtilDB.changeDB(sql);
	}

	public void remove(Book reference) {
		String sql = "DELETE FROM book_user WHERE book_id = " + reference.getId() + ";";
		UtilDB.changeDB(sql);
		
		sql = "DELETE FROM book_genre WHERE book_id = " + reference.getId() + ";";
		UtilDB.changeDB(sql);
		
		sql = "DELETE FROM book_writer WHERE book_id = " + reference.getId() + ";";
		UtilDB.changeDB(sql);
		
		sql = "DELETE FROM book WHERE book_id = " + reference.getId() + ";";
		UtilDB.changeDB(sql);
	}

	public ArrayList<Book> list() {
		String sql = "SELECT * FROM book;";
		ResultSet rs = UtilDB.getData(sql);
		ArrayList<Book> books = new ArrayList<>();
		
		try {
			while(rs.next()) {
				Book book = new Book();
				
				book.setId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setPages(rs.getInt("pages"));
				book.setWriters(getBookWriters(book.getId()));
				book.setGenres(getBookGenres(book.getId()));
				book.setPublisher(PublisherDAO.getPublisher(rs.getInt("publisher_id")));				
				
				books.add(book);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return books;
	}
	
	public static  ArrayList<Writer> getBookWriters(int bookID){
		String sql = "SELECT * FROM writers_book_vw WHERE book_id = " + bookID + "; ";
		ResultSet rs = UtilDB.getData(sql);
		ArrayList<Writer> writers = new ArrayList<Writer>();
		
		try{
			while(rs.next()) {
				Writer writer = new Writer();
				writer.setId(rs.getInt("writer_id"));
				writer.setName(rs.getString("name"));
				writer.setSurname(rs.getString("surname"));
				writer.setEmail(rs.getString("email"));
				
				writers.add(writer);
			}
			
		}catch(Exception e) {
			AlertUtil.error("Erro", "Erro com livros", "Erro ao carregar escritores de livro", e);
		}
		
		return writers;
	}

	public static ArrayList<Genre> getBookGenres(int bookID){
		String sql = "SELECT genre FROM book_genre WHERE book_id = " + bookID + ";";
		ResultSet rs = UtilDB.getData(sql);
		ArrayList<Genre> genres = new ArrayList<Genre>();
		
		try {
			while(rs.next()) {
				Genre genre = new Genre(rs.getString("genre"));
				genres.add(genre);
			}
			
		}catch(Exception e) {
			AlertUtil.error("Erro", "Erro com livros", "Erro ao carregar gêneros de livro", e);
		}
		
		return genres;
	}
	
	public static int getBookID(Book book) {
		String sql = "SELECT book_id FROM book WHERE title = '" + book.getTitle() + "' AND pages = " + book.getPages() + ";";
		ResultSet rs = UtilDB.getData(sql);
		
		int id = 0;
		
		try {
			id = rs.getInt("book_id");
		} catch (SQLException e) {
			AlertUtil.error("Erro", "Buscar dados de livro", "Erro ao obter id de livro", e);
		}
		
		return id;
	}

	public static void addWriter(Book book, Writer writer) {
		String sql = "INSERT INTO book_writer (book_id, writer_id) VALUES (" + book.getId() + ", " 
				+ writer.getId() + ");";
		UtilDB.changeDB(sql);
	}
	
	public static void addGenre(Book book, String genre) {
		String sql = "INSERT INTO book_genre (genre, book_id) VALUES ('" + genre + "', " + book.getId() + ");";
		UtilDB.changeDB(sql);
	}
	
	public static void removeWriter(Book book, Writer writer) {
		String sql = "DELETE FROM book_writer WHERE book_id = " + book.getId() + " AND writer_id = " 
				+ writer.getId() + ";";
		UtilDB.changeDB(sql);
	}
	
	public static void removeGenre(Book book, String genre) {
		String sql = "DELETE FROM book_genre WHERE book_id = " + book.getId() + " AND genre = '" + genre + "';";
		UtilDB.changeDB(sql);
	}
	
	public static void editTitle(int bookID, String newTitle) {
		String sql = "UPDATE book SET title = '" + newTitle + "' WHERE book_id = " + bookID + ";";
		UtilDB.changeDB(sql);
	}
	
	public static void editPages(int bookID, int newPages) {
		String sql = "UPDATE book SET pages = " + newPages + " WHERE book_id = " + bookID + ";";
		UtilDB.changeDB(sql);
	}
	
	public static void editPublisher(int bookID, String publisherName) {
		Publisher publisher = PublisherDAO.getPublisher(publisherName);
		String sql = "UPDATE book SET publisher_id = " + publisher.getId() + " WHERE book_id = " + bookID + ";";
		UtilDB.changeDB(sql);
	}
}













