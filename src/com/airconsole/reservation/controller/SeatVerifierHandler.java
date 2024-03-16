package com.airconsole.reservation.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.airconsole.reservation.model.Passenger; 
import com.airconsole.reservation.model.Seat; 

public class SeatVerifierHandler {
	private Scanner scanner = new Scanner(System.in);
	 
	private static final int TOTAL_PLANE_ROWS = 8;
	private static final int TOTAL_PLANE_COL = 5;
	private static final String COMMON_FILE_DELIMITER = ",";
	
	private static final String planeSeatReservationMapFilename = "planeSeatReservation.map";
	private static final String passangerSeatReservationMapFilename = "passangerSeatReservation.map"; 
	
	// Create a 2D array (matrix)
	private char[][] planeSeatReservationMap = new char[TOTAL_PLANE_ROWS][TOTAL_PLANE_COL];
	private Map<String, Passenger> passangerSeatReservationMap = new HashMap<String, Passenger>();
    
	public SeatVerifierHandler() {
		loadPlaneSeatReservationMap();
		loadPassangerSeatReservationMap();
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
			    
			    if (rowInput >= 1 && rowInput <= TOTAL_PLANE_COL) {
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
	
	private void loadPlaneSeatReservationMap() {
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(planeSeatReservationMapFilename))) {
		 
            // Read lines from the file and parse matrix elements
            String line;
            int row = 0;
             
            while ((line = reader.readLine()) != null) {
            	String[] elements = line.trim().split(COMMON_FILE_DELIMITER);
                
                for (int col = 0; col < elements.length; col++) {
                	planeSeatReservationMap[row][col] = elements[col].charAt(0);
                }
                row++;
            }
		} catch (IOException | NumberFormatException e) {
			System.out.println("Error loading planeSeatReservation file: " + e.getMessage());
			System.exit(1);
		}
    }
	
	private void loadPassangerSeatReservationMap() {
	
		if (FileExistenceChecker.check(passangerSeatReservationMapFilename)) {
			try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(passangerSeatReservationMapFilename))) {
				
				passangerSeatReservationMap = (Map<String, Passenger>) inputStream.readObject();
				
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Error loading data from file: " + e.getMessage());
			}
		} else {
			System.err.println("Error loading data from file: " + passangerSeatReservationMapFilename);
			System.exit(1);
		}
	}
}
