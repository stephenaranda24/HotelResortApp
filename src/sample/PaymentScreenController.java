package sample;

import com.sun.org.apache.xpath.internal.objects.XBoolean;
import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.smartcardio.Card;
import javax.xml.soap.Text;

public class PaymentScreenController {

  @FXML
  private Button backFrompayment;

  @FXML
  private Button submitPayment;
  @FXML
  private Label idSpace;

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
  private Label orderNo;

  @FXML
  private Text label;


  int finalCardYear = 0;
  int finalCardMonth = 0;
  String finalCardType = null;
  long finalCardNum = 0;
  int finalCvv = 0;
  int finalZipCode = 0;
  String userID = null;


  @FXML
  void backFrompaymentAction(ActionEvent event) {

  }



  public void initialize() {
    MainScreenController msc = new MainScreenController();

    setComboBoxText();
    userID= ClientScreenController.userId;



    idSpace.setText(userID);
    orderNo.setText(String.valueOf(ClientScreenController.orderNo));

  }



  private void setComboBoxText() {
    cardType.setPromptText("Select a type of card.");
    cardType.getItems().addAll("VISA", "MasterCard", "Discover", "Amex");
  }

  @FXML
  void submitPaymentSuccessfull(ActionEvent event) throws SQLException {
    boolean cardWorked = false;
    MainScreenController msc = new MainScreenController();
    String tempCardSelection = cardType.getValue();
    System.out.println("Card escalated "+ tempCardSelection);
    String tempCardNumber = cardNumber.getText();
    System.out.println("card number "  +tempCardNumber);
    DatabaseManager dm = new DatabaseManager();
    String tempCVV = cardCvv.getText();
    String tempZipCode = bilingZipCode.getText();
    String tempGetMonth = expMonth.getText();
    String tempGetYear = expYear.getText();
    String tempYear = String.format("20%s",tempGetYear);
    long cardNumber = Long.parseLong(tempCardNumber);
    int cvv = Integer.parseInt(tempCVV);
    int zipCode = Integer.parseInt(tempZipCode);
    int expYear = Integer.parseInt(tempYear);
    int expMonth = Integer.parseInt(tempGetMonth);
    boolean dateMonthConditionMet = monthAndYearValidation(expMonth,expYear);
    if (dateMonthConditionMet == true){
      System.out.println("Pushing the datemet condition");
      List <String>newList = cardNumber(tempCardSelection,tempCardNumber,tempCVV,tempZipCode);
      finalCardNum =Long.parseLong(newList.get(1));
      finalCvv = Integer.parseInt(newList.get(2));
      finalCardType = newList.get(0);
      finalZipCode = Integer.parseInt(newList.get(3));
    }

    dm.paidColumn(ClientScreenController.orderNo);

    boolean saveCardToFile = false;

    saveCardToFile = saveCardValidation();
    if (saveCardToFile == true){
      System.out.println("The tick mark pressed ");
      dm.saveCardInfo(userID,finalCardType,finalCardNum,finalCardMonth,finalCardYear,finalCvv,finalZipCode);
      System.out.println("Card Saved123");
    }
    else {
      System.out.println("Not Saved");

    }
    msc.loadScene(submitPayment,"ClientScreen.fxml","clientScreen");


  }
  public List<String> cardNumber(String cardSelection, String valCardNumber, String cvv, String zipCode){


    int lengthOfCard = valCardNumber.length();
    int lengthCvv = cvv.length();
    int lengthZipCOde = zipCode.length();

    String cardNumberR = null;
    String cvcR = null;
    String zipCodeR = null;


    if (cardSelection.equals("Amex")){
      if(lengthOfCard == 15 && (lengthCvv == 4) && (lengthZipCOde == 5)  )

      {//begin of 15 long credit card check
        System.out.println("Card :-)");
        cardNumberR = valCardNumber;
        cvcR = cvv;
        zipCodeR = zipCode;
      }//end of 15 long credit card check
      else{
        System.out.println("Please retype again");
      }
    }
    else{
      if(lengthOfCard == 16 && (lengthCvv == 3) && (lengthZipCOde == 5) )
      {
        System.out.println("Card :-)");
        System.out.println("Card :-)");
        cardNumberR = valCardNumber;
        cvcR = cvv;
        zipCodeR = zipCode;

      }//end of 15 long credit card check
      else{
        System.out.println("Please retype again");
      }
    }
    return Arrays.asList(cardSelection, cardNumberR,cvcR, zipCodeR);
    }


    //https://www.dreamincode.net/forums/topic/269950-finding-the-length-of-a-long-data-type/

  public boolean monthAndYearValidation(int month, int year){
    boolean yearMonthmet = false;
    int presentYear = Calendar.getInstance().get(Calendar.YEAR);
    if(year>presentYear && month>0 && month<13){
      finalCardMonth = month;
      finalCardYear = year;
      yearMonthmet = true;
      System.out.println("Year okkk");
    }
    else if(presentYear == year){
      int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
      if(month >= currentMonth){
        finalCardMonth = month;
        finalCardYear = year;
        System.out.println("Year okkk");
        yearMonthmet = true;
      }
      else{
        System.out.println("expired card");
      }
    }
    else{
      System.out.println("expired card");
    }
    return yearMonthmet;
  }
  public boolean saveCardValidation(){
    return saveCard.isSelected();
  }




}
