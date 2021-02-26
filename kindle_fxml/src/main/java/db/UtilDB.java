package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.gustavoferreira.kindle_fxml.AlertUtil;
import javafx.scene.control.Alert;

public class UtilDB {

	private static Connection connection;
	
	private static Connection openConnection() {
		Alert alert = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:kindle.sqlite");
		}catch(SQLException e) {
			alert = AlertUtil.error("Erro", "Erro no Banco de Dados", "Erro ao abrir conexão com o banco de dados.", e);
			alert.showAndWait(); 
		} catch (ClassNotFoundException e) {
			alert = AlertUtil.error("Erro", "Erro no Banco de Dados", "Biblioteca SQLite não está funcionando corretamente.", e);
			alert.showAndWait();
		}
		
		return connection;
	}
	
	public static Connection getConnection() {
		try {
			if(connection == null)
				openConnection();
		
			if(connection.isClosed())
				openConnection();
			
		}catch(SQLException e) {
			System.err.println("Erro ao obter conexão.");
		}
	
		return connection;
	}
	
	public static void closeConnection() {
		if(connection == null)
			return;
		
		try {
			if(!connection.isClosed())
				connection.close();
			
		}catch(SQLException e) {
			System.err.println("Erro ao fechar conexão.");
		}
	}

	public static void changeDB(String sql) {
		System.out.println("SLQ: " + sql);
		
		Connection connection = getConnection();
		
		try {
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql);
			stm.close();
		} catch (Exception e) {
			AlertUtil.error("Erro", "SQL", "Erro ao excutar SQL.", e);
		}
	}
	
	public static ResultSet getData(String sql) {
		ResultSet rs = null;
		
		try {
			PreparedStatement pstm = getConnection().prepareStatement(sql);
			rs = pstm.executeQuery();
		} catch (Exception e) {
			AlertUtil.error("Erro", "SQL", "Erro ao buscar dados do banco.", e);
		}
		
		return rs;
	}
	
}