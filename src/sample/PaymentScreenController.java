package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.smartcardio.Card;

public class PaymentScreenController {

  @FXML
  private Button backFrompayment;

  @FXML
  private Button submitPayment;

  @FXML
  private CheckBox saveCard;

  @FXML
  private TextField cardNumber;

  @FXML
  private TextField expMonth;

  @FXML
  private TextField expYear;

  @FXML
  private TextField cardCvv;

  @FXML
  private TextField bilingZipCode;

  @FXML
  private ComboBox<String> cardType;

  @FXML
  private Label idSpace;

  @FXML
  void backFrompaymentAction(ActionEvent event) {

  }



  public void initialize(URL url, ResourceBundle resources) {

    String userEmail = null;
    try {
      DatabaseManager db = new DatabaseManager();
      userEmail = db.getTheName(ClientScreenController.username);

    } catch (SQLException e) {
      e.printStackTrace();
    }


    idSpace.setText(userEmail);
    setComboBoxText();
  }



  private void setComboBoxText() {
    cardType.setPromptText("Select a type of card.");
    cardType.getItems().addAll("VISA", "MasterCard", "Discover", "Amex");
  }
  @FXML
  public boolean submitPaymentSuccessfull(ActionEvent event) {
    boolean cardWorked = false;
    String tempCardNumber = cardNumber.getText();

    String tempCVV = cardCvv.getText();
    String tempZipCode = bilingZipCode.getText();
    String tempGetMonth = expMonth.getText();
    String tempGetYear = expYear.getText();
    long cardNumber = Long.parseLong(tempCardNumber);
    int cvv = Integer.parseInt(tempCVV);
    int zipCode = Integer.parseInt(tempZipCode);
    int expYear = Integer.parseInt(tempGetYear);
    int expMont = Integer.parseInt(tempGetMonth);


    String cardSelection = cardType.getValue();
    if (cardSelection.equals("Amex")){
      if(cardNumber <= ((999999999 * 1000000)+999999) && (cardNumber >= (1000000000 * 100000)))

      {//begin of 15 long credit card check
        System.out.println("Card :-)");
        cardWorked = true;

      }//end of 15 long credit card check
      else{
        System.out.println("Please retype again");
      }

    }
    else{
      if(cardNumber <= ((999999999 * 10000000)+9999999) && (cardNumber >= (1000000000 * 1000000)))

      {//begin of 15 long credit card check
        System.out.println("Card :-)");
        cardWorked = true;

      }//end of 15 long credit card check
      else{
        System.out.println("Please retype again");
      }


    }
    return cardWorked;
    //https://www.dreamincode.net/forums/topic/269950-finding-the-length-of-a-long-data-type/




  }


}
