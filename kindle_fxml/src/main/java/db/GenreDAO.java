package db;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.gustavoferreira.kindle_fxml.AlertUtil;
import entities.Genre;
import javafx.scene.control.Alert;

public class GenreDAO implements InterfaceDAO<Genre> {

	@Override
	public void add(Genre reference) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO genre VALUES ('" + reference.getGenre() + "')";
		UtilDB.changeDB(sql);
	}

	@Override
	public void remove(Genre reference) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM genre WHERE genre = '" + reference.getGenre() + "'";
		UtilDB.changeDB(sql);
	}

	@Override
	public ArrayList<Genre> list() {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		String sql = "SELECT * FROM genre";
		ResultSet rs = UtilDB.getData(sql);

		try {
			while (rs.next()) {
				Genre genre = new Genre(rs.getString("genre"));
				genres.add(genre);
			}
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Erro", "Erro no Banco de Dados",
					"Erro ao carregar gêneros do banco de dados", e);
			alert.showAndWait();
		}

		return genres;
	}

	public static void edit(Genre oldGenre, Genre newGenre) {
		String sql = "UPDATE genre SET genre = '" + newGenre.getGenre() + "' WHERE genre = '" + 
				oldGenre.getGenre() + "';";
		UtilDB.changeDB(sql);
	}
}
