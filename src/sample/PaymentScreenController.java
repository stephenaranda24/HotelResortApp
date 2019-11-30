package sample;

import java.lang.reflect.Type;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javax.xml.soap.Text;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *Payment Controller class
 */
public class PaymentScreenController implements Initializable {

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
  private CheckBox savedMethod;

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

  @FXML
  private Button payLater;
  @FXML
  private TextField amount;
  public String userfxmTitile;


  int finalCardYear = 0;
  int finalCardMonth = 0;
  String finalCardType = null;
  long finalCardNum = 0;
  int finalCvv = 0;
  int finalZipCode = 0;
  String userID = null;


  /**
   * This method allows to go back the logged in user account
   * @param event
   */
  @FXML
  void backFrompaymentAction(ActionEvent event) {
    {
      MainScreenController msc = new MainScreenController();
      msc.loadScene(backFrompayment,userfxmTitile+".fxml",userfxmTitile);
    }


  }

  /** {@inheritDoc}
   * Initilize
   * */
  public void initialize(URL url, ResourceBundle resources) {
    userfxmTitile = Main.Type;
    MainScreenController msc = new MainScreenController();
    setComboBoxText();
    userID = Main.tempName;
    idSpace.setText(userID);
    orderNo.setText(String.valueOf(ClientScreenController.orderNo));
    double amounts = 0;
    try {
      DatabaseManager db = new DatabaseManager();
      String orderNO = orderNo.getText();
      int orderNo1 = Integer.parseInt(orderNO);
      amounts= db.orderNumberAmountr(orderNo1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    String displayAmount = (String.valueOf(amounts));
    amount.setText("$"+displayAmount);

  }


  /**
   * Combo Box for selecting the card type
   */
  private void setComboBoxText() {
    cardType.setPromptText("Select a type of card.");
    cardType.getItems().addAll("VISA", "MasterCard", "Discover", "Amex");
  }

  /**
   * Pop
   * @param event
   */
  @FXML
  void payLaterAction(ActionEvent event) {
    MainScreenController msc = new MainScreenController();
    Main.infoMessage("You can re-login any time and make the payment by selecting from the unpaid table screen"
        + "\nAlso you can pay before you in front desk before you check in"
        + "\nThank you for choosing our Resort");
    msc.loadScene(payLater,userfxmTitile+".fxml",userfxmTitile);
  }
  @FXML
  void savedMethodChecked(ActionEvent event) {
    try {
      DatabaseManager db = new DatabaseManager();
      String userId = idSpace.getText();
      System.out.println(userId);
      List cardList = db.cardInfo(userId);
      System.out.println(cardList);
      int checkTempButton = (int) cardList.get(2);
      System.out.println(checkTempButton);
      if(checkTempButton == 0){
        System.out.println();
        Main.errorMessage("There are no saved card");
        savedMethod.setSelected(false);


      }
      else {
        String cardTypeString = (String) cardList.get(0);
        cardType.setValue(cardTypeString);
        long cardNumberTemp = (long) cardList.get(1);
        int expMonthTemp = (int) cardList.get(2);
        int expYearTemp = (int) cardList.get(3);
        int cardCvvTemp = (int) cardList.get(4);
        int bilingZipCodeTemp = (int) cardList.get(5);
        cardNumber.setText(String.valueOf(cardNumberTemp));
        expMonth.setText(String.valueOf(expMonthTemp));
        expYear.setText(String.valueOf(expYearTemp));
        cardCvv.setText(String.valueOf(cardCvvTemp));
        bilingZipCode.setText(String.valueOf(bilingZipCodeTemp));
        savedMethod.setSelected(false);
      }


  } catch (SQLException e) {
    e.printStackTrace();
  }


  }


  /**
   * @param event It takes the card info and validates it, If validation is successfull then
   *              the information is saved as payed booking
   * @throws SQLException
   */
  @FXML
  void submitPaymentSuccessfull(ActionEvent event) throws SQLException {
      boolean cardWorked = false;
      MainScreenController msc = new MainScreenController();
      String tempCardSelection = cardType.getValue();
      String tempCardNumber = cardNumber.getText();
      DatabaseManager dm = new DatabaseManager();
      String tempCVV = cardCvv.getText();
      String tempZipCode = bilingZipCode.getText();
      String tempGetMonth = expMonth.getText();
      String tempYear = expYear.getText();

      long cardNumber = Long.parseLong(tempCardNumber);
      int cvv = Integer.parseInt(tempCVV);
      int zipCode = Integer.parseInt(tempZipCode);
      int expYear = Integer.parseInt(tempYear);
      int expMonth = Integer.parseInt(tempGetMonth);
      boolean dateMonthConditionMet = monthAndYearValidation(expMonth, expYear);
    System.out.println(dateMonthConditionMet);
      if ((dateMonthConditionMet == true) ) {
        tempCardSelection = cardType.getValue();

        System.out.println(tempCardNumber);
        System.out.println(tempCardSelection +" " + tempCVV+tempZipCode);
        List<String> newList = cardNumber(tempCardSelection, tempCardNumber, tempCVV, tempZipCode);
        finalCardNum = Long.parseLong(newList.get(1));
        finalCvv = Integer.parseInt(newList.get(2));
        finalCardType = newList.get(0);
        finalZipCode = Integer.parseInt(newList.get(3));

        dm.paidColumn(ClientScreenController.orderNo);

        boolean saveCardToFile = false;

        saveCardToFile = saveCardValidation();
        if (saveCardToFile == true) {

          dm.saveCardInfo(userID, finalCardType, finalCardNum, finalCardMonth, finalCardYear,
              finalCvv, finalZipCode);

        } else {
          Main.infoMessage("Card details not saved");
        }

        msc.loadScene(submitPayment, userfxmTitile + ".fxml", userfxmTitile);

      }
      else {
        Main.errorMessage("Information entered are not correct");
      }
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

        cardNumberR = valCardNumber;
        cvcR = cvv;
        zipCodeR = zipCode;
      }//end of 15 long credit card check
      else{
        Main.infoMessage("Please be sure that card details are the correct length");
      }
    }
    else{
      if(lengthOfCard == 16 && (lengthCvv == 3) && (lengthZipCOde == 5) )
      {
        cardNumberR = valCardNumber;
        cvcR = cvv;
        zipCodeR = zipCode;

      }//end of 15 long credit card check
      else{
          Main.infoMessage("Please be sure that card details are the correct length");
      }
    }
    return Arrays.asList(cardSelection, cardNumberR,cvcR, zipCodeR);
    }


    //https://www.dreamincode.net/forums/topic/269950-finding-the-length-of-a-long-data-type/

  public boolean monthAndYearValidation(int month, int year){
    boolean yearMonthmet = false;
    year = 2000 + year;
    int presentYear = Calendar.getInstance().get(Calendar.YEAR);
    if(year>presentYear && month>0 && month<13){
      finalCardMonth = month;
      finalCardYear = year;
      yearMonthmet = true;
    }
    else if(presentYear == year){
      int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
      if(month >= currentMonth){
        finalCardMonth = month;
        finalCardYear = year;
        yearMonthmet = true;
      }
      else{
    	Main.errorMessage("The card detailed entered is expired");
      }
    }
    else{
       	Main.errorMessage("The card detailed entered is expired");
            }
    return yearMonthmet;
  }
  public boolean saveCardValidation(){
    return saveCard.isSelected();
  }




}
