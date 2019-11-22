package sample;

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

	public CustomerBooking(int invoice, String cname, String room, String date, double cost,
			String paid,String checkinStatus) {
		this.invoice = invoice;
		this.cname = cname;
		this.room = room;
		this.date = date;
		this.cost = cost;
		this.paid = paid;
		this.checkinStatus = checkinStatus;
	}

	public int getInvoice() {
		return invoice;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getCheckinStatus() {
		return checkinStatus;
	}

	public void setCheckinStatus(String checkinStatus) {
		this.checkinStatus = checkinStatus;
	}
}
