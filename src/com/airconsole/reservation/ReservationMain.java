package com.airconsole.reservation;
 
import java.io.IOException;
import java.util.Scanner;

import com.airconsole.reservation.controller.ReservationHandler;
import com.airconsole.reservation.controller.SeatVerifierHandler;

public class ReservationMain {

	private static final Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		mainAppMenu();
	}
	
	private static void mainAppMenu() {
		
		System.out.println("*******************************\r\n"
			     + "**    Welcome to AirConsole  **\r\n"
				 + "*******************************\r\n");
	
		System.out.print("Task Selection\r\n"
				+ "R: Reservation\r\n"
				+ "S: Seat Verification\r\n"
				+ "X: Exit the System\r\n"
				+ "\r\n");
		
		System.out.print("Please enter the task you want to perform: ");

		boolean userSelectionMade = false;
		
		while (!userSelectionMade) {

			char userInput = scanner.next().charAt(0);
		
			userInput = Character.toUpperCase(userInput);
		
			// Process user input
		    switch (Character.toUpperCase(userInput)) {
		        case 'R':
		        	userSelectionMade = true ;
		        	processReservation();
		            break;
		        case 'S':
		        	userSelectionMade = true ;
		        	processSeatVerification();
		            break;
		        case 'X':
		        	userSelectionMade = true ;

		            break; 
		        default:
		            System.err.print("Invalid choice! Please enter 'R' 'S' or 'X': ");
		    }
		}
        exit();
	}

	private static void exit() {
	    scanner.close();
	    System.exit(0);
	} 
	  
	//Returns upper case char
	private static final void processReservation() { 
		
		ReservationHandler reservationHandler = new ReservationHandler();
	    
		try {
			
			reservationHandler.reserveSeat();
			
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		    e.printStackTrace();
		}
	}
	 
	//Returns upper case char
	private static final void processSeatVerification() {
		SeatVerifierHandler seatVerifierHandler = new SeatVerifierHandler();
		seatVerifierHandler.verifySeat();
	}
}
