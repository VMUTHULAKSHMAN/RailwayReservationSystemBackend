package model;

public class Passenger {
	private String passengerName;
	private int passengerAge;
	private String Contactnumber;
	public String getPassengerName() {
		return passengerName;
	}
	public Passenger(String passengerName, int passengerAge, String contactnumber) {
		super();
		this.passengerName = passengerName;
		this.passengerAge = passengerAge;
		Contactnumber = contactnumber;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public int getPassengerAge() {
		return passengerAge;
	}
	public void setPassengerAge(int passengerAge) {
		this.passengerAge = passengerAge;
	}
	public String getContactnumber() {
		return Contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		Contactnumber = contactnumber;
	}
}
