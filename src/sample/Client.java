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
