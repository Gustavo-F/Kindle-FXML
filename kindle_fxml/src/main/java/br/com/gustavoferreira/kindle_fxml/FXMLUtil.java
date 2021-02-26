package br.com.gustavoferreira.kindle_fxml;

import java.io.IOException;

import entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class FXMLUtil {

	private static FXMLLoader load;
	
	@SuppressWarnings("exports")
	public static Scene loadScene(String fxml) {
		
		try {
			load = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
			Scene scene = new Scene(load.load());
			return scene;
		} catch (IOException eIO) {
			AlertUtil.error("Erro", "Erro ao carregar componente", "Erro ao tentar carregar a janela: " + fxml + ".fxml", eIO);
			return null;
		}catch(IllegalStateException eIllegalState){
			Alert alert = AlertUtil.error("Erro", "Erro - Arquivo inexistente!",
					"Erro ao tentar carregar a janela: " + fxml + ".fxml", eIllegalState);
			alert.showAndWait();
			return null;
		}
	}
	
	@SuppressWarnings("exports")
	public static Parent loadScene(String fxml, User user) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
			Parent root = (Parent) fxmlLoader.load();
			
			if(fxmlLoader.getController().getClass() == new ClientMainController().getClass()) {
				ClientMainController controller = new ClientMainController();
				controller.setUser(user);
				System.out.println("ClientMainController -> User: " + fxmlLoader);
			}
			
			return root;
		}catch(IOException eIO) {
			AlertUtil.error("Erro", "Erro ao carregar componente", "Erro ao tentar carregar a janela: " + fxml + ".fxml", eIO);
			return null;
		}catch(IllegalStateException eIllegalState){
			Alert alert = AlertUtil.error("Erro", "Erro - Arquivo inexistente!",
					"Erro ao tentar carregar a janela: " + fxml + ".fxml", eIllegalState);
			alert.showAndWait();
			return null;
		}
	}

	public static void getController() {
		System.out.println(load.getController().getClass());
	}
}
