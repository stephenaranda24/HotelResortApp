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
import javax.xml.soap.Text;
import org.h2.table.Table;

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
	@FXML
	private Label type;

	public static String userId;
	public static int orderNo;
	private ObservableList<CustomerBooking> paid;
	private ObservableList<CustomerBooking> unpaid;


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
	void logOut(ActionEvent event) {
		Main.loggedInUser = null;
		MainScreenController msc = new MainScreenController();
		msc.loadScene(logoutButton,"MainScreenSample.fxml", "MainScreen");

	}




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

		/*
		 * ClientScreenController.orderNo = Integer.parseInt(tempLabel.getText());
		 * System.out.println(ClientScreenController.orderNo + "if it works");
		 */

	}

	@FXML
	void updatePasswordAction(ActionEvent event) throws SQLException {
		boolean passwordMatched = false;
		String userId = idSpace11.getText();
		String oPassword = oldPassword.getText();
		String password = newPassword.getText();
		String cPassword = newPasswordVerify.getText();

		DatabaseManager dm = new DatabaseManager();

		if (password.equals(cPassword)) {
			passwordMatched = dm.verifyPasswordorPin("Customer", userId, oPassword, "password");
			if (passwordMatched == true) {
				dm.changePinOrPass("Customer", userId, "password", password);
				System.out.println("password Changed");
			}
		} else {
			Main.errorMessage("Password does not match");
		}
	}

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
				System.out.println("pin Changed");
			}

		} else {
			Main.errorMessage("Pin does not match");
		}

	}

	public void initialize(URL url, ResourceBundle resources) {


		try {
			DatabaseManager db = new DatabaseManager();
			userId = db.getTheName(Main.loggedInUser, "Customer");
			paid =FXCollections.observableArrayList(db.BookingStatus(userId,"true"));
			System.out.println(userId+ "dsadasdada");
			System.out.println(paid);
			unpaid =FXCollections.observableArrayList(db.BookingStatus(userId,"false"));
			tableUnpaid.setItems(unpaid);
			tablePaid.setItems(paid);


		} catch (SQLException e) {
			e.printStackTrace();
		}

		idSpace1.setText(userId);
		idSpace11.setText(userId);
		setRoomSelectCombo();



	}

	private void setRoomSelectCombo() {
		DateAndCostManager dm = new DateAndCostManager();
		dm.setRoomSelectCombo(roomSelectCombo);
	}
//

	@FXML
	void submitDate(ActionEvent event) throws SQLException {
		MainScreenController msc = new MainScreenController();

		String gettId = idSpace1.getText();
		String username = gettId;
		System.out.println("I got the id as " + gettId);
		DateAndCostManager dc = new DateAndCostManager();
		String startDates = startDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		System.out.println(startDates);

		String enddate = endDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		System.out.println(enddate);
		List newList = dc.dateCalc(roomSelectCombo, startDates,enddate);

		/*List newList = dc.dateCalc(roomSelectCombo, startDate, startMonth, endDate, endMonth);*/

		/*
		 * String startDate = startDat.getText(); String startMonths =
		 * startMonth.getText(); String endDatee = endDate.getText(); String endMonths =
		 * endMonth.getText(); String room = roomSelectCombo.getValue();
		 *
		 * String dateMonthStart = String.format("%s/%s/2019",startMonths,startDate);
		 * String dateMonthend = String.format("%s/%s/2019",endMonths,endDatee);
		 * System.out.println(dateMonthStart);
		 *
		 *//*
		 * Format f = new SimpleDateFormat("MM/dd/yyyy"); String strDate =
		 * f.format(dateMonthStart); System.out.println(strDate + " Alpha");
		 *//*
		 * Date startBookingDate = new Date(dateMonthStart); System.out.println(new
		 * SimpleDateFormat("MM/dd/yyyy").format(startBookingDate)); Date endBookingDate
		 * = new Date(dateMonthend); long totalDaysBooked = endBookingDate.getTime() -
		 * startBookingDate.getTime(); totalDaysBooked = (long)
		 * Math.ceil((double)totalDaysBooked/86400000); String dateBookedToDisplay =
		 * startBookingDate + " - " +endBookingDate;
		 * System.out.println(dateBookedToDisplay); System.out.
		 * println("result sdfjosaekjfkjdskfndsjfndskjlfnkjdskjfbsdkjfsdkjfnkndsjllkjfns   "
		 * + totalDaysBooked); ArrayList<String> dateList = new ArrayList<>(); for(int i
		 * =0; i<totalDaysBooked;i++){ Calendar cal = Calendar.getInstance();
		 * SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		 *
		 * cal.setTime(startBookingDate);
		 *
		 * cal.add(Calendar.DAY_OF_MONTH, i); Date addDate = cal.getTime();
		 * System.out.println(addDate); String finalDateformat = sdf.format(addDate);
		 * System.out.println(finalDateformat ); dateList.add(finalDateformat); }
		 */
		// Object myObject[] =new String[4];
		// myObject[0] = totalDaysBooked;
		// myObject[1] = startBookingFDate;
		// myObject[2] = endBookingFDate;
		// myObject[3] = bookingCost;
		// myObject[4] = dateList;

		DatabaseManager db = new DatabaseManager();
		ArrayList<String> dateList = (ArrayList<String>) newList.get(4);
		System.out.println(dateList + "LOOKINGOUT");
		double cost = (double) newList.get(3);
		System.out.println(dateList + "LOOKINGOUT" + cost);
		String dateToDisplay = (String) newList.get(5);
		System.out.println("The booking was between " + dateToDisplay);

		String result = db.pushDateToRoom(roomSelectCombo.getValue(), gettId, dateList);

		if (result != null) {
			System.out.println(result + " Looking for result");
			String fullName = db.nameOfTheCustomer(gettId);

			db.pushDate(gettId, result, dateList, cost, dateToDisplay, fullName, "NO");
			orderNo = db.orderNumber();
			System.out.println("order no is " + orderNo);

		} else {
			Main.infoMessage("Sorry the rooms selected are not available for those dates");
		}

		System.out.println(dateList);

		msc.loadScene(submit, "PaymentScreen.fxml", "Payment Screen");
	}
	public int fetchOrder(TableView table){
		int index = table.getSelectionModel().getSelectedIndex();

		CustomerBooking invoiceNo = tableUnpaid.getItems().get(index);
		int newValue = invoiceNo.getInvoice();
		return newValue;

	}
	@FXML
	public void paymentScreenRunning(ActionEvent actionEvent) throws SQLException {
		MainScreenController msc = new MainScreenController();
		orderNo = fetchOrder(tableUnpaid);
		msc.loadScene(payNow, "PaymentScreen.fxml", "Payment Screen");
	}

	@FXML
	void deleteTheOrder(ActionEvent event) throws SQLException {

	if(tableUnpaid.getSelectionModel().isEmpty()){
		orderNo = fetchOrder(tablePaid);

	}
	else{
		orderNo = fetchOrder(tableUnpaid);

	}
		System.out.println(orderNo+"this is the order");
	DatabaseManager db = new DatabaseManager();
	db.deleteOrder(orderNo,userId);
		tableUnpaid.getSelectionModel().clearSelection();
		tableUnpaid.getItems().clear();
		tablePaid.getSelectionModel().clearSelection();
		tablePaid.getItems().clear();


	paid =FXCollections.observableArrayList(db.BookingStatus(userId,"true"));
	unpaid =FXCollections.observableArrayList(db.BookingStatus(userId,"false"));
	tableUnpaid.setItems(unpaid);
	tablePaid.setItems(paid);






	}

}