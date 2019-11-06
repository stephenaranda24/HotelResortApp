package sample;

import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.xml.soap.Text;

public class ClientScreenController implements Initializable {

  @FXML
  private TextField startDat;

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
  private ComboBox<String> roomSelectCombo;

  public void initialize(URL url, ResourceBundle resources) {
    String userEmail = null;
    try {
      DatabaseManager db = new DatabaseManager();
      userEmail = db.getTheName(SignInController.username);

    } catch (SQLException e) {
      e.printStackTrace();
    }


    idSpace1.setText(userEmail);
    setRoomSelectCombo();
  }
  private void setRoomSelectCombo() {
    roomSelectCombo.setPromptText("Select a room.");
    roomSelectCombo.getItems().addAll("A", "B", "C", "D");
  }

  @FXML
    void submitDate(ActionEvent event) throws SQLException {
    MainScreenController msc = new MainScreenController();
    String gettId = idSpace1.getText();
    System.out.println("I got the id as " +gettId);

    String startDate = startDat.getText();
    String startMonths = startMonth.getText();
    String endDatee = endDate.getText();
    String endMonths = endMonth.getText();
    String room = roomSelectCombo.getValue();

    String dateMonthStart = String.format("%s/%s/2019",startMonths,startDate);
    String dateMonthend = String.format("%s/%s/2019",endMonths,endDatee);
    System.out.println(dateMonthStart);

   /* Format f = new SimpleDateFormat("MM/dd/yyyy");
    String strDate = f.format(dateMonthStart);
    System.out.println(strDate + " Alpha");*/
    Date startBookingDate = new Date(dateMonthStart);
    System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(startBookingDate));
    Date endBookingDate = new Date(dateMonthend);
    long totalDaysBooked = endBookingDate.getTime() - startBookingDate.getTime();
    totalDaysBooked = (long) Math.ceil((double)totalDaysBooked/86400000);
    String dateBookedToDisplay = startBookingDate + " - " +endBookingDate;
    System.out.println(dateBookedToDisplay);
    System.out.println("result sdfjosaekjfkjdskfndsjfndskjlfnkjdskjfbsdkjfsdkjfnkndsjllkjfns   " + totalDaysBooked);
    ArrayList<String> dateList = new ArrayList<>();
    for(int i =0; i<totalDaysBooked;i++){
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

      cal.setTime(startBookingDate);

      cal.add(Calendar.DAY_OF_MONTH, i);
      Date addDate = cal.getTime();
      System.out.println(addDate);
      String finalDateformat = sdf.format(addDate);
      System.out.println(finalDateformat );
      dateList.add(finalDateformat);
    }

    DatabaseManager db = new DatabaseManager();

    String result = db.pushDateToRoom(room,gettId,dateList);

    if (result != null){
      System.out.println(result+" Looking for result");

      db.pushDate(gettId,result,dateList);

    }
    else{
      System.out.println("Sorry rooms you selected are not available for those dates");
    }


    System.out.println(dateList);



    /*try {

      Date date = formatter.parse(dateMonthStart);
      System.out.println(date+ "Alpha");
      String enDate = "02/03/2019";
      Date endDate = formatter.parse(enDate);
      System.out.println(endDate+"test v2");
      System.out.println(formatter.format(date)+"test");
      long totalDaysBooked = endDate.getTime() - date.getTime();
      System.out.println(totalDaysBooked/86400000+"   020285555");

    } catch (ParseException e) {
      e.printStackTrace();
    }*/
  }



}
