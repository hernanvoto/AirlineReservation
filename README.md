–
–
–
–
–
–
–
AirConsole Reservation System
You have been hired as a developer for AirConsole Pty
Ltd. Your task is to develop a new automated
reservations system. You are to write a new application
to assign seats on each flight serviced by the airline.
Specifications
There is only one route going from Madrid to Barcelona
There is only one airplane. The airplane has 40 seats. There are 8
rows. There are 5 seats on each row. Denominated seat A-E. The seat
number is a combination of the row number and the seat letter.
There are 2 seat classes. Rows 1 to 5 are business class rows. The
remaining number of rows are Economy class.
All user input must be validated
The initial task only covers seat reservation. So you don’t need to ask
the Date of Departure and offer Flight Date/Time selection
All passenger details are saved in a flight manifest file and loaded next
time the application is started
This will be a single terminal application. So thread safety and
concurrent access is not an issue.
Below is an example interaction in the user interface:
*******************************
** Welcome to AirConsole **
*******************************
Task Selection
R: Reservation
S: Seat Verification
X: Exit the System
Please enter the task you want to perform: R
*******************************
** Seat Class **
*******************************
Seat Class Selection:
B: Business Class
E: Economy Class
Please enter the seat class you want to reserve: A
Invalid Entry! Please try again.
Please enter the seat class you want to reserve: B
*******************************
** Business Class **
*******************************
A B C D E
1 X X X X X
2 X X
3 X X X X
4 X X X X X
5 X X X X X
Please enter the row number: 10
Invalid Entry! Please try again.
Please enter the row number: A
Invalid Entry! Please try again.
Please enter the row number: 1
Please enter the seat letter: X
Invalid Entry! Please try again.
Please enter the seat letter: 1
Invalid Entry! Please try again.
Please enter the seat letter: A
Sorry seat 1A is already taken.
Please enter the row number: 2
Please enter the seat letter: B
Seat 2B is available.
Please enter the passenger's firstname: Jake
Please enter the passenger's lastname: Sta. Teresa
Please enter the passenger's passport number: ABC123456
Seat 2B was successfully booked!
*******************************
** Welcome to AirConsole **
*******************************
Task Selection
R: Reservation
S: Seat Verification
X: Exit the System
Please enter the task you want to perform: S
*******************************
** Seat Verification **
*******************************
Please enter the row number: 2
Please enter the seat letter: B
Passenger details
Firstname: Jake
Lastname: Sta. Teresa
Passport Number: ABC123456
Press any key to continue...
*******************************
** Welcome to AirConsole **
*******************************
Task Selection
R: Reservation
B: Boarding
X: Exit the System
Please enter the task you want to perform: X
