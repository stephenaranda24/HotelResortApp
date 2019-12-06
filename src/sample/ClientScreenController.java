package sample;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.xml.soap.Text;
import org.h2.table.Table;

/**
 * This client controller class implements all actions done on the client screen
 *
 * @version 1.0
 * @ Romanov Andre
 * @ Shafi Mushfique
 * @ Stephen Aranda
 * @since 2019-09-21
 */
public class ClientScreenController implements Initializable {

  public static String userId;
  public static int orderNo;
  public static String tableName;
  @FXML
  TableView<CustomerBooking> tableUnpaid;
  @FXML
  TableColumn<CustomerBooking, String> upTableRoomNo;
  @FXML
  TableColumn<CustomerBooking, String> upTableDate;
  @FXML
  TableColumn<CustomerBooking, Double> upTableAmount;
  @FXML
  TableColumn<CustomerBooking, Boolean> upTableInvoice;
  @FXML
  TableView<CustomerBooking> tablePaid;
  @FXML
  TableColumn<CustomerBooking, String> pTableRoomNo;
  @FXML
  TableColumn<CustomerBooking, String> pTableDate;
  @FXML
  TableColumn<CustomerBooking, Double> pTableAmount;
  @FXML
  TableColumn<CustomerBooking, Boolean> pTableInvoice;
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
  private Label userLabel, typeLabel;
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
  @FXML
  private Label type;
  private ObservableList<CustomerBooking> paid;
  private ObservableList<CustomerBooking> unpaid;
  @FXML
  private Button deleteOrder;
  @FXML
  private Button payNow;
  @FXML
  private ComboBox<String> monthStart;
  @FXML
  private ComboBox<String> dateStart;
  @FXML
  private ComboBox<String> startYear;
  @FXML
  private ComboBox<String> monthEnd;
  @FXML
  private ComboBox<String> dateEnd;
  @FXML
  private ComboBox<String> endYear;
  @FXML
  private DatePicker startDatePicker;
  @FXML
  private DatePicker endDatePicker;
  @FXML
  private Button logoutButton;
  @FXML
  private ImageView roomAIView, roomBIView, roomCIView, roomDIView;
  @FXML
  private Label roomALabel;

  /**
   * This method is used for the the client to be able to log out of their account and returns to
   * the main screen.
   *
   * @param event This is an object of the ActionEvent class.
   */
  @FXML
  void logOut(ActionEvent event) {
    Main.loggedInUser = null;
    Main.Type = null;
    MainScreenController msc = new MainScreenController();
    msc.loadScene(logoutButton, "MainScreenSample.fxml", "MainScreen");

  }

