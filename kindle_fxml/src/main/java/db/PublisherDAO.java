package db;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.gustavoferreira.kindle_fxml.AlertUtil;
import entities.Publisher;

public class PublisherDAO implements InterfaceDAO<Publisher> {

	@Override
	public void add(Publisher reference) {
		reference.setId(JuridicalPersonDAO.addJuridicalPerson(reference));

		String sql = "INSERT INTO publisher VALUES (" + reference.getId() + ")";
		UtilDB.changeDB(sql);
	}

	@Override
	public void remove(Publisher reference) {
		String sql = "DELETE FROM publisher WHERE publisher_id = " + reference.getId() + ";";
		UtilDB.changeDB(sql);
		JuridicalPersonDAO.removeJuridicalPerson(reference);
	}

	@Override
	public ArrayList<Publisher> list() {
		String sql = "SELECT * FROM publisher_vw;";
		ResultSet rs = UtilDB.getData(sql);
		ArrayList<Publisher> publishers = new ArrayList<>();
		
		try {
			while(rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt("publisher_id"));
				publisher.setName(rs.getString("name"));
				publisher.setCnpj(rs.getString("cnpj"));
				publisher.setEmail(rs.getString("email"));
				publisher.setPhone(rs.getString("phone"));
				
				publishers.add(publisher);
			}
		} catch (Exception e) {
			AlertUtil.error("Erro", "Erro ao carregar dados", "Erro ao listar dados das editoras", e);
		}
		
		return publishers;
	}

	public static Publisher getPublisher(int publisherID){
		String sql = "SELECT * FROM publisher_vw WHERE publisher_id = " + publisherID + ";";
		ResultSet rs = UtilDB.getData(sql); 
		Publisher publisher = null;
		
		try {
			publisher = new Publisher();
			
			publisher.setId(rs.getInt("publisher_id"));
			publisher.setName(rs.getString("name"));
			publisher.setCnpj(rs.getString("cnpj"));
			publisher.setEmail(rs.getString("email"));
			publisher.setPhone(rs.getString("phone"));
			
		} catch (Exception e) {
			AlertUtil.error("Erro", "Erro ao carregar dados", "Erro o carregar dados de editora", e);
		}
		
		return publisher;
	}
	
	public static Publisher getPublisher(String publisherName){
		String sql = "SELECT * FROM publisher_vw WHERE name = '" + publisherName + "';";
		ResultSet rs = UtilDB.getData(sql); 
		Publisher publisher = null;
		
		try {
			publisher = new Publisher();
			
			publisher.setId(rs.getInt("publisher_id"));
			publisher.setName(rs.getString("name"));
			publisher.setCnpj(rs.getString("cnpj"));
			publisher.setEmail(rs.getString("email"));
			publisher.setPhone(rs.getString("phone"));
			
		} catch (Exception e) {
			AlertUtil.error("Erro", "Erro ao carregar dados", "Erro o carregar dados de editora", e);
		}
		
		return publisher;
	}
	
	public static void editName(String newName, int id) {
		String sql = "UPDATE juridical_person SET name = '" + newName + "' WHERE person_id = " + id +";";
		UtilDB.changeDB(sql);
	}

	public static void editCNPJ(String newcnpj, int id) {
		String sql = "UPDATE juridical_person SET cnpj = '" + newcnpj + "' WHERE person_id = " + id +";";
		UtilDB.changeDB(sql);
	}
	
	public static void editEmail(String newEmail, int id) {
		String sql = "UPDATE person SET email = '" + newEmail + "' WHERE person_id = " + id +";";
		UtilDB.changeDB(sql);
	}
	
	public static void editPhone(String newPhone, int id) {
		String sql = "UPDATE person SET phone = '" + newPhone + "' WHERE person_id = " + id +";";
		UtilDB.changeDB(sql);
	}
}















