package com.airconsole.reservation.controller;
 
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.airconsole.reservation.model.Passenger; 
import com.airconsole.reservation.model.Seat;
import com.airconsole.reservation.util.ReservationUtil; 

public class SeatVerifierHandler {
	private Scanner scanner = new Scanner(System.in);
	 
	private static final int TOTAL_PLANE_ROWS = 8; 
	
	// Create a 2D array (matrix) 
	private Map<String, Passenger> passangerSeatReservationMap = new HashMap<String, Passenger>();
    
	public SeatVerifierHandler() {
		passangerSeatReservationMap = ReservationUtil.loadPassangerSeatReservationMap();
	}
	
	public void verifySeat() {
	
		System.out.println(
				   "*******************************\r\n"
			     + "**    Seat Verification      **\r\n"
				 + "*******************************\r\n");
	 
		Integer rowInput = null;
    	boolean validUserSelection = false;    	
    	
    	/** ROW */
		while (!validUserSelection) {
				
			System.out.print("Please enter the row number: ");
	    	
			String userInput = scanner.nextLine();
			
			// Check if userInputChar is a digit
			if (userInput.matches("\\d+")) {

				rowInput = Integer.parseInt(userInput);
			    
			    if (rowInput >= 1 && rowInput <= TOTAL_PLANE_ROWS) {
			    	validUserSelection = true;
			    } else {
			    	System.out.println("Invalid Entry! valid range");
			        System.out.print("Please enter the row number: ");
			    }
			} else {
				System.out.println("Invalid Entry! valid range)");
		        System.out.print("Please enter the row number: ");
			}
		}
		
		System.out.print("Please enter the seat letter: ");
    	validUserSelection = false;
    	String colInput = null;
    	
    	/** Column */
		while (!validUserSelection) {
			
			colInput = scanner.nextLine();
			
			if (colInput.matches("[a-eA-E]")) {

				validUserSelection = true;
			    
			} else {
				System.out.println("Invalid Entry! valid range (A-E)");
		        System.out.print("Please enter the seat letter: ");
			}
		} 
		
		Seat seat = new Seat();
		seat.setSeatRow(rowInput); 
		seat.setSeatColumnLetter(colInput);
	    
		if (passangerSeatReservationMap.containsKey(seat.asHashMapKey())) {
		
	        System.out.println("\nPassenger details");
	        System.out.println("Firstname:" + passangerSeatReservationMap.get(seat.asHashMapKey()).getName());
	        System.out.println("Lastname:" + passangerSeatReservationMap.get(seat.asHashMapKey()).getSurname());
	        System.out.println("Passport Number:" + passangerSeatReservationMap.get(seat.asHashMapKey()).getPassportNumber());
		} else {
			System.out.println("This seat is not reserved");
		}
		
		scanner = new Scanner(System.in);
		System.out.println("\nPress ENTER key to continue...");
	    scanner.nextLine(); // This will wait for the user to press Enter
	    
	    scanner.close();	        
	}
	
	
}
