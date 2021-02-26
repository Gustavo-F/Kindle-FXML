package br.com.gustavoferreira.kindle_fxml;

import db.UserDAO;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

	@FXML private Button btnRegister;
	@FXML private Button btnCancel;
	@FXML private TextField txtName;
	@FXML private TextField txtSurname;
	@FXML private TextField txtCPF;
	@FXML private TextField txtEmail;
	@FXML private TextField txtPhone;
	@FXML private TextField txtLogin;
	@FXML private PasswordField txtPassword;
	@FXML private PasswordField txtConfirmPassword;
	@FXML private CheckBox checkAccessLevel;
	
	@FXML
	private void register() {
		try {
			if(txtName.getText().isBlank()) {
				AlertUtil.alert("Nome em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtSurname.getText().isBlank()) {
				AlertUtil.alert("Sobrenome em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtCPF.getText().isBlank()) {
				AlertUtil.alert("CPF em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtPhone.getText().isBlank()) {
				AlertUtil.alert("Telefone em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtEmail.getText().isBlank()) {
				AlertUtil.alert("Email em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtLogin.getText().isBlank()) {
				AlertUtil.alert("Nome de Usuário em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtPassword.getText().isBlank()) {
				AlertUtil.alert("Senha em branco.", AlertType.INFORMATION);
				return;
			}
			
			if(txtConfirmPassword.getText().isBlank()) {
				AlertUtil.alert("Confirme sua senha.", AlertType.INFORMATION);
				return;
			}
			
			if(!txtConfirmPassword.getText().equals(txtPassword.getText())) {
				AlertUtil.alert("Digite a senha corretamente.", AlertType.INFORMATION);
				return;
			}
			
			User user = new User();
			user.setName(txtName.getText());
			user.setSurname(txtSurname.getText());
			user.setCpf(txtCPF.getText());
			user.setPhone(txtPhone.getText());
			user.setEmail(txtEmail.getText());
			user.setLogin(txtLogin.getText());
			user.setPassword(txtConfirmPassword.getText());
			if(checkAccessLevel.isSelected()) {
				user.setAccessLevel("0");
			}else {
				user.setAccessLevel("1");
			} 
			
			new UserDAO().add(user);
			AlertUtil.alert("Usuário cadastrado com sucesso!", AlertType.CONFIRMATION);
			LoginController.getRegisterStage().close();
			
		}catch(Exception e) {
			AlertUtil.error("Erro", "Cadastro de usuário", "Erro ao cadastrar usuário", e);
		}
	}
	
	@FXML 
	private void cancel() {
		LoginController.getRegisterStage().close();
	}
}
