package sample;

/**
 *
 */
public class Client {
	
private String roomName, paymentStatus, dateDisplay;
private  double amount;

  public Client(String roomName, String paymentStatus, String dateDisplay, double amount) {
    this.roomName = roomName;
    this.paymentStatus = paymentStatus;
    this.dateDisplay = dateDisplay;
    this.amount = amount;
  }

  /**
   * Getter for property 'roomName'.
   *
   * @return Value for property 'roomName'.
   */
  public String getRoomName() {
    return roomName;
  }

  /**
   * Setter for property 'roomName'.
   *
   * @param roomName Value to set for property 'roomName'.
   */
  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  /**
   * Getter for property 'paymentStatus'.
   *
   * @return Value for property 'paymentStatus'.
   */
  public String getPaymentStatus() {
    return paymentStatus;
  }

  /**
   * Setter for property 'paymentStatus'.
   *
   * @param paymentStatus Value to set for property 'paymentStatus'.
   */
  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  /**
   * Getter for property 'dateDisplay'.
   *
   * @return Value for property 'dateDisplay'.
   */
  public String getDateDisplay() {
    return dateDisplay;
  }

  /**
   * Setter for property 'dateDisplay'.
   *
   * @param dateDisplay Value to set for property 'dateDisplay'.
   */
  public void setDateDisplay(String dateDisplay) {
    this.dateDisplay = dateDisplay;
  }

  /**
   * Getter for property 'amount'.
   *
   * @return Value for property 'amount'.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Setter for property 'amount'.
   *
   * @param amount Value to set for property 'amount'.
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }
}


