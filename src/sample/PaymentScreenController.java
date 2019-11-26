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
  private TextArea amount;
  public String userfxmTitile;


  int finalCardYear = 0;
  int finalCardMonth = 0;
  String finalCardType = null;
  long finalCardNum = 0;
  int finalCvv = 0;
  int finalZipCode = 0;
  String userID = null;



  @FXML
  void backFrompaymentAction(ActionEvent event) {
    {
      MainScreenController msc = new MainScreenController();
      msc.loadScene(backFrompayment,userfxmTitile+".fxml","clientScreen");
    }


  }

  @FXML
  public void initialize(URL url, ResourceBundle resources) {
    userfxmTitile = Main.Type;
    System.out.println(Main.Type + " TYPPPPPP");


	
    MainScreenController msc = new MainScreenController();

    setComboBoxText();  
    System.out.println("Logged User:" + Main.loggedInUser);
    userID = Main.loggedInUser;

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
    amount.setText(String.valueOf(amounts));

  }



  private void setComboBoxText() {
    cardType.setPromptText("Select a type of card.");
    cardType.getItems().addAll("VISA", "MasterCard", "Discover", "Amex");
  }
  @FXML
  void payLaterAction(ActionEvent event) {
    MainScreenController msc = new MainScreenController();
    msc.loadScene(payLater,userfxmTitile+".fxml","clientScreen");
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
    	Main.infoMessage("Card details not saved");
    }
    msc.loadScene(submitPayment,userfxmTitile+".fxml","clientScreen");


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
        Main.infoMessage("Please be sure that card details are the correct length");
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
          Main.infoMessage("Please be sure that card details are the correct length");
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
