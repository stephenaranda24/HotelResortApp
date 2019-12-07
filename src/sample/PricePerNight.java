package sample;

/**
 * Enum for the prices per night for each room.
 *
 * @version 1.0
 * @author Romanov Andre
 * @author Shafi Mushfique
 * @author Stephen Aranda
 * @since 2019-09-21
 */
public enum PricePerNight {

  A(39.99), B(49.99), C(79.99), D(89.99);
  private final double value;

  /**
   * Constructor for price
   * @param value price of the room
   */
  PricePerNight(double value) {
    this.value = value;
  }

  /**
   * Getter for property 'value'.
   *
   * @return Value for property 'value'.
   */
  public double getValue() {
    return value;
  }

}
