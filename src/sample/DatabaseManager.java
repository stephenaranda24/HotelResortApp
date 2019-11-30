package sample;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 *
 */
public class DatabaseManager extends Main {

	// Database connection
	private Connection con = null;
	String verified = null;

	/**
	 * Getter for property 'connection'.
	 *
	 * @return Value for property 'connection'.
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = DriverManager
				.getConnection("jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApp\\res\\ResortData");
		return connection;
	}

	//double cost = Math.round(c

	/**
	 * @throws SQLException
	 */
	// database connection
	public DatabaseManager() throws SQLException {
		// "jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApp\\res\\ResortData"
		this.con = DriverManager
				.getConnection("jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApp\\res\\ResortData");
		//"jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApp\\res\\ResortData"

	}

	// start the database

	/**
	 *
	 * @param userName
	 * @param password
	 * @param role
	 */
	public void startDatabase(String userName, String password, String role) {
		LogInAccount(userName, password, role);

	}

	// parse String function

	/**
	 *
	 * @param stringlist
	 * @return
	 */
	public ArrayList<String> parseString(String stringlist) {
		ArrayList<String> result = new ArrayList<>();
		for (String token : stringlist.substring(1, stringlist.length() - 1).split(", ")) {
			result.add(token);
		}
		return result;

	}
	public List<Serializable> cardInfo(String userId) throws SQLException{
		String query = "Select * from cardsaved where userid = '"+userId+"'";
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet rs;
		rs = stmt.executeQuery();


		while (rs.next()){
			String cardtype = rs.getString("cardtype");
			long cardNumber = rs.getLong("cardNumber");
			int month = rs.getInt("month");
			int year = rs.getInt("year");
			int cvv = rs.getInt("cvv");
			int billingZip = rs.getInt("billingzipcode");
			System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLL");

			return Arrays
					.asList(cardtype,cardNumber, month, year, cvv, billingZip);
		}
		/*else
		{
			Main.errorMessage("There are no saved card on file");
			return  Arrays
					.asList("null");
		}*/

return null;


	}

