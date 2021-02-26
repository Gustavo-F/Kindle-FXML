package br.com.gustavoferreira.kindle_fxml;

import db.UserDAO;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("static-access")
public class LoginController {

	@FXML private TextField txtLogin;
	@FXML private PasswordField txtPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnRegister;
	@FXML private Button btnExit;
	private static Stage registerStage;
	
	@FXML 
	private void login() {
		try {
			if(txtLogin.getText().isBlank()) {
				AlertUtil.alert("Login em branco", AlertType.INFORMATION);
				return;
			}
			
			if(txtPassword.getText().isBlank()) {
				AlertUtil.alert("Senha em branco", AlertType.INFORMATION);
				return;
			}
			
			User user = UserDAO.getUserData(txtLogin.getText());
			
			if(user.getLogin() == null) {
				AlertUtil.alert("Usuário inexistente", AlertType.INFORMATION);
				return;
			}
			
			if(!txtPassword.getText().equals(user.getPassword())) {
				AlertUtil.alert("Senha incorreta", AlertType.INFORMATION);
				return;
			}
			
			if(user.getAccessLevel().equals("0"))
				App.setRoot("main", null);
			else {
				App.setRoot("client_main", user);
			}

			App.changeResizable();
			App.setStageTitle("Kindle");
			
		}catch(Exception e){
			AlertUtil.error("Erro", "Erro de Login", "Erro ao fazer login", e);
		}
	}
	
	@FXML 
	private void register() {
		this.registerStage = new Stage();
		registerStage.setScene(FXMLUtil.loadScene("register"));
		registerStage.setResizable(false);
		registerStage.setTitle("Cadastrar");
		registerStage.show();
	}
	
	@SuppressWarnings("exports")
	public static Stage getRegisterStage() {
		return registerStage;
	}
	
	@FXML 
	private void exit() {
		App.close();
	}
}
