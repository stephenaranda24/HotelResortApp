package sample;

public class Client {
	
private String roomName, paymentStatus, dateDisplay;
private  double amount;

  public Client(String roomName, String paymentStatus, String dateDisplay, double amount) {
    this.roomName = roomName;
    this.paymentStatus = paymentStatus;
    this.dateDisplay = dateDisplay;
    this.amount = amount;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String getDateDisplay() {
    return dateDisplay;
  }

  public void setDateDisplay(String dateDisplay) {
    this.dateDisplay = dateDisplay;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}

/*
 <TableView fx:id="tableActivity" layoutX="172.0" layoutY="18.0" prefHeight="583.0" prefWidth="924.0">
<columns>
<TableColumn fx:id="invoiceID" prefWidth="136.77777099609375" text="Invoice No">
<cellValueFactory><PropertyValueFactory property="invoice" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="fullNametoDisplay" prefWidth="189.22222900390625" text="Full Name">
<cellValueFactory><PropertyValueFactory property="cname" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="roomNo" prefWidth="97.11114501953125" text="Room Number">
<cellValueFactory><PropertyValueFactory property="room" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="dateToDisplay" prefWidth="208.2222900390625" text="Stay Date">
<cellValueFactory><PropertyValueFactory property="date" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="amount" prefWidth="86.111083984375" text="Amount">
<cellValueFactory><PropertyValueFactory property="cost" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="paymentStatus" prefWidth="113.888916015625" text="Payment Status">
<cellValueFactory><PropertyValueFactory property="paid" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="checkInStatus" prefWidth="75.0" text="CheckinStatus" >
<cellValueFactory><PropertyValueFactory property="checkinStatus" /></cellValueFactory>
</TableColumn>
*/

/*<TableView fx:id="tablePaid" layoutX="369.0" layoutY="52.0" prefHeight="200.0" prefWidth="372.0">
<columns>
<TableColumn fx:id="upTableRoomNo" prefWidth="75.0" text="RoomNo" >
<cellValueFactory><PropertyValueFactory property="room" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="upTableDate" prefWidth="75.0" text="Date" >
<cellValueFactory><PropertyValueFactory property="date" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="upTableAmount" prefWidth="75.0" text="Amount" >
<cellValueFactory><PropertyValueFactory property="cost" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="upTableInvoice" prefWidth="75.0" text="Invoice" >
<cellValueFactory><PropertyValueFactory property="invoice" /></cellValueFactory>
</TableColumn>
</columns>
</TableView>
<TableView fx:id="tableUnpaid" layoutX="369.0" layoutY="331.0" prefHeight="200.0" prefWidth="367.0">
<columns>
<TableColumn fx:id="pTableRoomNo" prefWidth="75.0" text="RoomNo" >
<cellValueFactory><PropertyValueFactory property="room" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="pTableDate" prefWidth="75.0" text="Date" >
<cellValueFactory><PropertyValueFactory property="date" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="pTableAmount" prefWidth="75.0" text="Amount" >
<cellValueFactory><PropertyValueFactory property="cost" /></cellValueFactory>
</TableColumn>
<TableColumn fx:id="pTableInvoice" prefWidth="75.0" text="Invoice" >
<cellValueFactory><PropertyValueFactory property="invoice" /></cellValueFactory>
</TableColumn>*/


