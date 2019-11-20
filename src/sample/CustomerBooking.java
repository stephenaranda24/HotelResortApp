package sample;

public class CustomerBooking {

	private String room;
	private String date;
	private double cost;
	private boolean paid;

	public CustomerBooking(String roomType, String date, boolean paid) {
		this.room = roomType;
		this.date = date;
		this.cost = PricePerNight.valueOf(roomType).getValue();
		this.paid = paid;
	}

	public String getRoom() {
		return room;
	}

	public String getDate() {
		return date;
	}

	public double getCost() {
		return cost;
	}
	
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public boolean isPaid() {
		return paid;
	}
}
