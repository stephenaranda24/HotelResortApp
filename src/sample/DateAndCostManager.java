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

  /**
   * Setter for property 'roomSelectCombo'.
   *
   * @param roomSelectCombo Value to set for property 'roomSelectCombo'.
   */
  public void setRoomSelectCombo(ComboBox<String> roomSelectCombo) {
    roomSelectCombo.setPromptText("Select a room.");
    roomSelectCombo.getItems().addAll("A", "B", "C", "D");
  }
  /**
   * Setter for property 'dateCombox30'.
   *
   * @param combobox Value to set for property 'dateCombox30'.
   */
  public void setDateCombox30(ComboBox<String> combobox){
    combobox.setPromptText("Date");
    combobox.getItems().addAll("1", "2", "3", "4","5","6","7","8","9","10",
        "11", "12", "13", "14","15","16","17","18","19","20",
        "21", "22", "23", "24","25","26","27","28","29","30");

  }
  /**
   * Setter for property 'dateCombox31'.
   *
   * @param combobox Value to set for property 'dateCombox31'.
   */
  public void setDateCombox31(ComboBox<String> combobox){
    combobox.setPromptText("Date");
    combobox.getItems().addAll("1", "2", "3", "4","5","6","7","8","9","10",
        "11", "12", "13", "14","15","16","17","18","19","20",
        "21", "22", "23", "24","25","26","27","28","29","30");

  }



  public List<Serializable> dateCalc(ComboBox<String> roomSelectCombo, String startDate,
      String ending) {
    String room = roomSelectCombo.getValue();
    startDate = String.format(startDate);
    ending = String.format(ending);
    Date startBookingFDate = new Date(startDate);
    Date endBookingFDate = new Date(ending);
    long totalDaysBooked = endBookingFDate.getTime() - startBookingFDate.getTime();
    totalDaysBooked = (long) Math.ceil((double) totalDaysBooked / 86400000);
    if (totalDaysBooked < 1) {
      Main.errorMessage("Check Out date must be greater than check in date");
    } else {
      String dateBookedToDisplay = startBookingFDate + " - " + endBookingFDate;
      double roomsCost = PricePerNight.valueOf(room).getValue();
      Double bookingCost = totalDaysBooked * roomsCost + (totalDaysBooked * roomsCost * TAXRATE);
      bookingCost = Math.round(bookingCost * 100) / 100.00;
      ArrayList<String> dateList = new ArrayList<>();
      for (int i = 0; i < totalDaysBooked; i++) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        cal.setTime(startBookingFDate);
        cal.add(Calendar.DAY_OF_MONTH, i);
        Date addDate = cal.getTime();
        String finalDateformat = sdf.format(addDate);
        dateList.add(finalDateformat);
      }
      String dateToDisplay = String.format(startDate) + " - " + String.format(ending);

      return Arrays
          .asList(totalDaysBooked, startBookingFDate, endBookingFDate, bookingCost, dateList,
              dateToDisplay);
    }
    return  Arrays
        .asList("null");
  }

}
