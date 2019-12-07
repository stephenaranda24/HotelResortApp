package sample;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * There are three tabs in this class's associate screen
 * In one tab Owner can see all the room status, checkin status, payment status.
 * another for creating new user
 * and one for restting the password
 * @version 1.0
 * @author Romanov Andre
 * @author Shafi Mushfique
 * @author Stephen Aranda
 * @since 2019-09-21
 */
public class OwnerScreenController implements Initializable {

  public static String userTypeChangePassword;
  public static String userNameForChangePassword;
  boolean accountCreated = false;
  @FXML
  private Button submit;
  @FXML
  private Label idSpace1;
  @FXML
  private Button logout;
  @FXML
  private Button back;
  @FXML
  private PasswordField pin_User;
  @FXML
  private TextField userName;
  @FXML
  private TextField fullName;
  @FXML
  private TextField userNameToVerify;
  @FXML
  private TextField TF_name;
  @FXML
  private TextField TF_email;
  @FXML
  private TextField TF_password;
  @FXML
  private Button submitVerify;
  @FXML
  private TextField TF_cpassword;
  @FXML
  private TextField pin;
  @FXML
  private TextField confirmPin;
  @FXML
  private ComboBox<String> CB_type;
  @FXML
  private ComboBox<String> CB_type1;
  @FXML
  private Button button_create;
  @FXML
  private TableView<CustomerBooking> tableActivity;
  @FXML
  private TableColumn<?, ?> checkInStatus;
  @FXML
  private TableColumn<CustomerBooking, Integer> tableInvoice;
  @FXML
  private TableColumn<CustomerBooking, String> tableName, tableRoom, tableDate;
  @FXML
  private TableColumn<CustomerBooking, Double> tableCost;
  @FXML
  private TableColumn<CustomerBooking, String> tablePaid;
  @FXML
  private TableColumn<?, ?> invoiceID;
  @FXML
  private TableColumn<?, ?> fullNametoDisplay;
  @FXML
  private TableColumn<?, ?> roomNo;
  @FXML
  private TableColumn<?, ?> dateToDisplay;
  @FXML
  private TableColumn<?, ?> amount;
  @FXML
  private TableColumn<?, ?> paymentStatus;
  @FXML
  private Label labelRoom1, labelRoom2, labelRoom3, labelRoom4, labelRoom5, labelRoom6, labelRoom7, labelRoom8;
  @FXML
  private Label c101, c102, c103, c104, c105, c106, c107, c108;
  private ObservableList<CustomerBooking> roomStatus;

  public void setComboBoxText() {

    CB_type.setPromptText("Select a role.");
    CB_type.getItems().addAll("Desk_Assistant", "Custodian");
  }

  public void setComboBoxText2() {

    CB_type1.setPromptText("Select a role.");
    CB_type1.getItems().addAll("Desk_Assistant", "Custodian", "Customer");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL url, ResourceBundle resources) {
    LocalDate todaysDate = LocalDate.now();
    String datePushed = String.valueOf(todaysDate);
    CustodianScreenController cs = new CustodianScreenController();
    String[] roomArray = cs.roomArray;
    Label[] roomLabelArray = {labelRoom1, labelRoom2, labelRoom3, labelRoom4, labelRoom5,
        labelRoom6, labelRoom7,
        labelRoom8};
    Label[] dateCleanedArray = {c101, c102, c103, c104, c105, c106, c107, c108};
    try {
      DatabaseManager db = new DatabaseManager();
      for (int i = 0; i < roomArray.length; i++) {
        boolean checked = db.roomValidationCleaned(roomArray[i], datePushed);
        String tempDate = db.dateReturn(roomArray[i]);
        dateCleanedArray[i].setText(tempDate);
        if (checked == true) {
          roomLabelArray[i].setText("Cleaned");
          roomLabelArray[i].setTextFill(Color.GREEN);
        } else {
          roomLabelArray[i].setText("Checking required");
          roomLabelArray[i].setTextFill(Color.RED);
        }

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    setComboBoxText();
    setComboBoxText2();
    idSpace1.setText(Main.loggedInUser);
    DatabaseManager db = null;
    try {
      db = new DatabaseManager();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    roomStatus = FXCollections.observableArrayList(db.BookingStatus("all", "all"));
    tableActivity.setItems(roomStatus);

    // setRoomStatus();
    TF_name.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {

      }
    });
/**
 * create account method for the admin for custodian and desk assistant
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
          String type = CB_type.getValue();

          String pintoVerify = pin.getText();
          String pintoVerifyC = confirmPin.getText();

          if (password.equals(cpassword) && pintoVerify.equals(pintoVerifyC)) {
            boolean fieldsCompleted = !userName.equals("") && !email.equals("") //
                && !password.equals("") && !cpassword.equals("") && !fullNameText.equals("")
                && !pintoVerify.equals("") && !pintoVerifyC.equals("");
            if (fieldsCompleted) {
              accountCreated = true;
            } else {
              Main.infoMessage("Please complete the required fields");
            }

            int pintoVerifyInInt = Integer.parseInt(pintoVerify);
            Main.infoMessage("Account sucessfully created.");
            DatabaseManager db = new DatabaseManager();
            db.addByOwner(type, email, userName, password, pintoVerifyInInt);

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
          TF_name.clear();
          TF_email.clear();
          TF_password.clear();
          TF_cpassword.clear();
          fullName.clear();
          CB_type.setPromptText("Select a role.");

          pin.clear();
          confirmPin.clear();

          accountCreated = false;
        }
      }

    });


  }

  /**
   * Logout method
   * @param event
   */
  @FXML
  void logoutMethod(ActionEvent event) {
    Main.loggedInUser = null;
    MainScreenController msc = new MainScreenController();
    msc.loadScene(logout, "MainScreenSample.fxml", "Main Screen ");
  }

  /**
   * This method takes the owner to reset the password for the user he wants to change,
   * the pin verification is required.
   * @param actionEvent reset password screen method
   * @throws SQLException
   */
  public void goToResetScreen(ActionEvent actionEvent) throws SQLException {
    String username = userNameToVerify.getText();
    String pin = pin_User.getText();

    String type = CB_type1.getValue();

    boolean verified;
    DatabaseManager db = new DatabaseManager();
    MainScreenController msc = new MainScreenController();
    verified = db.verifyPasswordorPin(type, username, pin, "VERIFYPIN");
    if (verified == true) {
      userTypeChangePassword = type;
      userNameForChangePassword = username;
      msc.loadScene(submitVerify, "ResetPasswordOwner.fxml", "Password Reset");

    } else {
      Main.errorMessage("Pin doesnt match");
    }

  }
}
