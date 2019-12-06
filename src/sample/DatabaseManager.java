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
 * @version 1.0
 * @ Romanov Andre
 * @ Shafi Mushfique
 * @ Stephen Aranda
 * @since 2019-09-21
 */
public class DatabaseManager extends Main {

  String verified = null;
  // Database connection
  private Connection con = null;

  /**
   * Constructor for the DatabaseManager class.
   *
   * @throws SQLException Exception on sql error.
   */
  // database connection
  public DatabaseManager() throws SQLException {
    // "jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApp\\res\\ResortData"
    this.con = DriverManager
        .getConnection(
            "jdbc:h2:C:\\Users\\moart\\OneDrive - Florida Gulf Coast University\\IDEAProjects\\HotelResortApp\\res\\ResortData");
    //"jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApp\\res\\ResortData"

  }

  //double cost = Math.round(c

  /**
   * Getter for property 'connection'.
   *
   * @return Value for property 'connection'.
   */
  public static Connection getConnection() throws SQLException {
    Connection connection = DriverManager
        .getConnection(
            "jdbc:h2:C:\\Users\\moart\\OneDrive - Florida Gulf Coast University\\IDEAProjects\\HotelResortApp\\res\\ResortData");
    return connection;
  }

  // start the database

  /**
   * This method is used to start working with database that involves the users username,
   * password, and role.
   *
   * @param userName The users unique username for identification.
   * @param password The users unique password used to be able to log in.
   * @param role The users role which has an option of being an admin,a client, a custodian, or a desk assistant.
   */
  public void startDatabase(String userName, String password, String role) {
    LogInAccount(userName, password, role);

  }

  // parse String function

  /**
   *
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

  /**
   * This method here loops through the saved card information table and obtains
   * all of its properties such as the card type, card number, expiration date
   * and zip code and returns it as a list in an array.
   *
   * @param userId The users Id used for identification.
   * @return The list containing the card information
   * @throws SQLException Exception on sql error.
   */

