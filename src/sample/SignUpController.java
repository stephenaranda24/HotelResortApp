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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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


public class SignUpController extends MainScreenController implements Initializable {

	private Stage stage;
	@FXML
	private Button button_create;

	@FXML
	private TextField street;

	@FXML
	private ComboBox<String> state;

	@FXML
	private TextField zipCode;

	@FXML
	private TextField fullName;

	@FXML
	private TextField TF_name;

	@FXML
	private TextField TF_email;

	@FXML
	private TextField TF_password;

	@FXML
	private TextField TF_cpassword;

	@FXML
	private TextField city;

	@FXML
	private TextField pin;

	@FXML
	private TextField confirmPin;

	@FXML
	private ComboBox<String> countries;
	@FXML
	private TextField phoneNumber;


	boolean accountCreated = false;

	public void setComboBoxText() {
  	countries.setPromptText("Select a country.");
		for (int i = 0; i < Country.values().length; i++) {
			countries.getItems().add(Country.values()[i].name());
		}
		state.setPromptText("Select a State.");
		for (int i = 0; i < State.values().length; i++) {
			state.getItems().add(State.values()[i].name());
		}
  }
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		setComboBoxText();
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
						String streetAddress = street.getText();
						String pintoVerify = pin.getText();
						String pintoVerifyC = confirmPin.getText();
						String cityAddress = city.getText();
						String zipcode = zipCode.getText();
						String stateName = state.getValue();
						String countryName = countries.getValue();
						String phonenumber = phoneNumber.getText();


						System.out.println("(Create Pressed)");
						if (password.equals(cpassword) && pintoVerify.equals(pintoVerifyC)) {
							boolean fieldsCompleted = !userName.equals("") && !email.equals("") //
									&& !password.equals("") && !cpassword.equals("")
									&& !fullNameText.equals("") && !pintoVerify.equals("") && !pintoVerifyC.equals("");
							if (fieldsCompleted) {

								System.out.println("UserName: " + userName);
								System.out.println("Email: " + email);
								System.out.println("Password: " + password);
								System.out.println("C Password: " + cpassword);
								System.out.println("Full Name: " + fullNameText);
								/* do database stuff here*/
								System.out.println("Account sucessfully created.");
								accountCreated = true;
							} else {
								System.out.println("Please Enter the information");
							}
							DatabaseManager db = new DatabaseManager();
							int pintoVerifyInInt = Integer.parseInt(pintoVerify);
							int zipCodes = Integer.parseInt(zipcode);

							boolean nameDoesnotExist = db.AddCustomer(fullNameText,  userName,  email,  phonenumber,
									password ,  pintoVerifyInInt,  streetAddress, cityAddress,  stateName,  zipCodes ,countryName );
							if (nameDoesnotExist == true) {
								db.createCustomerTable(userName);
								db.saveEmailCardTable(userName,email);


							} else {
								System.out.println("Name does exist. Please use another name");
								accountCreated = false;
							}


						} else {
							System.out.println("Password/PIN doesnt match");
							accountCreated = false;
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					if (!accountCreated) {
						System.out.println("Please type Again");

					} else {
						msc.loadScene(button_create, "MainScreenSample.fxml", "Main Screen--Login Please");
						accountCreated = false;
					}
				}

			});

		}

	}

