package sample;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	// null = no user logged in yet
	// static since only one user can be 
	// logged-in at a time
	public static String loggedInUser = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainScreenSample.fxml"));
		primaryStage.setTitle("Main Menu");
		primaryStage.setScene(new Scene(root, 900, 400));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);

	}
	
	public static void errorMessage(String message) {
		JOptionPane.showMessageDialog(null,
      		  message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void infoMessage(String message) {
		JOptionPane.showMessageDialog(null,
      		  message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

}