  public List<Serializable> cardInfo(String userId) throws SQLException {
    String query = "Select * from cardsaved where userid = '" + userId + "'";
    PreparedStatement stmt = con.prepareStatement(query);
    ResultSet rs;
    rs = stmt.executeQuery();

    while (rs.next()) {
      String cardtype = rs.getString("cardtype");
      long cardNumber = rs.getLong("cardNumber");
      int month = rs.getInt("month");
      int year = rs.getInt("year");
      int cvv = rs.getInt("cvv");
      int billingZip = rs.getInt("billingzipcode");
      System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLL");

      return Arrays.asList(cardtype, cardNumber, month, year, cvv, billingZip);
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
   * Password validation for changing the pin.
   *
   * @param userType      Type of the user
   * @param userID        Unique ID
   * @param pinOrPassword Pin or Password depends on users method of changing
   * @param verifyType    Verify type (Pin or Password)
   * @return True if password or pin validate before changing the pin or password
   * @throws SQLException Exception on sql error.
   */
  // password validation for changing the pin
  public boolean verifyPasswordorPin(String userType, String userID, String pinOrPassword,
      String verifyType)
      throws SQLException {
    boolean passwordChanged = false;
    String pinToVerify = null;
    String typeChanged = null;
    Statement stmt = this.con.createStatement();

    try {
      ResultSet rs = null;

      rs = stmt
          .executeQuery(String.format("SELECT * FROM %s WHERE USERNAME = '%s'", userType, userID));

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
   * This method makes it possible to update the users pin or password by accepting
   * the user type, user Id, the kind of change (Pin or Password),
   * and finally the new pin or password.
   *
   * @param userType The type of user that is logged in.
   * @param userId The Id of the user logged in.
   * @param typeChange The type of change that is being made (Pin or Password).
   * @param newPinOrPass The new pin or password that is entered by the user.
   * @throws SQLException Exception on sql error.
   */
  public void changePinOrPass(String userType, String userId, String typeChange,
      String newPinOrPass)
      throws SQLException {
    Statement stmt = this.con.createStatement();
    stmt.executeUpdate(
        String.format("UPDATE %s SET %s = '%s' where username = '%s'", userType, typeChange,
            newPinOrPass, userId));

  }

  /**
   * This method gets the name of the user from the database by accepting the
   * email and the user type and returns it.
   *
   * @param email The email of the user that is logged in.
   * @param userType The type of the user.
   * @return The user Id is returned.
   */
  public String getTheName(String email, String userType) {
    String id = null;
    try {
      ResultSet rs = null;

      Statement stmt = this.con.createStatement();

      rs = stmt
          .executeQuery(String.format("SELECT * FROM " + userType + " WHERE EMAIL = '%s'", email));
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
   * This method is used to create an account as an owner by accepting the required credentials.
   *
   * @param typeUser
   * @param email
   * @param userId
   * @param password
   * @param verifyPin
   * @return
   */
  public boolean addByOwner(String typeUser, String email, String userId, String password,
      int verifyPin) {
    boolean nameDoesntExisted = false;
    try {
      Statement stmt = this.con.createStatement();
      boolean rssHad = false;
      boolean rsHad = false;
      ResultSet rss = null;
      rss = stmt
          .executeQuery(String.format("SELECT * FROM %s WHERE email = '%s'", typeUser, email));
      if (rss.next()) {
        String emailExist = rss.getString("email");
        nameDoesntExisted = false;
        rssHad = true;
      }
      ResultSet rs = null;
      rs = stmt
          .executeQuery(String.format("SELECT * FROM %s WHERE USERNAME = '%s'", typeUser, userId));
      if (!rssHad && rs.next()) {
        String userNameExisted = rs.getString("USERNAME");
        nameDoesntExisted = false;
        rsHad = true;
      }
      if (!rssHad && !rsHad) {
        stmt.executeUpdate(String.format(
            "INSERT INTO %s (USERNAME, EMAIL, PASSWORD, VERIFYPIN) VALUES "
                + "('%s','%s','%s','%d')",
            typeUser, userId, email, password, verifyPin));
        Main.infoMessage("The account has been successfully created");
      }
    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return nameDoesntExisted;

  }

  /**
   * This method makes it possible to add a new customer into the database when a user decides
   * to sign up for our application.
   *
   * @param fullName The first and last name of the user.
   * @param userName The username that the user sets up.
   * @param email The email of the user.
   * @param phonenumber The phone number of the user.
   * @param password The password of the user.
   * @param verifyPin The pin of the user.
   * @param address The address of the user.
   * @param city The city the user resides in.
   * @param state The state the user resides in.
   * @param zipcode The zipcode of the county the user resides in.
   * @param country The country the user resides in.
   * @return A boolean value of whether the username exists already or not is returned, which determines if the account can be created.
   */

  public boolean AddCustomer(String fullName, String userName, String email, String phonenumber,
      String password,
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
      rs = stmt
          .executeQuery(String.format("SELECT * FROM CUSTOMER WHERE USERNAME = '%s'", userName));
      if (!rssHad && rs.next()) {

        String userNameExisted = rs.getString("USERNAME");
        nameDoesntExisted = false;
        rsHad = true;
      }
      if (!rssHad && !rsHad) {
        stmt.executeUpdate(String.format(
            "INSERT INTO CUSTOMER (FULLNAME, USERNAME, email, phonenumber, password ,verifyPin, address,city, state, zipcode ,country ) "
                + "VALUES ('%s' ,'%s','%s', '%s','%s','%d','%s', '%s','%s','%d','%s')",
            fullName, userName, email, phonenumber, password, verifyPin, address, city, state,
            zipcode,
            country));
        nameDoesntExisted = true;
      }
    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return nameDoesntExisted;

  }

  /**
   * This method makes it possible for the user to be able to log in using their log in credentials such
   * as their email, password, and their roles (Admin, Guest, Desk Assistant, Custodian).
   *
   * @param email
   * @param password
   * @param role
   * @return
   */
  public boolean LogInAccount(String email, String password, String role) {
    Boolean verified = false;
//    //"Owner", "Customer", "Desk_Assistant", "Custodian"
    try {
      String query = "Select * from ";
      switch (role.toLowerCase()) {
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
      stmt.setString(1, email);
      rs = stmt.executeQuery();

      if (rs.next()) {
        String pass = rs.getString("PASSWORD");
        if (pass.equals(password)) {
          verified = true;
        } else {
          Main.errorMessage("Wrong Password");
          verified = false;
        }
      } else {
        Main.errorMessage("Email DOESNT EXIST");
        verified = false;
      }

    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return verified;
  }

  /**
   * This method is responsible for obtaining the customers name from the database using the user Id.
   *
   * @param userId The user Id of the client.
   * @return Returns the full name of the user.
   */
  //method for getting customers name
  public String nameOfTheCustomer(String userId) {
    String fullName = null;
    try {
      String query = "Select * from CUSTOMER where username = ?";
      ResultSet rs;
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, userId);
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
//#byshafi
  public List<CustomerBooking> BookingStatusForAll() {

    try {
      String query = "Select * from invoiceno ";
      ResultSet rs;
      ArrayList<CustomerBooking> paymentStatus = new ArrayList<>();
      PreparedStatement stmt = con.prepareStatement(query);
      rs = stmt.executeQuery();
      while (rs.next()) {
        int invoice = rs.getInt("Orderno");
        String fullName = rs.getString("fullname");
        String room = rs.getString("roomname");
        String date = rs.getString("DisplayDate");
        double cost = rs.getDouble("Cost");
        String paid = rs.getString("pay");
        String statusCheckin = rs.getString("CHECKINSTATUS");
        CustomerBooking cb = new CustomerBooking(invoice, fullName, room, date, cost, paid,
            statusCheckin);
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
   * This method searches for the customer username by order number and returns that value.
   *
   * @param orderNo The order number of the booking.
   * @return The username of the user.
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
   * This method is responsible for displaying the table information in
   * the customer screen.
   *
   * @param userName A string that represents the username of the client.
   * @param status The status of the payment.
   * @return The array list that contains the customer booking information.
   */
  public List<CustomerBooking> BookingStatus(String userName, String status) {
    try {

      String query = "Select * from invoiceno where username = ? AND ";
      String queryFullName = "Select * from CUSTOMER where username = ?";
      switch (userName.toLowerCase()) {
        case "all":
          query = "Select * from invoiceno ";
          break;
        default:
          break;
      }

      switch (status.toLowerCase()) {
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
      if (userName.equals("all")) {
        rs = stmt.executeQuery();
      } else {
        stmt.setString(1, userName);
        rs = stmt.executeQuery();
        stmt2.setString(1, userName);
        rsFullName = stmt2.executeQuery();

      }
      while (rs.next()) {
        int invoice = rs.getInt("Orderno");
        String fullName = rs.getString("fullname");
        String room = rs.getString("roomname");
        String date = rs.getString("DisplayDate");
        double cost = rs.getDouble("Cost");
        String paid = rs.getString("pay");
        String statusCheckin = rs.getString("CHECKINSTATUS");
        CustomerBooking cb = new CustomerBooking(invoice, fullName, room, date, cost, paid,
            statusCheckin);
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
   * This method is responsible for deleting the order from the database.
   *
   * @param orderNo An integer that represents order number of the booking.
   * @param userName A string that represents the username of the user.
   */
  public void deleteOrder(int orderNo, String userName) {
    try {
      String roomNo;
      Statement stmt = this.con.createStatement();

      ResultSet rs = null;
      rs = stmt
          .executeQuery(String.format("Select * FROM INVOICENO where orderno = '%d'", orderNo));
      while (rs.next()) {
        roomNo = rs.getString("Roomname");
        Statement stm = this.con.createStatement();
        stm.executeUpdate(String.format("Delete from invoiceno where orderno = '%d'", orderNo));
        stm.executeUpdate(String.format("Delete from %s where orderno = '%d'", userName, orderNo));
        stm.executeUpdate(String.format("Delete from %s where orderno = '%d'", roomNo, orderNo));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Password reset by owner

  /**
   * This method makes use of switch statements to reset the password in the correct table
   * of the data base by accepting the user name, role type, and the users password.
   *
   * @param userName A string that represents a users unique username.
   * @param type A string that represents a users role type.
   * @param password A string that represents a users unique password.
   */
  public void passwordReset(String userName, String type, String password) {
    try {
      String query = "UPDATE ";

      switch (type.toLowerCase()) {
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
      stmt.setString(2, userName);
      stmt.executeUpdate();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method goes into the invoice data base table and obtains the amount owed for each order number.
   *
   * @param orderNo An integer that represents the unique order number of the booking.
   * @return A double that represents amount of money owed.
   */
  public double orderNumberAmountr(int orderNo) {
    double amountOf = 0;
    try {
      Statement stmt = this.con.createStatement();

      ResultSet rs = null;
      rs = stmt
          .executeQuery(String.format("Select * FROM INVOICENO where orderno = '%d'", orderNo));
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
   * This method makes it possible to save users email to the data base by inserting the parameter value
   * into the card saved table.
   *
   * @param userName A string that represents the username.
   * @param email A string that represents the users email.
   */
  public void saveEmailCardTable(String userName, String email) {
    try {
      ResultSet rs = null;
      Statement stmt = this.con.createStatement();
      stmt.executeUpdate(
          String.format("Insert Into CARDSAVED (CARDMEMBEREMAIL, USERID) VALUES ('%s','%s')",
              email, userName));
    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
  }

  /**
   * This method makes it possible to save the users card information into the data base by inserting the parameter values
   * into the card saved table.
   *
   * @param userId A string that represents the unique user Id of the user.
   * @param cardType A string that represents the users payment card type.
   * @param cardNumber A long value that represents the card number of the user.
   * @param month An integer that represents the month of expiration of the payment card.
   * @param year An integer that represents the year of expiration of the payment card.
   * @param cvv An integer that represents the cvv code of the payment card.
   * @param zipcode An integer that represents the zip code
   */

  public void saveCardInfo(String userId, String cardType, long cardNumber, int month, int year,
      int cvv,
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
   * This method creates a row for a new booking in the invoice table.
   *
   * @param userName A string value that represents the users username.
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
   * This method is used to update the paid column in the invoice data base. This would be necessary
   * everytime a customer makes a payment for the booking.
   *
   * @param orderNO An integer that represents the order number of the booking.
   */
  public void paidColumn(int orderNO) {
    try {
      Statement stmt = this.con.createStatement();
      String paymentDone = "YES";
      ResultSet rs = null;
      stmt.executeUpdate(
          String.format("UPDATE INVOICENO SET pay = 'YES' where orderno = '%d'", orderNO));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method updates the check in status column in the invoice data base table
   * which would be necessary every time a guest checks in.
   *
   * @param orderNO An integer value that represents the order number of booking.
   * @param roomName A string that represents the room name.
   */
  public void checkedIn(int orderNO, String roomName) {
    try {
      Statement stmt = this.con.createStatement();
      String paymentDone = "YES";
      ResultSet rs = null;
      stmt.executeUpdate(String
          .format("UPDATE INVOICENO SET CHECKINSTATUS = 'YES' where orderno = '%d'", orderNO));
      stmt.executeUpdate(String
          .format("UPDATE %s SET CHECKINSTATUS = 'YES' where orderno = '%d'", roomName, orderNO));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method searches for the customers order number in the invoice data base table.
   *
   * @return The integer value of the order number is returned.
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
   * This method 'pushes' the booking credentials into the invoice data base table
   * when a booking is made by the client.
   *
   * @param userName A string value that represents the users username.
   * @param roomNo A string that represents the clients room number.
   * @param dateBooked An Array list made up of a string value that represents the dates
   *                   that the customer will be staying at the resort.
   * @param cost A double value that represents the cost of the booking.
   * @param dateToDisplay A string that represents the dates displayed on the date picker in the UI.
   * @param fullName A string that represents the full name of the user.
   * @param checkedInStatus A string that represents the check in status of the guest.
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
          roomNo, fullName, userName, dateBooked, defaultPay, cost, dateToDisplay,
          checkedInStatus));
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
   * This method is responsible for obtaining the bookings that are not paid for from the
   * invoice data base table.
   *
   * @param userName A string that represents the users username.
   * @return An array list of the room number, the date, and the cost of the booking.
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
   * This method is responsible for passing the cleaning status of the rooms available
   * into the room status data base table.
   *
   * @param room A string that represents the room number.
   * @param status A boolean value that represents the status of the room cleaning.
   * @param datePushed A string that represents the date the submission was made.
   * @param name A string that represents the name of the user.
   */
  public void roomCheckedDatabase(String room, boolean status, String datePushed, String name) {
    try {

      String update = ("UPDATE ROOMSTATUS SET (cleaned, date, MarkedBy) = (" + status + " , '"
          + datePushed + "', '" + name + "') where roomname = '" + room + "'");
      PreparedStatement stmt = con.prepareStatement(update);
      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Method for retriving the username for the payment screen with order number

  /**
   * This method is responsible for retrieving the username of the user by accepting
   * the order number.
   *
   * @param order An integer value that represents the order number of the booking.
   * @return The username of the user.
   */
  public String nameFetch(int order) {
    try {
      PreparedStatement stmt = con.prepareStatement(
          String.format("Select * FROM INVOICENO WHERE ROOMNAME = " + order));
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
   * This method is responsible for checking if the room is cleaned during the start
   * up of the program.
   *
   * @param room A string value that represents the room number.
   * @param dateToday A string value that represents the date.
   * @return If the room is checked as 'Cleaned' then it returns a true boolean value,
   * otherwise it returns a false.
   */
  public boolean roomValidationCleaned(String room, String dateToday) {
    try {
      PreparedStatement stmt = con.prepareStatement(
          String.format("Select * FROM ROOMSTATUS WHERE roomname = '%s'", room));
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        String date = rs.getString("date");
        boolean checked = rs.getBoolean("CLEANED");

        if ((date.equals(dateToday)) && (checked == true)) {
          return true;
        } else {
          return false;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  //get the current date cleaned for each room

  /**
   * This method retrieves the dates cleaned or not cleaned from the room status
   * data base table.
   *
   * @param room A string that represents the room number.
   * @return The display date is returned after pulling from the data base.
   */
  public String dateReturn(String room) {
    try {
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
   * This method is responsible for retrieving the custodians name from their respective
   * database tables.
   *
   * @param room A string value that represents the room number.
   * @return The custodians name is returned after pulling from the database.
   */
  public String custodianNameReturn(String room) {
    try {
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
   * This method is responsible for checking to see if a room was
   * cleaned by validating the date.
   *
   * @param date A string that represents the date of cleaning.
   * @throws SQLException Exception on sql error.
   */
  public void custodianDateValidation(String date) throws SQLException {
    try {
      Statement stmt = this.con.createStatement();

      stmt.executeUpdate(
          String.format("UPDATE ROOMSTATUS SET cleaned = false where date != '%s'", date));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is to push a date to the data base.
   *
   * @param userName A string that represents the user name.
   * @param roomNo A string that represents the room number.
   * @param dateBooked An array list that contains the dates that are booked.
   */
  public void pushDateToUserTable(String userName, String roomNo, ArrayList<String> dateBooked) {
    try {

      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      Statement stmt = this.con.createStatement();
      stmt.executeUpdate(
          String.format("INSERT INTO %s (orderno, roomno, daysBooked) VALUES ('%s', 12 ,'%s')",
              userName, roomNo, dateBooked));
    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }

  }

  /**
   * This method 'pushes' the date
   *
   * @param room
   * @param userName
   * @param Datetobebook
   * @return
   */
  public String pushDateToRoom(String room, String userName, ArrayList<String> Datetobebook) {
    try {

      HashMap<String, String[]> roomMatch = new HashMap<>();
      roomMatch.put("A", new String[]{"ROOMA101", "ROOMA102"});
      roomMatch.put("B", new String[]{"ROOMB103", "ROOMB104"});
      roomMatch.put("C", new String[]{"ROOMC105", "ROOMC106"});
      roomMatch.put("D", new String[]{"ROOMD107", "ROOMD108"});
      boolean matchfound = false;

      String[] roomListSelected = roomMatch.get(room);
      roomLoop:
      for (String roomName : roomListSelected) {

        Statement stmt = this.con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT DATEBOOKED FROM " + roomName);

        ArrayList<String> roomAlreadyBooked = new ArrayList<>();
        while (rs.next()) {
          String bookedListed = rs.getString("DateBooked");
          roomAlreadyBooked.addAll(parseString(bookedListed));
        }
        for (String day : Datetobebook) {
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
   * This method is used to handle the error messages that appears
   * for all SQL exception errors.
   *
   * @param error An object of the SQLException class.
   */

  public void sqlExceptionHandler(SQLException error) {

    error.getMessage();
  }
}