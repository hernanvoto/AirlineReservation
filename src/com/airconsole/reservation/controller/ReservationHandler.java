package com.airconsole.reservation.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.airconsole.reservation.model.Passenger;
import com.airconsole.reservation.model.Reservation;
import com.airconsole.reservation.model.Seat; 

/**
 * this class may be to long too complex too procedural
 */
public class ReservationHandler {
	
	private static final int BUSINESS_CLASS_FIRST_PLANE_ROW = 1;
	private static final int BUSINESS_CLASS_LAST_PLANE_ROW = 5;
	private static final int ECONOMY_CLASS_FIRST_PLANE_ROW = 6;
	
	private static final char EMPTY_SEAT_ID = 'E';
	private static final char OCCUPIED_SEAT_ID = 'X';
	private static final int TOTAL_PLANE_ROWS = 8;
	private static final int TOTAL_PLANE_COL = 5;
	private static final String COMMON_FILE_DELIMITER = ",";
	
	private static final String planeSeatReservationMapFilename = "planeSeatReservation.map";
	private static final String passangerSeatReservationMapFilename = "passangerSeatReservation.map";
	
	private static final Scanner scanner = new Scanner(System.in);
	
	// Create a 2D array (matrix)
	private char[][] planeSeatReservationMap = new char[TOTAL_PLANE_ROWS][TOTAL_PLANE_COL];
	private Map<String, Passenger> passengerSeatReservationMap = new HashMap<String, Passenger>();
    
	/**
	 * initialises the class by loading existing reservations if any.
	 */	 
	public ReservationHandler() {
		
		//initialize Reservation Map. IF not found it either was removed, lost or is the first time the code runs.
		if (!FileExistenceChecker.check(planeSeatReservationMapFilename)) {
		    for (int row = 0; row < TOTAL_PLANE_ROWS; row++) {
		        for (int seat = 0; seat < TOTAL_PLANE_COL; seat++) {
		        	planeSeatReservationMap[row][seat] = EMPTY_SEAT_ID;  
		        }
		    }
		} else { //load existing reservation
			loadReservationMap();
		}
	}
	
	public void reserveSeat() throws IOException{
		System.out.println(
				 "\n******************************\r\n"
			     + "**        Seat Class        **\r\n"
				 + "******************************\r\n");
	
		System.out.print("Task Selection\r\n"
				+ "B: Business Class\r\n"
				+ "E: Economy Class\r\n"
				+ "X: Exit the System\r\n"
				+ "\r\n");
		
		System.out.print("Please enter the seat class you want to reserve: ");
		
		boolean validUserSelection = false;
		
		while (!validUserSelection) {
			
			char userInput = scanner.nextLine().charAt(0);
		
			userInput = Character.toUpperCase(userInput);
			
			// Process user input
			switch (userInput) {
				case 'B' :
					validUserSelection = true;
		    		processClassReservation(Reservation.PlaneClass.BUSINESS_CLASS);
		    		break;
				case 'E': 
					validUserSelection = true;
					processClassReservation(Reservation.PlaneClass.ECONOMY_CLASS);
					break;
				case 'X':
					validUserSelection = true;
					break;
				default:			
					System.err.print("Invalid Entry! Please enter B, E, or X: ");
			}
		}		
		scanner.close();	
	}
	
	// Process reservation 
    private void processClassReservation(final Reservation.PlaneClass selectedClass) throws IOException { 
    	printReservationMap(selectedClass);

    	boolean seatIsBooked = false;
    	int startingPlaneRowAccordingToClass = 0;
    	
    	switch (selectedClass) {
        	case BUSINESS_CLASS:
    			startingPlaneRowAccordingToClass = BUSINESS_CLASS_FIRST_PLANE_ROW;
    		break;
        	case ECONOMY_CLASS:
    			startingPlaneRowAccordingToClass = ECONOMY_CLASS_FIRST_PLANE_ROW;
    		break;
    	}
    	
    	/** Reservation */
    	while (!seatIsBooked) {
    		Integer rowInput = null;
        	boolean validUserSelection = false;
        	
        	/** ROW */
			while (!validUserSelection) {
					
				System.out.print("\nPlease enter the row number: ");
		    	
				String userInput = scanner.nextLine();
				
				// Check if userInputChar is a digit
				if (userInput.matches("\\d+")) {
	
					rowInput = Integer.parseInt(userInput);
				    
				    if (rowInput >= startingPlaneRowAccordingToClass && rowInput <= TOTAL_PLANE_ROWS) {
				    	validUserSelection = true;
				    } else {
				    	System.out.println("Invalid Class Entry!");
				    }
				} else {
					System.out.println("Invalid Class Entry!");
				}
			}
			
			validUserSelection = false;
	    	String colInput = null;
	    	
	    	/** Column */
			while (!validUserSelection) {
				System.out.print("Please enter the seat letter: ");	
				colInput = scanner.nextLine();
				
				if (colInput.matches("[a-eA-E]")) {
	
					validUserSelection = true;
				    
				} else {
					System.out.println("Invalid Entry! valid range (A-E)");
				}
			}
			
			Reservation res = new Reservation();
			Seat seat = new Seat();
			seat.setSeatRow(rowInput);
			seat.setSeatColumnLetter(colInput);
			
			res.setSeat(seat);
			
			/** Save seat*/
			if (isSeatAvailable(res)) {
				System.out.println("\nSeat " + res.getSeat().getSeatRow() + res.getSeat().getSeatColumnLetter() + " is available.");
				
				/** get and save Passenger*/
				Passenger passenger = new Passenger();
				
				validUserSelection = false;
				
				while (!validUserSelection) {
					System.out.print("\nPlease enter the passenger's firstname:");
					String userInput = scanner.nextLine();
		    	
					// Check if userInputChar is not empty
					if (!userInput.isEmpty()) {
						passenger.setName(userInput);
						validUserSelection = true;
				    } else {
				    	System.out.println("Invalid Entry! Please enter a firstname ");
				    }
				}

				validUserSelection = false;
				
				while (!validUserSelection) {
			    	System.out.print("\nPlease enter the passenger's lastname:");
					String userInput = scanner.nextLine();
		    	
					// Check if userInputChar is not empty
					if (!userInput.isEmpty()) {
						passenger.setSurname(userInput);
						validUserSelection = true;					
				    } else {
				    	System.out.println("Invalid Entry! Please enter a lastname");
				    }
				}
	
				validUserSelection = false;
				
				while (!validUserSelection) {
					System.out.print("\nPlease enter the passenger's passport number:");
					String userInput = scanner.nextLine();
		    	
					// Check if userInputChar is not empty
					if (!userInput.isEmpty()) {
						passenger.setPassportNumber(userInput);
						validUserSelection = true;					
				    } else {
				    	System.out.println("Invalid Entry! Please enter a passport number");
				    }
				}
				
				res.setPassenger(passenger);
		    		    			    	
				//save seat
				planeSeatReservationMap[res.getSeat().getSeatRowNumberForStorage()][res.getSeat().getSeatColumnNumberForStorage()] = OCCUPIED_SEAT_ID;
				passengerSeatReservationMap.put(res.getSeat().asHashMapKey(), res.getPassenger());
				
				storeReservation();
				
				seatIsBooked = true;
				System.out.println("\nSeat " + res.getSeat().getSeatRow() + res.getSeat().getSeatColumnLetter() + " was successfully booked!");				
			} else {
				System.out.println("\nSorry seat " + res.getSeat().getSeatRow() + res.getSeat().getSeatColumnLetter() + " is already taken.");				
			}
    	}    	
    }
    
