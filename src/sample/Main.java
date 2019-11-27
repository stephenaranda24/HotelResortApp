package sample;

import java.time.LocalDate;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.h2.engine.Database;

public class Main extends Application {

	// null = no user logged in yet
	// static since only one user can be
	// logged-in at a time
	public static String loggedInUser = null;
	public static String Type = null;

	@Override
	public void start(Stage primaryStage) throws Exception {


		System.out.println(LocalDate.now());
		LocalDate todaysDate = LocalDate.now();
		String datePushed = String.valueOf(todaysDate);
		DatabaseManager db = new DatabaseManager();
		db.custodianDateValidation(datePushed);

		Parent root = FXMLLoader.load(getClass().getResource("MainScreenSample.fxml"));
		primaryStage.setTitle("Main Menu");
		primaryStage.setScene(new Scene(root, 900, 400));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void errorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void infoMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void logOutUser(Button pressedButton) {
		loggedInUser = null; // remove logged in user
		MainScreenController msc = new MainScreenController();
		msc.loadScene(pressedButton, "MainScreenSample.fxml", "Main cScreen");

	}

}
