package db;

import entities.JuridicalPerson;

public class JuridicalPersonDAO {

	
	public static int addJuridicalPerson(JuridicalPerson reference) {
		new PersonDAO().addPerson(reference);
		reference.setId(PersonDAO.getPersonID(reference.getEmail()));
		
		String sql = "INSERT INTO juridical_person (person_id, name, cnpj) VALUES"
				+ "(" + reference.getId() + ", '" + reference.getName() + "', '" + reference.getCnpj() + "')";
		UtilDB.changeDB(sql);
		
		return reference.getId();
	}
	
	public static void removeJuridicalPerson(JuridicalPerson reference) {
		String sql = "DELETE FROM juridical_person WHERE person_id = " + reference.getId() + ";";
		UtilDB.changeDB(sql);
		PersonDAO.removePerson(reference);
	}
}