  //#byshafi
  @FXML
  void tempButton(ActionEvent event) {
    MainScreenController msc = new MainScreenController();
    if (tableUnpaid.getSelectionModel().getSelectedItem() != null) {
      //	msc.loadScene(submit, "PaymentScreen.fxml", "Payment Screen");
      try {
        DatabaseManager dm = new DatabaseManager();
        System.out.println("Unpaid bookings: " + dm.viewUnpaidTable(Main.loggedInUser));
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // After transaction has been paid:
      CustomerBooking selection = tableUnpaid.getSelectionModel().getSelectedItem();
      selection.setPaid("TRUE");
      // add booking to paid table
      ObservableList<CustomerBooking> nowPaid = FXCollections.observableArrayList(//
          tableUnpaid.getSelectionModel().getSelectedItem()); //
      tablePaid.getItems().addAll(nowPaid);
      // remove booking from unpaid table
      tableUnpaid.getItems().remove(selection);
    }


  }

  /**
   * This method updates the password when the old password and new password are entered by the user
   * and the button is pressed.
   *
   * @param event An object of the ActionEvent class.
   * @throws SQLException Exception on sql error
   */
  @FXML
  void updatePasswordAction(ActionEvent event) throws SQLException {
    boolean passwordMatched = false;
    String userId = idSpace11.getText();

    String oPassword = oldPassword.getText();
    String password = newPassword.getText();
    String cPassword = newPasswordVerify.getText();

    DatabaseManager dm = new DatabaseManager();

    if (password.equals(cPassword)) {
      passwordMatched = dm.verifyPasswordorPin("Customer", userId, oPassword, "PASSWORD");
      if (passwordMatched == true) {
        dm.changePinOrPass("Customer", userId, "PASSWORD", password);
        Main.infoMessage("Password Changed");
        oldPassword.clear();
        newPassword.clear();
        newPasswordVerify.clear();


      } else {
        Main.errorMessage("Old Password does not match please re-enter");
        oldPassword.clear();
        newPassword.clear();
        newPasswordVerify.clear();
      }
    } else {
      Main.errorMessage("Password does not match");
    }
  }

  /**
   * This method updates the pin of the client after entering the old and new pins and hitting the
   * button.
   *
   * @param event An object of the ActionEvent class.
   * @throws SQLException Exception on sql error.
   */
  @FXML
  void updatePin(ActionEvent event) throws SQLException {
    boolean pinMatched = false;
    String userId = idSpace11.getText();
    String oPin = oldPin.getText();
    String pin = newPin.getText();
    String cPin = newPinVerify.getText();

    DatabaseManager dm = new DatabaseManager();

    if (pin.equals(cPin)) {
      pinMatched = dm.verifyPasswordorPin("Customer", userId, oPin, "VERIFYPIN");
      if (pinMatched == true) {
        dm.changePinOrPass("Customer", userId, "VERIFYPIN", pin);
        Main.infoMessage("Pin Changed");
        oldPin.clear();
        newPin.clear();
        newPinVerify.clear();
      } else {
        Main.errorMessage("Old Password does not match please re-enter");
        oldPin.clear();
        newPin.clear();
        newPinVerify.clear();
      }

    } else {
      Main.errorMessage("Pin does not match");
    }

  }

  /**
   * {@inheritDoc}
   */
  public void initialize(URL url, ResourceBundle resources) {
    try {
      DatabaseManager db = new DatabaseManager();
      userId = db.getTheName(Main.loggedInUser, "Customer");
      Main.tempName = userId;
      paid = FXCollections.observableArrayList(db.BookingStatus(userId, "true"));
      unpaid = FXCollections.observableArrayList(db.BookingStatus(userId, "false"));
      tableUnpaid.setItems(unpaid);
      tablePaid.setItems(paid);


    } catch (SQLException e) {
      e.printStackTrace();
    }

    userLabel.setText(userId);
    idSpace11.setText(userId);
    typeLabel.setText(Main.Type);
    setRoomSelectCombo();

    roomAIView.setImage(new Image("/sample/Room A.jpg"));
    roomBIView.setImage(new Image("/sample/Room B.jpg"));
    roomCIView.setImage(new Image("/sample/Room C.jpg"));
    roomDIView.setImage(new Image("/sample/Room D.jpg"));
    //	roomAImageView.setY(740);

  }

  /**
   * This method sets the 'roomSelectCombo' combo box equal to the the information stored in date
   * and cost manager.
   */

  private void setRoomSelectCombo() {
    DateAndCostManager dm = new DateAndCostManager();
    dm.setRoomSelectCombo(roomSelectCombo);
  }

  /**
   * This method submits the the dates entered by the user from the date pickers.
   *
   * @param event An object of the ActionEvent class.
   * @throws SQLException Exception on sql error.
   */
  @FXML
  void submitDate(ActionEvent event) throws SQLException {
    MainScreenController msc = new MainScreenController();

    String gettId = userLabel.getText();
    String username = gettId;

    DateAndCostManager dc = new DateAndCostManager();
    String startDates = startDatePicker.getValue()
        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    String enddate = endDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    List newList = dc.dateCalc(roomSelectCombo, startDates, enddate);

    if (newList.get(0).equals("null")) {
      Main.errorMessage("Please pick the correct date again");
    } else {

      DatabaseManager db = new DatabaseManager();
      ArrayList<String> dateList = (ArrayList<String>) newList.get(4);
      double cost = (double) newList.get(3);
      String dateToDisplay = (String) newList.get(5);

      String result = db.pushDateToRoom(roomSelectCombo.getValue(), gettId, dateList);

      if (result != null) {
        String fullName = db.nameOfTheCustomer(gettId);

        db.pushDate(gettId, result, dateList, cost, dateToDisplay, fullName, "NO");
        orderNo = db.orderNumber();
        Main.infoMessage("Thank you for making the reservation"
            + "\nYour booking has been confirmed"
            + "\nYour booking number is " + orderNo
            + "\nPeriod of Stay " + dateToDisplay
            + "\nThe cost of the booking is $" + cost
            + "\nYou will rediredted to the Payment Screen"
            + "\nYou may skip the payment by using pay later option"
            + "\n Additional information will be provided on Payment Scree");
        msc.loadScene(submit, "PaymentScreen.fxml", "Payment Screen");
      } else {
        Main.infoMessage("Sorry the rooms selected are not available for those dates");
      }


    }
  }

  /**
   * This method gets the invoice numbers from the correct tables (Unpaid/Paid) by using an if-else
   * statement.
   *
   * @param table This is an object from the TableView class.
   * @return
   */

  public int fetchOrder(TableView table) {
    int index = table.getSelectionModel().getSelectedIndex();
    CustomerBooking invoiceNo;

    if (table == tableUnpaid) {
      invoiceNo = tableUnpaid.getItems().get(index);
      tableName = "yes";
      System.out.println("Yess");
    } else {
      invoiceNo = tablePaid.getItems().get(index);
      tableName = null;
    }

    System.out.println();
    int newValue = invoiceNo.getInvoice();
    return newValue;

  }

  /**
   * This method makes it so an error message occurs when you attempt to pay for a booking that is
   * already paid. It also ensures that if the booking needs to be paid, it will load the payment
   * scree when the button is pressed.
   *
   * @throws SQLException Exception on sql errors.
   */
  @FXML
  public void paymentScreenRunning(ActionEvent actionEvent) throws SQLException {
    MainScreenController msc = new MainScreenController();

    if (tableUnpaid.getSelectionModel().isEmpty()) {
      Main.errorMessage("this booking is already paid");
    } else {
      orderNo = fetchOrder(tableUnpaid);

      msc.loadScene(payNow, "PaymentScreen.fxml", "Payment Screen");
    }
  }

  /**
   * This method deletes any unpaid or paid booking from the designated
   * table.
   *
   * @param event An object of the ActionEvent class.
   * @throws SQLException Exception on sql error.
   */
  @FXML
  void deleteTheOrder(ActionEvent event) throws SQLException {

    if (tableUnpaid.getSelectionModel().isEmpty()) {
      orderNo = fetchOrder(tablePaid);

    } else {
      orderNo = fetchOrder(tableUnpaid);
    }
    DatabaseManager db = new DatabaseManager();
    db.deleteOrder(orderNo, userId);
    tableUnpaid.getSelectionModel().clearSelection();
    tableUnpaid.getItems().clear();
    tablePaid.getSelectionModel().clearSelection();
    tablePaid.getItems().clear();
    paid = FXCollections.observableArrayList(db.BookingStatus(userId, "true"));
    unpaid = FXCollections.observableArrayList(db.BookingStatus(userId, "false"));
    tableUnpaid.setItems(unpaid);
    tablePaid.setItems(paid);

  }

}