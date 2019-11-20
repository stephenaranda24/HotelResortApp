package sample;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DateAndCostManager {
  final double TAXRATE = 0.065;
  public void setRoomSelectCombo(ComboBox<String> roomSelectCombo) {
    roomSelectCombo.setPromptText("Select a room.");
    roomSelectCombo.getItems().addAll("A", "B", "C", "D");
  }
  public List<Serializable> dateCalc(ComboBox<String> roomSelectCombo, TextField startDate, TextField startMonth, TextField endDate,TextField endMonth ){


    String SartookingDate = startDate.getText();
    String startBookingMonths = startMonth.getText();
    String endBookingDate = endDate.getText();
    String endBookingMonths = endMonth.getText();
    String room = roomSelectCombo.getValue();


    String dateMonthStart = String.format("%s/%s/2019", startBookingMonths, SartookingDate);
    String dateMonthend = String.format("%s/%s/2019", endBookingMonths, endBookingDate);
    System.out.println(dateMonthStart);

  /* Format f = new SimpleDateFormat("MM/dd/yyyy");
   String strDate = f.format(dateMonthStart);
   System.out.println(strDate + " Alpha");*/
    Date startBookingFDate = new Date(dateMonthStart);
    System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(startBookingFDate));
    Date endBookingFDate = new Date(dateMonthend);
    long totalDaysBooked = endBookingFDate.getTime() - startBookingFDate.getTime();
    totalDaysBooked = (long) Math.ceil((double) totalDaysBooked / 86400000);
    String dateBookedToDisplay = startBookingFDate + " - " + endBookingFDate;
    System.out.println(dateBookedToDisplay);
    double roomsCost = PricePerNight.valueOf(room).getValue();
    Double bookingCost = totalDaysBooked*roomsCost + (totalDaysBooked*roomsCost*TAXRATE);
    ArrayList<String> dateList = new ArrayList<>();
    for(int i =0; i<totalDaysBooked;i++){
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

      cal.setTime(startBookingFDate);

      cal.add(Calendar.DAY_OF_MONTH, i);
      Date addDate = cal.getTime();
      System.out.println(addDate);
      String finalDateformat = sdf.format(addDate);
      System.out.println(finalDateformat );
      dateList.add(finalDateformat);
    }
    String dateToDisplay = String.format(dateMonthStart) + " - " + String.format(dateMonthend);





    return Arrays.asList(totalDaysBooked,startBookingFDate,endBookingFDate,bookingCost,dateList,dateToDisplay);
  }


}
