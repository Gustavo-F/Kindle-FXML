package entities;

public abstract class Person {

	private int id;
	private String email;
	private String phone;

	public Person() {
	}

	public Person(int id, String email, String phone) {
		super();
		this.id = id;
		this.email = email;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}