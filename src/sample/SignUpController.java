package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    countries.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        if (!countries.getValue().equals("USA")) {
          state.setDisable(true);
        } else {
          state.setDisable(false);
        }
        System.out.println(countries.getValue());

      }
    });
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
              Main.infoMessage("Please complete the required fields");
            }
            DatabaseManager db = new DatabaseManager();
            int pintoVerifyInInt = Integer.parseInt(pintoVerify);
            int zipCodes = Integer.parseInt(zipcode);

            boolean nameDoesnotExist = db.AddCustomer(fullNameText, userName, email, phonenumber,
                password, pintoVerifyInInt, streetAddress, cityAddress, stateName, zipCodes,
                countryName);
            if (nameDoesnotExist == true) {
              db.createCustomerTable(userName);
              db.saveEmailCardTable(userName, email);


            } else {
              Main.errorMessage("Username is unavailable");
              accountCreated = false;
            }


          } else {
            Main.errorMessage("Password or PIN does not match");
            accountCreated = false;
          }
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
        if (!accountCreated) {
          Main.infoMessage("Please complete all the required fields");

        } else {
          msc.loadScene(button_create, "MainScreenSample.fxml", "Main Screen--Login Please");
          accountCreated = false;
        }
      }

    });

  }

}

