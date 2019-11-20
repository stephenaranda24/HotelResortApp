//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientScreenController implements Initializable {
  @FXML
  private TextField startDate;
  @FXML
  private TextField endDate;
  @FXML
  private TextField startMonth;
  @FXML
  private TextField endMonth;
  @FXML
  private Button submit;
  @FXML
  private TextArea idSpace;
  @FXML
  private Label idSpace1;
  @FXML
  private Label idSpace11;
  @FXML
  private ComboBox<String> roomSelectCombo;
  @FXML
  private Button tempButton;
  @FXML
  private Label tempLabel;
  @FXML
  private Button updatePassword;
  @FXML
  private PasswordField newPin;
  @FXML
  private PasswordField newPinVerify;
  @FXML
  private PasswordField oldPin;
  @FXML
  private PasswordField oldPassword;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField newPasswordVerify;
  @FXML
  private Button updatePin;
  public static String userId;
  public static int orderNo;
  public static String username;

  public ClientScreenController() {
  }

  @FXML
  void tempButton(ActionEvent event) {
    MainScreenController msc = new MainScreenController();
    orderNo = Integer.parseInt(this.tempLabel.getText());
    System.out.println(orderNo + "if it works");
    msc.loadScene(this.submit, "PaymentScreen.fxml", "Payment Screen");
  }

  @FXML
  void updatePasswordAction(ActionEvent event) throws SQLException {
    boolean passwordMatched = false;
    String userId = this.idSpace11.getText();
    String oPassword = this.oldPassword.getText();
    String password = this.newPassword.getText();
    String cPassword = this.newPasswordVerify.getText();
    DatabaseManager dm = new DatabaseManager();
    if (password.equals(cPassword)) {
      dm.verifyPasswordorPin("Customer", userId, oPassword, "password");
      passwordMatched = true;
      if (true) {
        dm.changePinOrPass("Customer", userId, "password", password);
        System.out.println("password Changed");
      } else {
        passwordMatched = false;
      }
    } else {
      Main.errorMessage("Password does not match");
    }

  }

  @FXML
  void updatePin(ActionEvent event) throws SQLException {
    boolean pinMatched = false;
    String userId = this.idSpace11.getText();
    String oPin = this.oldPin.getText();
    String pin = this.newPin.getText();
    String cPin = this.newPinVerify.getText();
    DatabaseManager dm = new DatabaseManager();
    if (pin.equals(cPin)) {
      dm.verifyPasswordorPin("Customer", userId, oPin, "verifypin");
      pinMatched = true;
      if (true) {
        dm.changePinOrPass("Customer", userId, "verifypin", pin);
        System.out.println("pin Changed");
      } else {
        pinMatched = false;
      }
    } else {
      Main.errorMessage("Pin does not match");
    }

  }

  public void initialize(URL url, ResourceBundle resources) {
    try {
      DatabaseManager db = new DatabaseManager();
      userId = db.getTheName(Main.loggedInUser);
    } catch (SQLException var4) {
      var4.printStackTrace();
    }

    this.idSpace1.setText(userId);
    this.idSpace11.setText(userId);
    this.setRoomSelectCombo();
  }

  private void setRoomSelectCombo() {
    DateAndCostManager dm = new DateAndCostManager();
    dm.setRoomSelectCombo(this.roomSelectCombo);
  }

  @FXML
  void submitDate(ActionEvent event) throws SQLException {
    MainScreenController msc = new MainScreenController();
    String gettId = this.idSpace1.getText();
    username = gettId;
    System.out.println("I got the id as " + gettId);
    DateAndCostManager dc = new DateAndCostManager();
    List newList = dc.dateCalc(this.roomSelectCombo, this.startDate, this.startMonth, this.endDate, this.endMonth);
    DatabaseManager db = new DatabaseManager();
    ArrayList<String> dateList = (ArrayList)newList.get(4);
    System.out.println(dateList + "LOOKINGOUT");
    double cost = (Double)newList.get(3);
    System.out.println(dateList + "LOOKINGOUT" + cost);
    String dateToDisplay = (String)newList.get(5);
    System.out.println("The booking was between " + dateToDisplay);
    String result = db.pushDateToRoom((String)this.roomSelectCombo.getValue(), gettId, dateList);
    if (result != null) {
      System.out.println(result + " Looking for result");
      db.pushDate(gettId, result, dateList, cost, dateToDisplay);
      orderNo = db.orderNumber();
      System.out.println("order no is " + orderNo);
    } else {
      Main.infoMessage("Sorry the rooms selected are not available for those dates");
    }

    System.out.println(dateList);
    msc.loadScene(this.submit, "PaymentScreen.fxml", "Payment Screen");
  }
}
