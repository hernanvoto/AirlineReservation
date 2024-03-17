package com.airconsole.reservation.model;

public class Reservation {
	 
	public enum PlaneClass {
	    BUSINESS_CLASS,
	    ECONOMY_CLASS,
	}
	
	public static final int TOTAL_PLANE_ROWS = 8;
	public static final int TOTAL_PLANE_COL = 5;

	
	public static final int BUSINESS_CLASS_FIRST_PLANE_ROW = 1;
	public static final int BUSINESS_CLASS_LAST_PLANE_ROW = 5;
	public static final int ECONOMY_CLASS_FIRST_PLANE_ROW = 6;

	private Passenger passenger;
	private Seat seat;  
	
	public Passenger getPassenger() {
		return passenger;
	}
	
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	 
}
