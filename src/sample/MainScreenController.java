package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {

	Stage stage;
	@FXML
	Button button_signup;
	@FXML
	Button button_login;

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		button_signup.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("(Sign-Up Pressed)");
				/* Go to sign up page */
				try {
					// retrieves and closes current stage
					stage = (Stage) button_login.getScene().getWindow();
					stage.close();

					// loads main screen stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUpSample.fxml"));
					Parent profile = (Parent) fxmlLoader.load();

					// creates a new stage
					Stage newStage = new Stage();
					newStage.setTitle("Sign-Up");
					newStage.setScene(new Scene(profile));

					// set new stage to current stage and display stage
					stage = newStage;
					stage.show();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		button_login.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				/* Go to login page */
				try {
					// retrieves and closes current stage
					stage = (Stage) button_login.getScene().getWindow();
					stage.close();

					// loads main screen stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignInSample.fxml"));
					Parent profile = (Parent) fxmlLoader.load();

					// creates a new stage
					Stage newStage = new Stage();
					newStage.setTitle("Login");
					newStage.setScene(new Scene(profile));

					// set new stage to current stage and display stage
					stage = newStage;
					stage.show();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

}
