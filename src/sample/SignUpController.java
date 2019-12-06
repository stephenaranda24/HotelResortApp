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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * @version 1.0
 * @ Romanov Andre
 * @ Shafi Mushfique
 * @ Stephen Aranda
 * @since 2019-09-21
 */
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
  private PasswordField TF_password;

  @FXML
  private PasswordField TF_cpassword;

  @FXML
  private TextField city;
  @FXML
  private Button button_back;

  @FXML
  private PasswordField pin;

  @FXML
  private PasswordField confirmPin;

  @FXML
  private ComboBox<String> countries;
  @FXML
  private TextField phoneNumber;
  boolean accountCreated = false;

  //Method for setting up the combo box from the enum for state list, and
  //country list to initialize the combo box
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

      }
    });
  }

  /**
   * initilialize
   */
  @Override
  public void initialize(URL url, ResourceBundle resources) {
    setComboBoxText();
    backButtonPressed();
    TF_name.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {

      }
    });
/**
 * Getting value from textfield and send it to the database
 */
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
              Main.infoMessage("Account sucessfully created.");
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

  /**
   * Back button  method
   */
  void backButtonPressed() {
    button_back.setOnMousePressed(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        MainScreenController msc = new MainScreenController();
        msc.loadScene(button_back, "MainScreenSample.fxml", "Main Screen");
      }
    });

  }

}

