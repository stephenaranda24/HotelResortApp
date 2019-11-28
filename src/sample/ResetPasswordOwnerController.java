package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ResetPasswordOwnerController implements Initializable {
  @FXML
  private Label user_id;
  @FXML
  private Button back_button;

  @FXML
  private PasswordField passwordChange_Verify;

  @FXML
  private TextField passwordChange;

  @FXML
  private Button resetButton;
  public static String tempUserType;
  public static String tempUser;



  public void initialize(URL url, ResourceBundle resources) {
   tempUser = OwnerScreenController.userNameForChangePassword;

    tempUserType = OwnerScreenController.userTypeChangePassword;
    user_id.setText(tempUser);

  }

  @FXML
  void changePassword_Action(ActionEvent event) {
    try {
      DatabaseManager db = new DatabaseManager();
      MainScreenController msc = new MainScreenController();
      System.out.println();
      // retrieves sign-in fields
      String password = passwordChange.getText();
      String reTypePass = passwordChange_Verify.getText();

      if (password.equals(reTypePass)){
        db.passwordReset(user_id.getText(),tempUserType,password);
        msc.loadScene(resetButton,"OwnerScreen.fxml","Owner");

      }
      else {
        Main.errorMessage("Password doesnt match");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

}
