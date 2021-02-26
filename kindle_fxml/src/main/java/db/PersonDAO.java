package db;

import java.sql.ResultSet;

import entities.Person;

public class PersonDAO {

	public void addPerson(Person person) {
		String sql = "INSERT INTO person (email, phone) VALUES"
				+ "('" + person.getEmail() + "', '" + person.getPhone() + "');";
		UtilDB.changeDB(sql);
	}
	
	public static int getPersonID(String email) {
		String sql = "SELECT person_id FROM person WHERE email = '" + email +"';";
		ResultSet rs = UtilDB.getData(sql);
		int id = 0;
		
		try {
			id = rs.getInt("person_id");
		} catch (Exception e) {
			System.err.println("Erro ao obter ID.");
		}
		
		return id;
	}
	
	public static void removePerson(Person person) {
		String sql = "DELETE FROM person WHERE person_id = " + person.getId() + ";";
		UtilDB.changeDB(sql);
	}
}
