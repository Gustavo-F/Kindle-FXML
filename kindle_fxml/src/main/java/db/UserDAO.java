package db;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.gustavoferreira.kindle_fxml.AlertUtil;
import entities.Book;
import entities.User;

public class UserDAO {

	public void add(User user) {
		user.setId(PhysicalPersonDAO.add(user));
		
		String sql = "INSERT INTO users (user_id, login, user_password, access_level) VALUES (" + user.getId() + ""
				+ ", '" + user.getLogin() + "', '" + user.getPassword() + "', '" + user.getAccessLevel() + "');";
		UtilDB.changeDB(sql);
	}
	
	public static User getUserData(String userName) {
		String sql = "SELECT * FROM user_vw WHERE login = '" + userName + "';";
		ResultSet userData = UtilDB.getData(sql);
		
		User user = null;
		
		try {
			user = new User();
			user.setId(userData.getInt("user_id"));
			user.setName(userData.getString("name"));
			user.setSurname(userData.getString("surname"));
			user.setCpf(userData.getString("cpf"));
			user.setPhone(userData.getString("phone"));
			user.setEmail(userData.getString("email"));
			user.setLogin(userData.getString("login"));
			user.setPassword(userData.getString("user_password"));
			user.setAccessLevel(userData.getString("access_level"));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return user;
	}
	
	public static ArrayList<Book> getUserBooks(int userID){
		String sql = "SELECT * FROM user_books_vw WHERE user_id = " + userID + "";
		ResultSet rs = UtilDB.getData(sql);
		ArrayList<Book> books = new ArrayList<Book>();
		
		try {
			while(rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setPages(rs.getShort("pages"));
				book.setWriters(BookDAO.getBookWriters(book.getId()));
				book.setGenres(BookDAO.getBookGenres(book.getId()));
				book.setPublisher(PublisherDAO.getPublisher(rs.getInt("publisher_id")));
				books.add(book);
			}
		} catch (Exception e) {
			AlertUtil.error("Erro", "Erro ao carregar livros", "Erro ao carregar livros do usuário.", e);
		}
		
		return books;
	}
	
	public static void addBookToLibrary(Book book, User user) {
		String sql = "INSERT INTO book_user (book_id, user_id) VALUES (" + book.getId() + ", " + user.getId() + ")";
		UtilDB.changeDB(sql);
	}
}

















