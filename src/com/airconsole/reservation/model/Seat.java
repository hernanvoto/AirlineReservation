package com.airconsole.reservation.model;

import java.io.Serializable;

public class Seat implements Serializable {
	public static final char EMPTY_SEAT_ID = 'E';
	public static final char OCCUPIED_SEAT_ID = 'X';
  
	private static final long serialVersionUID = 6287922474113391944L;
	
	private Integer seatRow = null;
	private String seatColumnLetter = null;
	
	public Integer getSeatRow() {
		return seatRow;
	}
	
	public void setSeatRow(Integer seatRow) {
		this.seatRow = seatRow;
	}
	
	public String getSeatColumnLetter() {
		return seatColumnLetter;
	}
	
	public void setSeatColumnLetter(String seatColumnLetter) {
		this.seatColumnLetter = seatColumnLetter.toUpperCase();
	}
	
	public String asHashMapKey() {
		return seatRow + seatColumnLetter;
	}

	public Integer getSeatRowNumberForStorage() {
		return seatRow - 1;
	}
	
	public Integer getSeatColumnNumberForStorage() {
//		Integer seatLetterNumber; 
		if (seatColumnLetter != null && !seatColumnLetter.isBlank()){
			  switch (seatColumnLetter.toUpperCase()) {
				case "A" :
					return 0;
				case "B": 
					return 1;
				case "C":
					return 2;
				case "D":
					return 3;
				case "E":
					return 4;
			  }
		}
		
		return -1;
	}	
}
