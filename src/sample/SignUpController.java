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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class SignUpController extends MainScreenController implements Initializable {

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
	@FXML
	private TextArea idSpace;
	@FXML
	private TextField fullName;


	boolean accountCreated = false;
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
				MainScreenController msc = new MainScreenController();
				try {

					String userName = TF_name.getText();
					String email = TF_email.getText();
					String password = TF_password.getText();
					String cpassword = TF_cpassword.getText();
					String fullNameText = fullName.getText();
					System.out.println("(Create Pressed)");
					if (password.equals(cpassword)) {
						boolean fieldsCompleted = !userName.equals("") && !email.equals("") //
								&& !password.equals("") && !cpassword.equals("") &&!fullNameText.equals("");
						if (fieldsCompleted) {
							System.out.println("UserName: " + userName);
							System.out.println("Email: " + email);
							System.out.println("Password: " + password);
							System.out.println("C Password: " + cpassword);
							System.out.println("Full Name: "+ fullNameText);
							/* do database stuff here*/
							System.out.println("Account sucessfully created.");
							accountCreated = true;
						} else {
							System.out.println("Please Enter the information");
						}
						DatabaseManager db = new DatabaseManager();
						boolean nameExist = db.AddCustomer(userName, fullNameText, password, email);
						if (nameExist==true){
							db.createCustomerTable(userName);

						}
						else{
							System.out.println("Name does exist. Please use another name");
						}


					}
					else{
						System.out.println("Password doesnt match");
					}
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
				if (!accountCreated){
					System.out.println("Please type Again");
				}
				else {
					msc.loadScene(button_create,"MainScreenSample.fxml","Main Screen--Login Please");
					accountCreated = false;
				}
			}
		});
	}

}
