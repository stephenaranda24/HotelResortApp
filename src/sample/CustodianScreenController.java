/*
package sample;

import java.time.LocalDate;

public class CustodianScreenController {
  LocalDate todaysDate = LocalDate.now();
  String datePushed = String.valueOf(todaysDate);

  boolean checked =


}
*/
package sample;

import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;


public class CustodianScreenController implements Initializable {
  String [] roomArray = {"ROOMA101","ROOMA102","ROOMB103","ROOMB104","ROOMC105",
      "ROOMC106","ROOMD107","ROOMD108"};
  LocalDate todaysDate = LocalDate.now();
  String datePushed = String.valueOf(todaysDate);


  @FXML
  private Button custodianSubmit;

  @FXML
  private CheckBox roomA101;

  @FXML
  private CheckBox roomA102;

  @FXML
  private CheckBox roomB103;

  @FXML
  private CheckBox roomB104;

  @FXML
  private CheckBox roomC105;

  @FXML
  private CheckBox roomC106;

  @FXML
  private CheckBox roomD107;

  @FXML
  private CheckBox roomD108;

  @FXML
  void custodianSubmit(ActionEvent event) {
    try{
      DatabaseManager db = new DatabaseManager();
      CheckBox [] roomCheckBox = {roomA101,roomA102, roomB103, roomB104, roomC105, roomC106, roomD107, roomD108};
      for (int i = 0; i < roomCheckBox.length; i++){
        boolean selectedClean = roomCheckBox[i].isSelected();
        if(selectedClean){
          db.roomCheckedDatabase(roomArray[i],selectedClean,datePushed);
        }
        else{
          String date = db.dateReturn(roomArray[i]);
          db.roomCheckedDatabase(roomArray[i],!selectedClean,date);

        }


      }

    } catch (Exception e) {
      e.printStackTrace();
    }




  }
  @Override
  public void initialize(URL url, ResourceBundle resources) {


    try {
      DatabaseManager db = new DatabaseManager();
      db.custodianDateValidation(datePushed);

      CheckBox [] roomCheckBox = {roomA101,roomA102, roomB103, roomB104, roomC105, roomC106, roomD107, roomD108};
      for (int i = 0; i < roomArray.length; i++){
        boolean checked = db.roomValidationCleaned(roomArray[i],datePushed);
        if(checked == true){
          roomCheckBox[i].setSelected(true);
        }
        else{
          roomCheckBox[i].setSelected(false);
        }



      }

    } catch (SQLException e) {
      e.printStackTrace();
    }




  }

}
