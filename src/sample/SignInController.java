package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private Button button_back;
	@FXML
	private TextArea idSpace;

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		setComboBoxText();
		loginButtonPressed();
		forgotButtonPressed();
		backButtonPressed();
		TF_username.setText("alpha@bravo.com");
		PF_password.setText("hotel");
		CB_type.setValue("Owner");
		
	}

	private void setComboBoxText() {
		CB_type.setPromptText("Select a role.");
		CB_type.getItems().addAll("Owner", "Customer", "Desk_Assistant", "Custodian");
	}

	private void loginButtonPressed() {
		button_login.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				try {
					DatabaseManager db = new DatabaseManager();
					MainScreenController msc = new MainScreenController();
					// retrieves sign-in fields
					String username = TF_username.getText();
					String password = PF_password.getText();
					String type = CB_type.getValue();
					boolean fieldsCompleted = !username.equals("") && !password.equals("") //
							&& !type.equals(null);
					// checks that required fields are not empty
					if (fieldsCompleted) {
						System.out.println("Username: " + username);
						System.out.println("Password: " + password);
						System.out.println("Type: " + type);
						db.startDatabase(username, password, type);
						boolean verified = db.LogInAccount(username, password, type);
						// "Owner", "Customer", "Desk_Assistant", "Custodian"

						if (verified == true && type == "Customer") {
							Main.loggedInUser = username;
							Main.Type = "ClientScreen";
							msc.loadScene(button_login, "ClientScreen.fxml", "Main cScreen");
						} else if (verified == true && type == "Owner") {
							Main.loggedInUser = username;
							msc.loadScene(button_login, "OwnerScreen.fxml", "Main cScreen");

						} else if (verified == true && type == "Custodian") {
							Main.loggedInUser = username;

							msc.loadScene(button_login, "CustodianScreen.fxml", "Main cScreen");

						} else if (verified == true && type == "Desk_Assistant") {
							Main.loggedInUser = username;
							msc.loadScene(button_login, "DeskAssistantScreen.fxml", "Main cScreen");

						} else {
							Main.errorMessage("Password or Username is incorrect");
						}

					} else {
						Main.infoMessage("Please be sure that all required fields are completed");
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

	void backButtonPressed() {

		button_back.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("(Back Pressed)");

				MainScreenController msc = new MainScreenController();
				msc.loadScene(button_back, "MainScreenSample.fxml", "Main Screen");
			}
		});

	}

}
