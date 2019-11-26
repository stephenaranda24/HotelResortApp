package sample;

import java.io.Serializable;
import java.text.Format;
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
  public void setDateCombox30(ComboBox<String> combobox){
    combobox.setPromptText("Date");
    combobox.getItems().addAll("1", "2", "3", "4","5","6","7","8","9","10",
        "11", "12", "13", "14","15","16","17","18","19","20",
        "21", "22", "23", "24","25","26","27","28","29","30");

  }
  public void setDateCombox31(ComboBox<String> combobox){
    combobox.setPromptText("Date");
    combobox.getItems().addAll("1", "2", "3", "4","5","6","7","8","9","10",
        "11", "12", "13", "14","15","16","17","18","19","20",
        "21", "22", "23", "24","25","26","27","28","29","30");

  }



  public List<Serializable> dateCalc(ComboBox<String> roomSelectCombo, String startDate,
      String ending) {

    /*String SartookingDate = startDate.getText();
    String startBookingMonths = startMonth.getText();
    String endBookingDate = endDate.getText();
    String endBookingMonths = endMonth.getText();*/
    String room = roomSelectCombo.getValue();

    /*String dateMonthStart = String.format("%s/%s/2019", startBookingMonths, SartookingDate);
    String dateMonthend = String.format("%s/%s/2019", endBookingMonths, endBookingDate);*/
   /* System.out.println(dateMonthStart);*/
    startDate = String.format(startDate);
    ending = String.format(ending);

   /*Format f = new SimpleDateFormat("MM/dd/yyyy");
   String strDate = f.format(startDate);
   System.out.println(strDate + " Alpha");*/
    Date startBookingFDate = new Date(startDate);
    /*System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(ending));*/
    Date endBookingFDate = new Date(ending);
    long totalDaysBooked = endBookingFDate.getTime() - startBookingFDate.getTime();
    totalDaysBooked = (long) Math.ceil((double) totalDaysBooked / 86400000);
    if((totalDaysBooked >= 1) && (roomSelectCombo != null)){
      String dateBookedToDisplay = startBookingFDate + " - " + endBookingFDate;
      System.out.println(dateBookedToDisplay);
      double roomsCost = PricePerNight.valueOf(room).getValue();
      Double bookingCost = totalDaysBooked * roomsCost + (totalDaysBooked * roomsCost * TAXRATE);
      ArrayList<String> dateList = new ArrayList<>();
      for (int i = 0; i < totalDaysBooked; i++) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        cal.setTime(startBookingFDate);

        cal.add(Calendar.DAY_OF_MONTH, i);
        Date addDate = cal.getTime();
        System.out.println(addDate);
        String finalDateformat = sdf.format(addDate);
        System.out.println(finalDateformat);
        dateList.add(finalDateformat);
      }
      String dateToDisplay = String.format(startDate) + " - " + String.format(ending);

      return Arrays.asList(totalDaysBooked, startBookingFDate, endBookingFDate, bookingCost, dateList,
          dateToDisplay);

    }
    else {
      Main.errorMessage("The date selection is not correct or you may have forgot to select the room");
    }
    return null;

  }


}
