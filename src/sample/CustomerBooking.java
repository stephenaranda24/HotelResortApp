package sample;

public class CustomerBooking {

	private int invoice;
	private String cname;
	private String room;
	private String date;
	private double cost;
	private boolean paid;

	/*
	 * public CustomerBooking(String roomType, String date, boolean paid) { this(0,
	 * Main.loggedInUser, roomType, date, paid); }
	 */

	public CustomerBooking(int invoiceNumber, String customerName, String roomType, String date, boolean paid) {
		this.invoice = invoiceNumber;
		this.cname = customerName;
		this.room = roomType;
		this.date = date;
		this.cost = PricePerNight.valueOf(roomType).getValue();
		this.paid = paid;
	}

	public int getInvoice() {
		return invoice;
	}

	public String getCname() {
		return cname;
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
