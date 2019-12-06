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
import javafx.scene.control.Label;

/**
 * This client controller class implements all actions done on the client screen
 *
 * @version 1.0
 * @ Romanov Andre
 * @ Shafi Mushfique
 * @ Stephen Aranda
 * @since 2019-09-21
 */
public class CustodianScreenController implements Initializable {

  String[] roomArray = {"ROOMA101", "ROOMA102", "ROOMB103", "ROOMB104", "ROOMC105",
      "ROOMC106", "ROOMD107", "ROOMD108"};
  LocalDate todaysDate = LocalDate.now();
  String datePushed = String.valueOf(todaysDate);
  @FXML
  private Button logoutButton;

  @FXML
  private Label userId;

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
  public static String custodianUserId;

  /**
   * This method is used for the the client to be able to log out of their account and returns to
   * the main screen.
   *
   * @param event This is an object of the ActionEvent class.
   */
  @FXML
  void logOut(ActionEvent event) {
    Main.loggedInUser = null;
    MainScreenController msc = new MainScreenController();
    msc.loadScene(logoutButton, "MainScreenSample.fxml", "Main Screen");

  }

  /**
   * This method handles the functionality of the button that is pressed for the custodian when they
   * want to submit whether the room is clean or not.
   *
   * @param event This is an object of the ActionEvent class.
   */
  @FXML
  void custodianSubmit(ActionEvent event) {
    try {
      DatabaseManager db = new DatabaseManager();
      CheckBox[] roomCheckBox = {roomA101, roomA102, roomB103, roomB104, roomC105, roomC106,
          roomD107, roomD108};
      for (int i = 0; i < roomCheckBox.length; i++) {
        boolean selectedClean = roomCheckBox[i].isSelected();
        if (selectedClean) {
          db.roomCheckedDatabase(roomArray[i], selectedClean, datePushed, custodianUserId);

        } else {
          String date = db.dateReturn(roomArray[i]);
          String markedBy = db.custodianNameReturn(roomArray[i]);
          db.roomCheckedDatabase(roomArray[i], selectedClean, date, markedBy);


        }


      }

    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL url, ResourceBundle resources) {

    try {
      DatabaseManager db = new DatabaseManager();
      custodianUserId = db.getTheName(Main.loggedInUser, "custodian");
      userId.setText(custodianUserId);
      db.custodianDateValidation(datePushed);

      CheckBox[] roomCheckBox = {roomA101, roomA102, roomB103, roomB104, roomC105, roomC106,
          roomD107, roomD108};
      for (int i = 0; i < roomArray.length; i++) {
        boolean checked = db.roomValidationCleaned(roomArray[i], datePushed);
        if (checked == true) {
          roomCheckBox[i].setSelected(true);
        } else {
          roomCheckBox[i].setSelected(false);
        }


      }

    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

}
