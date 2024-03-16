package com.airconsole.reservation.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.airconsole.reservation.model.Reservation; 

public class SeatClassReservationHandler {
	
	private static final int TOTAL_PLANE_ROWS = 8;
	private static final int TOTAL_PLANE_SEATS = 5;
	private static final String COMMON_FILE_DELIMITER = "\t";
	private static final String reservationMapFilename = "reservationMap.map";
	
	private static final Scanner scanner = new Scanner(System.in);
	
	// Create a 2D array (matrix)
	private char[][] reservationMap = new char[TOTAL_PLANE_ROWS][TOTAL_PLANE_SEATS];
    
	/**
	 * initialises the class by loading existing reservations if any.
	 */	 
	public SeatClassReservationHandler() {
		//initialize Reservation Map. IF not found it either was removed, lost or is the first time the code runs.
		if (!FileExistenceChecker.check(reservationMapFilename)) {
		    for (int row = 0; row < TOTAL_PLANE_ROWS; row++) {
		        for (int seat = 0; seat < TOTAL_PLANE_SEATS; seat++) {
		            reservationMap[row][seat] = 'E'; // Set 'E' for empty seat
		        }
		    }
		}
	}
	
	public void reserveSeat() throws IOException{
		System.out.println("*******************************\r\n"
			     + "**    Seat Class  **\r\n"
				 + "*******************************\r\n");
	
		System.out.print("Task Selection\r\n"
				+ "B: Business Class\r\n"
				+ "E: Economy Class\r\n"
				+ "X: Exit the System\r\n"
				+ "\r\n");
		
		System.out.print("Please enter the seat class you want to reserve: ");
		
		boolean userSelectionMade = false;
		
		while (!userSelectionMade) {
			
			char userInput = scanner.next().charAt(0);
		
			userInput = Character.toUpperCase(userInput);
			
			// Process user input
		   switch (userInput) {
		       case 'B' :
		    	   userSelectionMade = true;
		    	   processClassReservation(Reservation.PlaneClass.BUSINESS_CLASS);
		           break;
		       case 'E': 
		    	   userSelectionMade = true;
		    	   processClassReservation(Reservation.PlaneClass.ECONOMY_CLASS);
		           break;
		       case 'X': 
		           break;
		       default:
		           System.err.print("Invalid Entry! Please enter B, E, or X: ");
		   }
		}
		
		storeReservation();
	}
	
	// Process reservation 
    private void processClassReservation(final Reservation.PlaneClass pclass) throws IOException { 
    	//load latest reservation map
    	loadReservationMap();
    	
		//show plain map for Business
    	
    }
    
    private void loadReservationMap() throws IOException{
    	try (BufferedReader reader = new BufferedReader(new FileReader(reservationMapFilename))) {
		 
            // Read lines from the file and parse matrix elements
            String line;
            int row = 0;
             
            while ((line = reader.readLine()) != null) {
            	String[] elements = line.trim().split(COMMON_FILE_DELIMITER);
                
                for (int col = 0; col < elements.length; col++) {
                	reservationMap[row][col] = elements[col].charAt(0);
                }
                row++;
            }
		} catch (IOException | NumberFormatException e) {
			throw new IOException("Error loading matrix from file: " + e.getMessage());
		}
    }
    
	public void storeReservation() throws IOException{
		if (reservationMap == null || reservationMap.length == 0) {
			System.err.println("Error storing matrix. There is no information to store");
		} else {
		
			// Write the matrix to a text file        
			BufferedWriter writer = new BufferedWriter(new FileWriter(reservationMapFilename));
	        
	        for (int i = 0; i < reservationMap.length; i++) {
	            for (int j = 0; j < reservationMap[i].length; j++) {
	                writer.write(reservationMap[i][j] + COMMON_FILE_DELIMITER);
	            }
	            writer.newLine();
	        }
	        
	        System.out.println("Matrix has been written to " + reservationMapFilename);
	        writer.close();
		}
	}	
}
