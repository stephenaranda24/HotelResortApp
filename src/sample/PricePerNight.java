package sample;

public enum PricePerNight {
	
  A(39.99), B(49.99), C(79.99), D(89.99);
  private final double value;


  PricePerNight(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

}
