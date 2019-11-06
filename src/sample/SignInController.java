package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignInController implements Initializable {

	Stage stage;
	@FXML
	private TextField TF_username;
	@FXML
	private PasswordField PF_password;
	@FXML
	private ComboBox<String> CB_type;
	@FXML
	private Button button_login;
	@FXML
	private Button button_forgot;
	@FXML
	private TextArea idSpace;


	@Override
	public void initialize(URL url, ResourceBundle resources) {
		setComboBoxText();
		loginButtonPressed();
		forgotButtonPressed();
	}

	private void setComboBoxText() {
		CB_type.setPromptText("Select a role.");
		CB_type.getItems().addAll("Owner", "Customer", "Desk_Assistant", "Custodian");
	}

	public static String username;
	private void loginButtonPressed() {
		button_login.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				try {
					DatabaseManager db = new DatabaseManager();
					MainScreenController msc = new MainScreenController();
					// retrieves sign-in fields
					username = TF_username.getText();
					String password = PF_password.getText();
					String type = CB_type.getValue();
					System.out.println("(Login Pressed)");
					boolean fieldsCompleted = !username.equals("") && !password.equals("") //
							&& !type.equals(null);
					// checks that required fields are not empty
					if (fieldsCompleted) {
						System.out.println("Username: " + username);
						System.out.println("Password: " + password);
						System.out.println("Type: " + type);
						db.startDatabase(username,password,type);
						boolean verified = db.LogInAccount(username,password,type);
						// Client client = new Client(username, password...)

						if (verified == true) {
							msc.loadScene(button_login, "ClientScreen.fxml", "Main cScreen");

						}
						else{
							System.out.println("wrong password");
						}



					} else {
						System.out.println(
								"Please be sure that all required fields (username, password, type)" + " are completed.");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}


			}
		});
	}

	private void forgotButtonPressed() {
		button_forgot.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("(Forgot Pressed)");
				// go to forgot username screen
			}

		});
	}

}