	/**
	 * 	Password validation for changing the pin.
	 * @param userType type of the user
	 * @param userID unique ID
	 * @param pinOrPassword Pin or Password depends on users method of changing
	 * @param verifyType verify type (Pin or Password)
	 * @return true if password or pin validate before changing the pin or password
	 * @throws SQLException couldnt get user from database
	 */
	// password validation for changing the pin
	public boolean verifyPasswordorPin(String userType, String userID, String pinOrPassword, String verifyType)
			throws SQLException {
		boolean passwordChanged = false;
		String pinToVerify = null;
		String typeChanged = null;
		Statement stmt = this.con.createStatement();

		try {
			ResultSet rs = null;


			rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE USERNAME = '%s'", userType, userID));

			if (rs.next()) {


				if (verifyType.equals("VERIFYPIN")) {
					int tempPinToVerify = rs.getInt("VERIFYPIN");
					pinToVerify = String.valueOf(tempPinToVerify);
					typeChanged = verifyType;
					if (pinToVerify.equals(pinOrPassword)) {

						passwordChanged = true;
						return true;
					} else {

						passwordChanged = false;
						return false;
					}
				} else if (verifyType.equals("PASSWORD")) {
					pinToVerify = rs.getString("Password");
					typeChanged = verifyType;

					if (pinToVerify.equals(pinOrPassword)) {

						passwordChanged = true;
						return true;
					} else {

						passwordChanged = false;
						return false;
					}
				}
			}
		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
		return passwordChanged;
	}

	/**
	 *
	 * @param userType
	 * @param userId
	 * @param typeChange
	 * @param newPinOrPass
	 * @throws SQLException
	 */
	public void changePinOrPass(String userType, String userId, String typeChange, String newPinOrPass)
			throws SQLException {
		Statement stmt = this.con.createStatement();
		stmt.executeUpdate(String.format("UPDATE %s SET %s = '%s' where username = '%s'", userType, typeChange,
				newPinOrPass, userId));

	}

	/**
	 *
	 * @param email
	 * @param userType
	 * @return
	 */
	public String getTheName(String email, String userType) {
		String id = null;
		try {
			ResultSet rs = null;

			Statement stmt = this.con.createStatement();

			rs = stmt.executeQuery(String.format("SELECT * FROM "+userType+ " WHERE EMAIL = '%s'", email));
			if (rs.next()) {
				id = rs.getString("USERNAME");
			}

		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
		return id;
	}
//Owner adding the user

	/**
	 *
	 * @param typeUser
	 * @param email
	 * @param userId
	 * @param password
	 * @param verifyPin
	 * @return
	 */
	public boolean addByOwner(String typeUser, String email, String userId, String password, int verifyPin) {
		boolean nameDoesntExisted = false;
		try {
			Statement stmt = this.con.createStatement();
			boolean rssHad = false;
			boolean rsHad = false;
			ResultSet rss = null;
			rss = stmt.executeQuery(String.format("SELECT * FROM %s WHERE email = '%s'", typeUser, email));
			if (rss.next()) {
				String emailExist = rss.getString("email");
				nameDoesntExisted = false;
				rssHad = true;
			}
			ResultSet rs = null;
			rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE USERNAME = '%s'", typeUser, userId));
			if (!rssHad && rs.next()) {
				String userNameExisted = rs.getString("USERNAME");
				nameDoesntExisted = false;
				rsHad = true;
			}
			if (!rssHad && !rsHad) {
				stmt.executeUpdate(String.format(
						"INSERT INTO %s (USERNAME, EMAIL, PASSWORD, VERIFYPIN) VALUES " + "('%s','%s','%s','%d')",
						typeUser, userId, email, password, verifyPin));
Main.infoMessage("The account has been successfully created");
			}
		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
		return nameDoesntExisted;

	}

	/**
	 *
	 * @param fullName
	 * @param userName
	 * @param email
	 * @param phonenumber
	 * @param password
	 * @param verifyPin
	 * @param address
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param country
	 * @return
	 */

	public boolean AddCustomer(String fullName, String userName, String email, String phonenumber, String password,
			int verifyPin, String address, String city, String state, int zipcode, String country) {
		boolean nameDoesntExisted = false;
		try {

			Statement stmt = this.con.createStatement();

			boolean rssHad = false;
			boolean rsHad = false;

			ResultSet rss = null;
			rss = stmt.executeQuery(String.format("SELECT * FROM CUSTOMER WHERE email = '%s'", email));
			if (rss.next()) {
				String emailExist = rss.getString("email");
				nameDoesntExisted = false;
				rssHad = true;
			}

			ResultSet rs = null;
			rs = stmt.executeQuery(String.format("SELECT * FROM CUSTOMER WHERE USERNAME = '%s'", userName));
			if (!rssHad && rs.next()) {

				String userNameExisted = rs.getString("USERNAME");
				nameDoesntExisted = false;
				rsHad = true;
			}
			if (!rssHad && !rsHad) {
				stmt.executeUpdate(String.format(
						"INSERT INTO CUSTOMER (FULLNAME, USERNAME, email, phonenumber, password ,verifyPin, address,city, state, zipcode ,country ) "
								+ "VALUES ('%s' ,'%s','%s', '%s','%s','%d','%s', '%s','%s','%d','%s')",
						fullName, userName, email, phonenumber, password, verifyPin, address, city, state, zipcode,
						country));
				nameDoesntExisted = true;
			}
		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
		return nameDoesntExisted;

	}

	/**
	 *
	 * @param email
	 * @param password
	 * @param role
	 * @return
	 */
  public boolean LogInAccount( String email, String password ,String role) {
    Boolean verified = false;
//    //"Owner", "Customer", "Desk_Assistant", "Custodian"
    try {
      String query = "Select * from ";
      switch (role.toLowerCase()){
        case "owner":
          query += "owner ";
          break;
        case "customer":
          query += "customer ";
          break;
        case "desk_assistant":
          query += "Desk_Assistant ";
          break;
        case "custodian":
          query += "CUSTODIAN ";
          break;
        default:
          query += "something ";
          break;
      }
      query += "where email = ?";

      ResultSet rs;
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1,email);
      rs = stmt.executeQuery();

      if(rs.next()){
        String pass = rs.getString("PASSWORD");
        if (pass.equals(password)){
          verified = true;
        }
        else{
					Main.errorMessage("Wrong Password");
          verified = false;
        }
      }
      else{
        Main.errorMessage("Email DOESNT EXIST");
        verified = false;
      }

    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return verified;
  }

	/**
	 * @param userId
	 * @return
	 */
  //method for getting customers name
	public String nameOfTheCustomer(String userId){
		String fullName = null;
		try{
			String query = "Select * from CUSTOMER where username = ?";
			ResultSet rs;
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				fullName = rs.getString("FULLNAME");
			}
			return fullName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<CustomerBooking> BookingStatusForAll(){



		try {
			String query = "Select * from invoiceno ";
			ResultSet rs;
			ArrayList<CustomerBooking> paymentStatus = new ArrayList<>();
			PreparedStatement stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()) {
				int invoice = rs.getInt("Orderno");
				String fullName = rs.getString("fullname");
				String room = rs.getString("roomname");
				String date = rs.getString("DisplayDate");
				double cost = rs.getDouble("Cost");
				String paid = rs.getString("pay");
				String statusCheckin = rs.getString("CHECKINSTATUS");
				CustomerBooking cb = new CustomerBooking(invoice,fullName,room,date,cost,paid,statusCheckin);
				paymentStatus.add(cb);
			}
			return paymentStatus;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//get id from name

	/**
	 *
	 * @param orderNo
	 * @return
	 */
	public String getCustomerUSerId(int orderNo) {
		try {
			String roomNo;
			Statement stmt = this.con.createStatement();

			ResultSet rs = null;
			rs = stmt
					.executeQuery(String.format("Select * FROM INVOICENO where orderno = '%d'", orderNo));
			while (rs.next()) {
				String userName = rs.getString("username");
				return userName;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

		//method for displaying table information in customer screen

	/**
	 *
	 * @param userName
	 * @param status
	 * @return
	 */
	public List<CustomerBooking> BookingStatus(String userName, String status){
		try {

			String query = "Select * from invoiceno where username = ? AND ";
			String queryFullName = "Select * from CUSTOMER where username = ?";
			switch (userName.toLowerCase()){
				case "all":
					query = "Select * from invoiceno ";
					break;
				default:
					break;
			}

			switch (status.toLowerCase()){
				case "true":
					query += "PAY = 'YES'";
					break;
				case "false":
					query += "PAY = 'NO'";
					break;
				case "all":
					query = "Select * from invoiceno ";
					break;

				default:
					query += "null ";
					break;
			}
			ResultSet rs;
			ResultSet rsFullName;
			ArrayList<CustomerBooking> paymentStatus = new ArrayList<>();
			PreparedStatement stmt2 = con.prepareStatement(queryFullName);
			PreparedStatement stmt = con.prepareStatement(query);
			if (userName.equals("all")){
				rs = stmt.executeQuery();
			}else {
				stmt.setString(1,userName);
				rs = stmt.executeQuery();
				stmt2.setString(1,userName);
				rsFullName = stmt2.executeQuery();

			}
			while(rs.next()) {
				int invoice = rs.getInt("Orderno");
				String fullName = rs.getString("fullname");
				String room = rs.getString("roomname");
				String date = rs.getString("DisplayDate");
				double cost = rs.getDouble("Cost");
				String paid = rs.getString("pay");
				String statusCheckin = rs.getString("CHECKINSTATUS");
				CustomerBooking cb = new CustomerBooking(invoice,fullName,room,date,cost,paid,statusCheckin);
				paymentStatus.add(cb);
			}
			return paymentStatus;

			} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//Method for deleting the order

	/**
	 *
	 * @param orderNo
	 * @param userName
	 */
	public void deleteOrder(int orderNo, String userName){
		try{
			String roomNo;
			Statement stmt = this.con.createStatement();

			ResultSet rs = null;
			rs = stmt.executeQuery(String.format("Select * FROM INVOICENO where orderno = '%d'",orderNo));
			while(rs.next()) {
				roomNo = rs.getString("Roomname");
				Statement stm = this.con.createStatement();
				stm.executeUpdate(String.format("Delete from invoiceno where orderno = '%d'",orderNo));
				stm.executeUpdate(String.format("Delete from %s where orderno = '%d'",userName,orderNo));
				stm.executeUpdate(String.format("Delete from %s where orderno = '%d'",roomNo,orderNo));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// Password reset by owner

	/**
	 *
	 * @param userName
	 * @param type
	 * @param password
	 */
	public void passwordReset( String userName, String type, String password){
		try{
			String query = "UPDATE ";

				switch (type.toLowerCase()){
					case "customer":
						query += "customer ";
						break;
					case "desk_assistant":
						query += "Desk_Assistant ";
						break;
					case "custodian":
						query += "CUSTODIAN ";
						break;
					default:
						query += "something ";
						break;
				}
				query += "set password = ? where username = ?";



			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, password);
			stmt.setString(2,userName);
			stmt.executeUpdate();





		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *
	 * @param orderNo
	 * @return
	 */
	public double orderNumberAmountr(int orderNo) {
		double amountOf = 0;
		try {
			Statement stmt = this.con.createStatement();

			ResultSet rs = null;
			rs = stmt.executeQuery(String.format("Select * FROM INVOICENO where orderno = '%d'",orderNo));
			while (rs.next()) {
				String orderNo1 = rs.getString("cost");
				amountOf = Double.parseDouble(orderNo1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return amountOf;
	}



	//Method for saving card information to the database

	/**
	 *
	 * @param userName
	 * @param email
	 */
	public void saveEmailCardTable(String userName, String email) {
		try {
			ResultSet rs = null;
			Statement stmt = this.con.createStatement();
			stmt.executeUpdate(String.format("Insert Into CARDSAVED (CARDMEMBEREMAIL, USERID) VALUES ('%s','%s')",
					email, userName));
		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
	}

	/**
	 *
	 * @param userId
	 * @param cardType
	 * @param cardNumber
	 * @param month
	 * @param year
	 * @param cvv
	 * @param zipcode
	 */

	public void saveCardInfo(String userId, String cardType, long cardNumber, int month, int year, int cvv,
			int zipcode) {
		try {
			Statement stmt = this.con.createStatement();
			stmt.executeUpdate(String.format(
					"UPDATE CARDSAVED SET (cardtype, cardNumber, month, year, cvv, billingzipcode) = ('%s','%d','%d','%d','%d','%d') where userid = '%s'",
					cardType, cardNumber, month, year, cvv, zipcode, userId));

		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
	}

	/**
	 *
	 * @param userName
	 */
	public void createCustomerTable(String userName) {
		try {
			Statement stmt = this.con.createStatement();
			String sql = "CREATE TABLE " + userName + "(ORDERNO INTEGER , " + " ROOMNO VARCHAR(255), "
					+ " DaysBooked VARCHAR(4555), " + " DISPLAYDATE VARCHAR(255), " + "COST DOUBLE ) ";
			stmt.executeUpdate(sql);
		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
	}

	/**
	 *
	 * @param orderNO
	 */
//Update database about payment status
	public void paidColumn(int orderNO) {
		try {
			Statement stmt = this.con.createStatement();
			String paymentDone = "YES";
			ResultSet rs = null;
			stmt.executeUpdate(String.format("UPDATE INVOICENO SET pay = 'YES' where orderno = '%d'", orderNO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param orderNO
	 * @param roomName
	 */
	//update table with checkin status
	public void checkedIn(int orderNO, String roomName) {
		try {
			Statement stmt = this.con.createStatement();
			String paymentDone = "YES";
			ResultSet rs = null;
			stmt.executeUpdate(String.format("UPDATE INVOICENO SET CHECKINSTATUS = 'YES' where orderno = '%d'", orderNO));
			stmt.executeUpdate(String.format("UPDATE %s SET CHECKINSTATUS = 'YES' where orderno = '%d'",roomName, orderNO));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return
	 */
	public int orderNumber() {
		int orderNo = 0;
		try {
			Statement stmt = this.con.createStatement();

			ResultSet rs = null;
			rs = stmt.executeQuery(String.format("Select MAX(ORDERNO) FROM INVOICENO"));
			while (rs.next()) {
				String orderNo1 = rs.getString(1);
				orderNo = Integer.parseInt(orderNo1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderNo;
	}

	/**
	 *
	 * @param userName
	 * @param roomNo
	 * @param dateBooked
	 * @param cost
	 * @param dateToDisplay
	 * @param fullName
	 * @param checkedInStatus
	 */
	public void pushDate(String userName, String roomNo, ArrayList<String> dateBooked, double cost,
			String dateToDisplay, String fullName, String checkedInStatus) {
		try {
			int orderNo = 0;

			long timeStamp = System.currentTimeMillis();
			String defaultPay = "NO";

			Statement stmt = this.con.createStatement();
			stmt.executeUpdate(String.format(
					"INSERT INTO INVOICENO (roomname, fullname, username, dateBooked, pay, cost, displaydate, CHECKINSTATUS) VALUES ('%s','%s','%s','%s','%s','%f','%s','%s')",
					roomNo, fullName, userName, dateBooked, defaultPay, cost, dateToDisplay,checkedInStatus));
			/*
			 * ResultSet rs =
			 * stmt.executeQuery(String.format("Select ORDERNO FROM INVOICENO"));
			 *
			 */
			ResultSet rs = stmt.executeQuery(String.format("Select MAX(ORDERNO)  FROM INVOICENO"));

			while (rs.next()) {
				String orderNo1 = rs.getString(1);
				orderNo = Integer.parseInt(orderNo1);



			}


			stmt.executeUpdate(String.format(
					"INSERT INTO %s (orderno, username, dateBooked, cost, displaydate,CHECKINSTATUS) VALUES ('%s','%s','%s','%f','%s','NO')",
					roomNo, orderNo, userName, dateBooked, cost, dateToDisplay));
			stmt.executeUpdate(String.format(
					"INSERT INTO %s (orderno, roomno, daysBooked, cost, displaydate) VALUES ('%d', '%s' ,'%s','%f','%s')",
					userName, orderNo, roomNo, dateBooked, cost, dateToDisplay));

		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}

	}

	/**
	 *
	 * @param userName
	 * @return
	 */
	public List<Serializable> viewUnpaidTable(String userName) {
		try {
			PreparedStatement stmt = con.prepareStatement(
					String.format("Select * FROM INVOICENO WHERE (USERNAME, PAY) = ('%s','no')", userName));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String roomNumber = rs.getString("roomname");
				String dateDisplay = rs.getString("DISPLAYDATE");
				double cost = rs.getDouble("COST");
				return Arrays.asList(roomNumber, cost, dateDisplay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//method for returning the date of the room was cleaned
/*  public String dateCleaned(String roomName){
	  try {
      PreparedStatement stmt = con.prepareStatement(String.format("Select * FROM ROOMSTATUS WHERE roomname = '%s'", roomName));
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        String date = rs.getString("date");
        return date;
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
	  return null;

  }*/

	//method for passing clean status from custodian to database

	/**
	 *
	 * @param room
	 * @param status
	 * @param datePushed
	 * @param name
	 */
	public void roomCheckedDatabase(String room, boolean status, String datePushed, String name){
		try{

			String update = ("UPDATE ROOMSTATUS SET (cleaned, date, MarkedBy) = ("+ status + " , '" + datePushed + "', '"+name +"') where roomname = '"+room+"'");
			PreparedStatement stmt = con.prepareStatement(update);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Method for retriving the username for the payment screen with order number
	public String nameFetch(int order){
		try{
			PreparedStatement stmt = con.prepareStatement(
					String.format("Select * FROM INVOICENO WHERE ROOMNAME = "+ order));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				String userName = rs.getString("username");

				return userName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}




	// method for checking if room is cleaned during intialization

	/**
	 *
	 * @param room
	 * @param dateToday
	 * @return
	 */
	public boolean roomValidationCleaned(String room, String dateToday){
		try{
			PreparedStatement stmt = con.prepareStatement(
					String.format("Select * FROM ROOMSTATUS WHERE roomname = '%s'", room));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String date = rs.getString("date");
				boolean checked = rs.getBoolean("CLEANED");

				if((date.equals(dateToday)) && (checked == true)){
					return true;
				}
				else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//get the current date cleaned for each room
	public String dateReturn(String room){
		try{
			PreparedStatement stmt = con.prepareStatement(
					String.format("Select * FROM ROOMSTATUS WHERE ROOMNAME = '%s'", room));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				String dateDisplay = rs.getString("date");

				return dateDisplay;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param room
	 * @return
	 */
	public String custodianNameReturn(String room){
		try{
			PreparedStatement stmt = con.prepareStatement(
					String.format("Select * FROM ROOMSTATUS WHERE ROOMNAME = '%s'", room));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				String markedBy = rs.getString("markedby");

				return markedBy;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	//validate to check if the room was cleaned yesterday

	/**
	 *
	 * @param date
	 * @throws SQLException
	 */
	public void custodianDateValidation(String date) throws SQLException {
		try {
			Statement stmt = this.con.createStatement();

			stmt.executeUpdate(String.format("UPDATE ROOMSTATUS SET cleaned = false where date != '%s'", date));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param userName
	 * @param roomNo
	 * @param dateBooked
	 */
	public void pushDateToUserTable(String userName, String roomNo, ArrayList<String> dateBooked) {
		try {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			Statement stmt = this.con.createStatement();
			stmt.executeUpdate(String.format("INSERT INTO %s (orderno, roomno, daysBooked) VALUES ('%s', 12 ,'%s')",
					userName, roomNo, dateBooked));
		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}

	}

	/**
	 *
	 * @param room
	 * @param userName
	 * @param Datetobebook
	 * @return
	 */
	public String pushDateToRoom(String room, String userName, ArrayList<String> Datetobebook) {
		try {

			HashMap<String, String[]> roomMatch = new HashMap<>();
			roomMatch.put("A", new String[] { "ROOMA101", "ROOMA102" });
			roomMatch.put("B", new String[] { "ROOMB103", "ROOMB104" });
			roomMatch.put("C", new String[] { "ROOMC105", "ROOMC106" });
			roomMatch.put("D", new String[] { "ROOMD107", "ROOMD108" });
			boolean matchfound = false;

			String[] roomListSelected = roomMatch.get(room);
			roomLoop: for (String roomName : roomListSelected) {

				Statement stmt = this.con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT DATEBOOKED FROM " + roomName);

				ArrayList<String> roomAlreadyBooked = new ArrayList<>();
				while (rs.next()) {
					String bookedListed = rs.getString("DateBooked");
					roomAlreadyBooked.addAll(parseString(bookedListed));
				}
				for (String day : Datetobebook)
				{
					if (roomAlreadyBooked.contains(day)) {
						continue roomLoop;
					}
				}
				/* pushDate(userName, roomName, Datetobebook); */
				return roomName;
			}

		} catch (SQLException var6) {
			this.sqlExceptionHandler(var6);
		}
		return null;

	}

	/**
	 *
	 * @param error
	 */

	public void sqlExceptionHandler(SQLException error) {

		error.getMessage();
	}
}