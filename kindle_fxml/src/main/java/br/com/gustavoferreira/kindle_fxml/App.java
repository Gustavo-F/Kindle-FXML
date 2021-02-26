package br.com.gustavoferreira.kindle_fxml;

import entities.User;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */

@SuppressWarnings({ "static-access", "exports" })
public class App extends Application {
    private static Stage stage;

	@Override
    public void start(Stage stage){
    	this.stage = new Stage();
    	this.stage.setTitle("Login");
    	this.stage.setScene(FXMLUtil.loadScene("login"));
    	App.changeResizable();
    	this.stage.show();
    }

    public static void close() {
    	stage.close();
    }
    
    public static void setRoot(String fxml, User user){
    	stage.setUserData(user);
    	stage.setScene(FXMLUtil.loadScene(fxml));
    	stage.show();
    }
    
    public static Object getUserData() {
    	return stage.getUserData();
    }
    
    public static void changeResizable() {
    	if(stage.isResizable()) {
    		stage.setResizable(false);
    	}else {
    		stage.setResizable(true);
    	}
    }
    
    public static void setStageTitle(String title) {
    	stage.setTitle(title);
    }
    
    public static void main(String[] args) {   	
    	launch();
    }
}













