package entities;

public class User extends PhysicalPerson {

	private String login;
	private String password;
	private String accessLevel;
	private Library library;
	
	public User() {
	}

	public User(int id, String email, String phone, String name, String surname, String cpf) {
		super(id, email, phone, name, surname, cpf);
		library = new Library();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	public Library getLibrary() {
		return library;
	}
}
