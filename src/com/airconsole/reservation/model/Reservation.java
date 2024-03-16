package com.airconsole.reservation.model;

public class Reservation {
	 
	public enum PlaneClass {
	    BUSINESS_CLASS,
	    ECONOMY_CLASS,
	}
	
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
