package com.airconsole.reservation.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import com.airconsole.reservation.controller.FileExistenceChecker;
import com.airconsole.reservation.model.Passenger;
import com.airconsole.reservation.model.Seat;
import com.airconsole.reservation.model.Reservation;

public class ReservationUtil {

private static final String planeSeatReservationMapFilename = "planeSeatReservation.map";
private static final String passangerSeatReservationMapFilename = "passangerSeatReservation.map"; 

private static final String COMMON_FILE_DELIMITER = ",";

	public static char[][] loadPlaneSeatReservationMap() {
		char[][] planeSeatReservationMap = new char[Reservation.TOTAL_PLANE_ROWS][Reservation.TOTAL_PLANE_COL];
		
		if (!FileExistenceChecker.check(planeSeatReservationMapFilename)) {
		    for (int row = 0; row < Reservation.TOTAL_PLANE_ROWS; row++) {
		        for (int seat = 0; seat < Reservation.TOTAL_PLANE_COL; seat++) {
		        	planeSeatReservationMap[row][seat] = Seat.EMPTY_SEAT_ID;  
		        }
		    }
		} else { //load existing reservation

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
		
		return planeSeatReservationMap;
	}
 
	public static Map<String, Passenger> loadPassangerSeatReservationMap() {

		Map<String, Passenger> passangerSeatReservationMap = new HashMap<String, Passenger>();
		
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
		
		return passangerSeatReservationMap;
	}
}
