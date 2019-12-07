package sample;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * This class helps to validate the room booking info, taxrate calculation date validation
 * @version 1.0
 * @author Romanov Andre
 * @author Shafi Mushfique
 * @author Stephen Aranda
 * @since 2019-09-21
 */
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
   *
   * @param roomSelectCombo get the combobox selection from client screen
   * @param startDate get the start fate from date picker
   * @param ending get the end date from date picker
   * @return an array with booking information to save it to the database
   */

  public List<Serializable> dateCalc(ComboBox<String> roomSelectCombo, String startDate,
      String ending) {
    String room = roomSelectCombo.getValue();
    startDate = String.format(startDate);
    LocalDate todaysDate = LocalDate.now();
    java.util.Date verifyDate = java.sql.Date.valueOf(todaysDate);

    ending = String.format(ending);
    Date startBookingFDate = new Date(startDate);
    Date endBookingFDate = new Date(ending);
    long datesInLong = startBookingFDate.getTime() - verifyDate.getTime();
    datesInLong = (long) Math.ceil((double) datesInLong / 86400000);

    if (datesInLong >= 0) {

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
    } else {
      Main.errorMessage("The booking date must be greater then " + todaysDate);
    }
    return Arrays
        .asList("null");
  }

}
