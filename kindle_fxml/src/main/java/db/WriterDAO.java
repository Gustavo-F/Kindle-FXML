package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Writer;

public class WriterDAO implements InterfaceDAO<Writer> {

	@Override
	public void add(Writer writer) {
		String sql = ("INSERT INTO writer (name, surname, email) VALUES ('" + writer.getName() + "', '"
				+ writer.getSurname() + "', '" + writer.getEmail() + "');");
		UtilDB.changeDB(sql);
	}

	@Override
	public void remove(Writer reference) {
		String sql = "DELETE FROM writer WHERE writer_id = " + reference.getId() + ";";
		UtilDB.changeDB(sql);
	}

	@Override
	public ArrayList<Writer> list() {
		ArrayList<Writer> writers = new ArrayList<Writer>();
		String sql = ("SELECT * FROM writer;");
		ResultSet rs = UtilDB.getData(sql);
		
		try {
			while(rs.next()) {
				Writer writer = new Writer();
				writer.setId(rs.getInt("writer_id"));
				writer.setName(rs.getString("name"));
				writer.setSurname(rs.getString("surname"));
				writer.setEmail(rs.getString("email"));
				
				writers.add(writer);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao obter dados de escritores.");
		}
		
		return writers;
	}

	public static void editName(String newName, int id ) {
		String sql = "UPDATE writer SET name = '" + newName + "' WHERE writer_id = " + id +";";
		UtilDB.changeDB(sql);
	}

	public static void editSurname(String newSurname, int id) {
		String sql = "UPDATE writer SET surname = '" + newSurname + "' WHERE writer_id = " + id +";";
		UtilDB.changeDB(sql);
	}
	
	public static void editEmail(String newEmail, int id) {
		String sql = "UPDATE writer SET email = '" + newEmail + "' WHERE writer_id = " + id +";";
		UtilDB.changeDB(sql);
	}
	
}