package db;

import entities.PhysicalPerson;

public class PhysicalPersonDAO {

	public static int add(PhysicalPerson person) {
		new PersonDAO().addPerson(person);
		person.setId(PersonDAO.getPersonID(person.getEmail()));
		
		String sql = "INSERT INTO physical_person VALUES (" + person.getId() + ", '" + person.getName() + "'"
				+ ", '" + person.getSurname() + "', '" + person.getCpf() + "');";
		UtilDB.changeDB(sql);
		
		return person.getId();
	}
	
}
