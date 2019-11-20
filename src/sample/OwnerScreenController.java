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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class OwnerScreenController implements Initializable {

  @FXML
  private Button submit;

  @FXML
  private Label idSpace1;

  @FXML
  private Button logout;

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
  private TextField pin;

  @FXML
  private TextField confirmPin;

  @FXML
  private ComboBox<String> CB_type;

  @FXML
  private Button button_create;




  boolean accountCreated = false;

  public void setComboBoxText() {

    CB_type.setPromptText("Select a role.");
    CB_type.getItems().addAll( "Desk_Assistant", "Custodian");
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
          String type = CB_type.getValue();

          String pintoVerify = pin.getText();
          String pintoVerifyC = confirmPin.getText();


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


            db.addByOwner(type,  email, userName,
                password ,  pintoVerifyInInt);






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