    private boolean isSeatAvailable(Reservation res) {
    	
    	if (planeSeatReservationMap[res.getSeat().getSeatRowNumberForStorage()][res.getSeat().getSeatColumnNumberForStorage()] == OCCUPIED_SEAT_ID) {
    	    return false;
    	}

        return true;
	}

	private void printReservationMap(final Reservation.PlaneClass selectedClass) {
		int startingPlaneRowAccordingToClass = 0;
		
    	if (planeSeatReservationMap == null || planeSeatReservationMap.length == 0) {
    		System.err.println("Error printing matrix. There is no information");
			System.exit(1);
    	} else {
    			
    		if (selectedClass == Reservation.PlaneClass.BUSINESS_CLASS) {    	    	
	    		System.out.println("\r\n*******************************\r\n"
	   			     + "**      Business Class       **\r\n"
	   				 + "*******************************\r\n");
	    		
	    		startingPlaneRowAccordingToClass = BUSINESS_CLASS_FIRST_PLANE_ROW;
    		} else {
    			System.out.println("\r\n*******************************\r\n"
   	   			     + "**      Economy Class       **\r\n"
   	   				 + "*******************************\r\n");
    			
    			startingPlaneRowAccordingToClass = ECONOMY_CLASS_FIRST_PLANE_ROW;    	    	
    		}
    		
    	    //print columns
    		System.out.println("\t" + "A" + "\t" + "B" + "\t" + "C" + "\t" + "D" + "\t" + "E" + "\t");
	    	// Loop through each row
	    	for (int row = startingPlaneRowAccordingToClass-1; row < TOTAL_PLANE_ROWS; row++) {

	    		if (selectedClass == Reservation.PlaneClass.BUSINESS_CLASS && row == BUSINESS_CLASS_LAST_PLANE_ROW) {
	    			break;
	    		} else {
	    			
		    		// Print Row Numbers
		    		System.out.print(row + 1);
	    			
		    	    for (int col = 0; col  < TOTAL_PLANE_COL; col ++) {
		    	        // Print the current element followed by a space
		    	        System.out.print( "\t" + planeSeatReservationMap[row][col]);
		    	    }
		    	    // Move to the next line after printing all columns in the row
		    	    System.out.println();
	    		}
	    	}
    	}
    }
    
    //since there is only one plane one route one time, i kept this simple
	//Not sure how to reuse this one
    private void loadReservationMap() {
    	
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
			System.out.println("Error loading file: " + e.getMessage());
			System.exit(1);
		}
    }

	//not sure where this should go. 
	public void storeReservation() throws IOException{
		// Write the planeSeatReservationMap to a text file        
		BufferedWriter writer = new BufferedWriter(new FileWriter(planeSeatReservationMapFilename));
        
        for (int i = 0; i < planeSeatReservationMap.length; i++) {
            for (int j = 0; j < planeSeatReservationMap[i].length; j++) {
                if (j == planeSeatReservationMap[i].length -1)
                	writer.write(planeSeatReservationMap[i][j]);
                else
                	writer.write(planeSeatReservationMap[i][j] + COMMON_FILE_DELIMITER);
            }
            writer.newLine();
        }
        
        writer.close();
        
        // Write the PassangerSeatReservationMap to a text file        
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(passangerSeatReservationMapFilename))) {
            outputStream.writeObject(passengerSeatReservationMap);
            
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        } 
	}	
}
