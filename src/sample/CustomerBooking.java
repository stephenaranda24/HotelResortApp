package sample;

/**
 * This class contains all the characteristics needed for a customer booking, such as the invoice
 * number, name, the room, date, costs, whether the booking is paid or not, and the check in status
 * of the customer. It also contains all the required setters and getters for these properties.
 *
 * @version 1.0
 * @ Romanov Andre
 * @ Shafi Mushfique
 * @ Stephen Aranda
 * @since 2019-09-21
 */
public class CustomerBooking {

  private int invoice;
  private String cname;
  private String room;
  private String date;
  private double cost;
  private String paid;
  private String checkinStatus;


  /*
   * public CustomerBooking(String roomType, String date, boolean paid) { this(0,
   * Main.loggedInUser, roomType, date, paid); }
   */

  /**
   * Constructor for the CustomerBooking class.
   *
   * @param invoice       The invoice number of the booking.
   * @param cname         The name of the customer booking.
   * @param room          The room number of the booking.
   * @param date          The date of the booking.
   * @param cost          The cost of the booking.
   * @param paid          The status of whether a booking is paid or not.
   * @param checkinStatus The status of whether a customer has checked in or not.
   */
  public CustomerBooking(int invoice, String cname, String room, String date, double cost,
      String paid, String checkinStatus) {
    this.invoice = invoice;
    this.cname = cname;
    this.room = room;
    this.date = date;
    this.cost = cost;
    this.paid = paid;
    this.checkinStatus = checkinStatus;
  }

  /**
   * Accessor/Getter method for the invoice number.
   *
   * @return The invoice number.
   */
  public int getInvoice() {
    return invoice;
  }

  /**
   * Mutator/Setter method for the invoice number.
   *
   * @param invoice The invoice number
   */
  public void setInvoice(int invoice) {
    this.invoice = invoice;
  }

  /**
   * Accessor/Getter method for the name.
   *
   * @return The name of the client.
   */
  public String getCname() {
    return cname;
  }

  /**
   * Mutator/Setter method for the name.
   *
   * @param cname The name of the client.
   */
  public void setCname(String cname) {
    this.cname = cname;
  }

  /**
   * Accessor/Getter method for the room.
   *
   * @return
   */
  public String getRoom() {
    return room;
  }

  /**
   * Mutator/Setter method for the room.
   *
   * @param room
   */
  public void setRoom(String room) {
    this.room = room;
  }

  /**
   * Accessor/Getter method for the dates the client is booked for.
   *
   * @return Customer bookings dates
   */
  public String getDate() {
    return date;
  }

  /**
   * Mutator/Setter for the date of the client is booked for.
   *
   * @param date Customer booking dates
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * Accessor/Getter for the cost of the booking.
   *
   * @return The cost of booking.
   */
  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  /**
   * Accessor/Getter method for the string that determines if the booking is paid or not.
   *
   * @return The string value.
   */
  public String getPaid() {
    return paid;
  }

  /**
   * Mutator/Setter method for the string that determines if the booking is paid or not.
   *
   * @param paid The string.
   */
  public void setPaid(String paid) {
    this.paid = paid;
  }

  /**
   * Accessor/Getter method for the string that determines whether the client has checked in or
   * not.
   *
   * @return The string value.
   */
  public String getCheckinStatus() {
    return checkinStatus;
  }

  /**
   * Mutator/Setter method for the string that determines whether the customer
   * has checked in.
   *
   * @param checkinStatus The string value.
   */
  public void setCheckinStatus(String checkinStatus) {
    this.checkinStatus = checkinStatus;
  }
}
