package sample;


import com.sun.xml.internal.fastinfoset.util.StringArray;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import org.h2.command.Prepared;

public class DatabaseManager extends Main {

  private Connection con = null;
  String verified = null;


  //final String JDBC_DRIVER = "org.h2.Driver";
  public DatabaseManager() throws SQLException {

    this.con = DriverManager
        .getConnection("jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApp\\res\\ResortData");

  }
  public void startDatabase(String userName, String password ,String role) {
    LogInAccount(userName, password, role);

  }
  public ArrayList<String> parseString(String stringlist){
    ArrayList<String> result = new ArrayList<>();
    for(String token: stringlist.substring(1,stringlist.length()-1).split(", ")){
      result.add(token);
    }
    return result;


  }
  public String getTheName(String email){
    String id = null;
    try {
      ResultSet rs = null;

      Statement stmt = this.con.createStatement();

      rs = stmt.executeQuery(String.format("SELECT * FROM CUSTOMER WHERE EMAIL = '%s'",email));
      if(rs.next()) {

        System.out.println("usimg this name:" + email);
        id = rs.getString("USERNAME");
        System.out.println(id);

      }




    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return id;




  }
  public boolean AddCustomer(String fullName, String userName, String email, String phonenumber,
      String password , int verifyPin, String address,String city, String state, int zipcode ,String country ) {
    boolean nameDoesntExisted = false;
    try {

      Statement stmt = this.con.createStatement();
      ResultSet rs = null;
      ResultSet rss = null;
      rs = stmt.executeQuery(String.format("SELECT * FROM CUSTOMER WHERE USERNAME = '%s'",userName));
      rss = stmt.executeQuery(String.format("SELECT * FROM CUSTOMER WHERE email = '%s'",email));
      if(rss.next()){
        String emailExist = rss.getString("email");
        nameDoesntExisted = false;

      }
      else if(rs.next()){

        String userNameExisted = rs.getString("USERNAME");
        System.out.println("it exist");
        nameDoesntExisted = false;

      }
      else {

        stmt.executeUpdate(String.format(
            "INSERT INTO CUSTOMER (FULLNAME, USERNAME, email, phonenumber, password ,verifyPin, address,city, state, zipcode ,country ) VALUES ('%s' ,'%s','%s', '%s','%s','%d','%s', '%s','%s','%d','%s')",
             fullName,  userName,  email,  phonenumber,
             password ,  verifyPin,  address, city,  state,  zipcode , country ));
        System.out.println("HEy it pushed");
        nameDoesntExisted= true;
      }


    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return nameDoesntExisted;

  }
  public boolean LogInAccount( String email, String password ,String role) {
    Boolean verified = false;
    try {
      ResultSet rs = null;

      Statement stmt = this.con.createStatement();

      rs = stmt.executeQuery(String.format("SELECT * FROM  %s WHERE EMAIL = '%s'", role, email));
      if(rs.next()){

        System.out.println("usimg this name:" + password);
        String pass = rs.getString("PASSWORD");
        System.out.println(pass);

        if (pass.equals(password)){
          System.out.println("Successfull");
          verified = true;

        }
        else{
          System.out.println("Wrong Password");
          verified = false;
        }

      }
      else{
        System.out.println("Email DOESNT EXIST");
        verified = false;
      }

    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return verified;

  }
  public void saveEmailCardTable(String userName, String email){

    try {
      ResultSet rs = null;

      Statement stmt = this.con.createStatement();

      stmt.executeUpdate(String.format("Insert Into CARDSAVED(CARDMEMBEREMAIL) VALUES ('%s')",email));




    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }




  }

  public void createCustomerTable(String userName){
    try {
      System.out.println("SOB");
      String lol = userName;
      System.out.println(lol);

      Statement stmt = this.con.createStatement();
      System.out.println("Creating table in given database...");

      String sql = "CREATE TABLE "+userName +
          "(ORDERNO INTEGER , " +
          " ROOMNO VARCHAR(255), " +
          " DaysBooked VARCHAR(4555), "+
          " DISPLAYDATE VARCHAR(255), " +
          "COST DOUBLE ) ";
      stmt.executeUpdate(sql);


      System.out.println("table created");



    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }

  }
  public void pushDate(String userName, String roomNo, ArrayList<String> dateBooked){
    try {
      int orderNo = 0;

      long timeStamp = System.currentTimeMillis();

      Statement stmt = this.con.createStatement();
      stmt.executeUpdate(String.format("INSERT INTO INVOICENO (roomname, username, dateBooked) VALUES ('%s','%s','%s')",roomNo,userName,dateBooked));
/*
      ResultSet rs = stmt.executeQuery(String.format("Select ORDERNO FROM INVOICENO"));

*/

      ResultSet rs = stmt.executeQuery(String.format("Select MAX(ORDERNO)  FROM INVOICENO"));

      System.out.println(rs + "2222");

      while(rs.next()){
        String orderNo1 = rs.getString(1);
        orderNo = Integer.parseInt(orderNo1);
        System.out.println("HAPPYYYY " + orderNo);

        System.out.println(orderNo+ " I finally pushed the order no");


      }
      System.out.println(orderNo+ " I finally pushed the order no");

      stmt.executeUpdate(String.format("INSERT INTO %s (orderno, username, dateBooked) VALUES ('%s','%s','%s')",roomNo,orderNo,userName,dateBooked));
      stmt.executeUpdate(String.format("INSERT INTO %s (orderno, roomno, daysBooked) VALUES ('%d', '%s' ,'%s')",userName,orderNo, roomNo,dateBooked));


    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }

  }
  public void pushDateToUserTable(String userName, String roomNo, ArrayList<String> dateBooked){
    try {

      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      Statement stmt = this.con.createStatement();
      stmt.executeUpdate(String.format("INSERT INTO %s (orderno, roomno, daysBooked) VALUES ('%s', 12 ,'%s')",userName, roomNo,dateBooked));
    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }

  }
  public String pushDateToRoom(String room, String userName, ArrayList<String> Datetobebook){
    try {
/*
      PreparedStatement stmt = this.con.prepareStatement("SELECT DATEBOOKED FROM ?");
*/
      HashMap<String, String[]> roomMatch = new HashMap<>();
      roomMatch.put("A",new String[]{"ROOMA101", "ROOMA102"});
      roomMatch.put("B",new String[]{"ROOMB103", "ROOMB104"});
      roomMatch.put("C",new String[]{"ROOMC105", "ROOMC106"});
      roomMatch.put("D",new String[]{"ROOMD107", "ROOMD108"});
      boolean matchfound = false;

        String [] roomListSelected  = roomMatch.get(room);
        roomLoop:
        for(String roomName: roomListSelected){
          /*stmt.setString(1,roomName);
          ResultSet rs = stmt.executeQuery();*/
          Statement stmt = this.con.createStatement();
          System.out.println("JARED LOOKING FOR ROOM NAME " + roomName + " else " + room);
          ResultSet rs = stmt.executeQuery("SELECT DATEBOOKED FROM " + roomName);

          ArrayList <String> roomAlreadyBooked = new ArrayList<>();
          while(rs.next()){
            String bookedListed = rs.getString("DateBooked");
            roomAlreadyBooked.addAll(parseString(bookedListed));
          }
          for(String day : Datetobebook)
          //for(int i =0 ; i<datetobebooked.size(); i++){   }
          {
           if(roomAlreadyBooked.contains(day)) {
             continue roomLoop;
           }
          }
          /*pushDate(userName, roomName, Datetobebook);*/
          return roomName;
        }

    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }
    return null;

  }


  public void sqlExceptionHandler(SQLException error) {

    System.out.println("Standard Failure: " + error.getMessage());
  }
}