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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController implements Initializable {

	private Stage stage;
	@FXML
	private TextField TF_name;
	@FXML
	private TextField TF_email;
	@FXML
	private TextField TF_password;
	@FXML
	private TextField TF_cpassword;
	@FXML
	private Button button_create;

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		TF_name.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				System.out.println("Name: ");
			}

		});
		button_create.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				String fullname = TF_name.getText();
				String email = TF_email.getText();
				String password = TF_password.getText();
				String cpassword = TF_cpassword.getText();
				System.out.println("(Create Pressed)");
				boolean fieldsCompleted = !fullname.equals("") && !email.equals("") //
						&& !password.equals("") && !cpassword.equals("");
				if (fieldsCompleted) {
					System.out.println("Name: " + fullname);
					System.out.println("Email: " + email);
					System.out.println("Password: " + password);
					System.out.println("C Password: " + cpassword);
					
					/* do database stuff here*/
					
					System.out.println("Account sucessfully created.");
					
					/* Go to log-in screen */
					try {
						// retrieves and closes current stage
						stage = (Stage) button_create.getScene().getWindow();
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
			}
		});
	}

}
