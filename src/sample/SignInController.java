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

/**
 * @version 1.0
 * @ Romanov Andre
 * @ Shafi Mushfique
 * @ Stephen Aranda
 * @since 2019-09-21
 */
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

  /**
   * {@inheritDoc} initialize the scene with the value in combobox
   */
  @Override
  public void initialize(URL url, ResourceBundle resources) {
    setComboBoxText();
    loginButtonPressed();
    forgotButtonPressed();
    backButtonPressed();
    TF_username.setText("jared12"); //alpha@bravo.com
    PF_password.setText("4");//hotel


  }

  /**
   * items adding in combo box Owner as Admin Customer as Guest Front_Desk As Desk_Assistant
   * HouseKeeping as Custodian
   */
  private void setComboBoxText() {
    CB_type.setPromptText("Select a role.");
    CB_type.getItems().addAll("Admin", "Guest", "Front Desk", "House Keeping");
  }

  /**
   * Action for login button It get the value from the text field and check if the values are empty?
   * If the values are not empty then it go to database table for the right user type and look if
   * the name exist or not. It is done thorugh the method LogInAccount( String email, String
   * password ,String role) from the Database Manager If it exist then it check for the password
   * from the table's password column, if it validates true then it will open correct type of users
   * screen
   */
  private void loginButtonPressed() {
    button_login.setOnMousePressed(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        try {
          DatabaseManager db = new DatabaseManager();
          MainScreenController msc = new MainScreenController();
          // retrieves sign-in fields
          String username = TF_username.getText();
          String password = PF_password.getText();
          String role = CB_type.getValue();
          String type;

          switch (role.toLowerCase()) {
            case "admin":
              type = "Owner";
              break;
            case "guest":
              type = "Customer";
              break;
            case "front desk":
              type = "Desk_Assistant";
              break;
            case "house keeping":
              type = "CUSTODIAN";
              break;
            default:
              type = "something ";
              break;
          }
          // checks that required fields are not empty
          boolean fieldsCompleted = !username.equals("") && !password.equals("") //
              && !type.equals(null);
          if (fieldsCompleted) {
            db.startDatabase(username, password, type);
            boolean verified = db.LogInAccount(username, password, type);
            if (verified == true && type == "Customer") {
              Main.loggedInUser = username;
              Main.Type = "ClientScreen";
              msc.loadScene(button_login, "ClientScreen.fxml", "Client Screen");
            } else if (verified == true && type == "Owner") {
              System.out.println("Owneerr");
              Main.loggedInUser = username;
              Main.Type = "OwnerScreen";
              msc.loadScene(button_login, "OwnerScreen.fxml", "Owners Screen");

            } else if (verified == true && type == "CUSTODIAN") {
              Main.loggedInUser = username;

              msc.loadScene(button_login, "CustodianScreen.fxml", "Custodian Screen");

            } else if (verified == true && type == "Desk_Assistant") {
              Main.loggedInUser = username;
              msc.loadScene(button_login, "DeskAssistantScreen.fxml", "Desk Assistant Screen");
              Main.Type = "DeskAssistantScreen";

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

  /**
   * Forget Password Screen Message
   */

  private void forgotButtonPressed() {
    button_forgot.setOnMousePressed(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        Main.infoMessage("Please call at 1-800-000-0000 and provide your pin to reset the Password"
            + "\nWe strongly suggest you to change your pin and password once you get the temporary password for re-login");
        // go to forgot username screen

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
