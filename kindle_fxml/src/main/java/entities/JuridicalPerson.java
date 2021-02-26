package entities;

public abstract class JuridicalPerson extends Person {

	private String name;
	private String cnpj;
	
	public JuridicalPerson() {
	}
	
	public JuridicalPerson(int id, String email, String phone, String name, String cnpj) {
		super(id, email, phone);
		this.name = name;
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
}
