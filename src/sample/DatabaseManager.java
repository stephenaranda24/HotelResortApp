package sample;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager extends Main {

  private Connection con = null;

  //final String JDBC_DRIVER = "org.h2.Driver";
  public DatabaseManager() throws SQLException {

    this.con = DriverManager
        .getConnection("jdbc:h2:C:\\Users\\shafi\\IdeaProjects\\HotelResortApplication\\res\\ResortData");

  }
  public void AddCustomer( String userName, String password ,String customerEmail) {
    try {

      Statement stmt = this.con.createStatement();
      stmt.executeUpdate(String.format("INSERT INTO CUSTOMER (USERNAME, PASSWORD, EMAIL) VALUES ('%s' ,'%s','%s')", userName, password
      ,customerEmail));
      this.con.close();


    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }

  }
  public void LogInAccount( String userName, String password ,String role) {
    try {
      ResultSet rs = null;

      Statement stmt = this.con.createStatement();
      rs = stmt.executeQuery(String.format("SELECT * FROM  %s WHERE USERNAME = '%s'", role, userName));
      if(rs.next()){

        System.out.println("usimg this name:" + password);
        String pass = rs.getString("PASSWORD");
        System.out.println(pass);

        if (pass.equals(password)){
          System.out.println("Successfull");

        }
        else{
          System.out.println("Wrong Password");
        }

      }
      else{
        System.out.println("USERNAME DOESNT EXIST");


      }
      this.con.close();


    } catch (SQLException var6) {
      this.sqlExceptionHandler(var6);
    }

  }


  public void sqlExceptionHandler(SQLException error) {

    System.out.println("Standard Failure: " + error.getMessage());
  }
}