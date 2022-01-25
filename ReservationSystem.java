import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class that represents a plane seat 
 * reservation through user input
 * @author nimra
 *
 */

public class ReservationSystem {

	public static void main(String[] args) {
		Plane plane = new Plane();
		if (args.length == 1) {
			readFile(plane, args[0]);
		}
		Scanner input = new Scanner(System.in);
		String readInput = "";

		while (true) {
			System.out.println("Add [P]assenger, Add [G]roup, [C]ancel Reservations,"
					+ " Print Seating [A]vailability Chart, Print [M]anifest, [Q]uit");
			readInput = input.nextLine().toUpperCase();
			if (readInput.length() == 0) {
				System.out.println("Input is invalid, try again!");
			} else if (readInput.charAt(0) == 'P') {
				addPassenger(plane, input);
			} else if (readInput.charAt(0) == 'G') {
				addGroup(plane, input);
			} else if (readInput.charAt(0) == 'C') {
				cancel(plane, input);
			} else if (readInput.charAt(0) == 'A') {
				System.out.println("Choose a class: Economy or First");
				readInput = input.nextLine();

				if (readInput.equalsIgnoreCase("First")) {
					System.out.println(plane.getAvailableSeats("First"));
				} else if (readInput.equalsIgnoreCase("Economy")) {
					System.out.println(plane.getAvailableSeats("Economy"));
				} else {
					System.out.println("Input is invalid.");
				}
			} else if (readInput.charAt(0) == 'M') {
				getPlaneManifest(plane, input);
			} else if (readInput.charAt(0) == 'Q') {
				if (args.length == 1) {
					storeInfo(plane, args[0]);
				} else {
					System.out.println("Couldn't save flight Information");
				}
				System.exit(0);
			} else {
				System.out.println("Input is invalid, try again!");
			}
		}
	}

	/**
	 * saves info of all the passengers into a file
	 * @param plane
	 * @param file
	 */
	private static void storeInfo(Plane plane, String file) {
		// TODO Auto-generated method stub
		File result = new File(file);
		try {
			PrintWriter save = new PrintWriter(new FileWriter(file));
			save.println("First 1-2, Left: A-B, Right: C-D; Economy 10-29, Left A-C, Right: D-F");
			String flightInfo = plane.getFlightInfo();
			save.print(flightInfo);
			save.close();
		} catch (IOException e) {
			System.out.println("\n Flight Info is saved to: " + file);
		}

	}
	
	/**
	 * a method that works with the process of accessing
	 * the manifest 
	 * @param plane
	 * @param reading
	 */

	private static void getPlaneManifest(Plane plane, Scanner reading) {
		String prefClass;
		System.out.println("For which class would you like to view the manifest?");
		prefClass = reading.nextLine();

		System.out.println(plane.getManifest(prefClass));
	}

	/**
	 * a method that works with the process of cancelling
	 * a particular reservation
	 * @param plane
	 * @param reading
	 */
	private static void cancel(Plane plane, Scanner reading) {
		// TODO Auto-generated method stub
		String reader;
		String isReserved = "";
		System.out.println("Choose an option to cancel: [I]ndividual or [G]roup?");
		reader = reading.nextLine().toUpperCase();

		if (reader.charAt(0) == 'G') {
			System.out.println("Enter group name: ");
			reader = reading.nextLine();
			isReserved = plane.cancelGroupReservation(reader);
		} else if (reader.charAt(0) == 'I') {
			System.out.println("Enter passenger name: ");
			reader = reading.nextLine();
			isReserved = plane.cancelReserved(reader);
		}

		if (isReserved.equals("Your reservation has successfully been cancelled.")) {
			System.out.println("Reservation is cancelled for: " + reader + "\n");
		} else {
			System.out.println("Cancellation failed");
		}

	}

	/**
	 * a method that works with the process of 
	 * adding a passenger
	 * @param plane
	 * @param reading
	 */
	private static void addPassenger(Plane plane, Scanner reading) {
		// TODO Auto-generated method stub
		String name;
		String prefSeat;
		String prefClass;
		String returnString;

		do {
			System.out.println("Please enter your name: ");
			name = reading.nextLine();
			if (name.contains(",")) {
				System.out.println("no commas allowed, try again");
			}
		} while (name.contains(","));

		do {
			System.out.println("Please enter your preferred class: ");
			prefClass = reading.nextLine().toUpperCase();

			if (!prefClass.equalsIgnoreCase("Economy") && !prefClass.equalsIgnoreCase("First")) {
				System.out.println("Only choose Economy or First, try again");
			}
		} while ((prefClass.charAt(0) != 'E') && (prefClass.charAt(0) != 'F'));

		do {
			if (prefClass.substring(0, 1).equalsIgnoreCase("F")) {
				System.out.println("Enter your seat preference: [W]indow or [A]isle");
			} else if (prefClass.substring(0, 1).equalsIgnoreCase("E")) {
				System.out.println("Enter your seat preference: [W]indow, [A]isle, or [C]enter");
			}
			prefSeat = reading.nextLine().toUpperCase();
			Passenger pass = new Passenger(name, prefClass, null);
			returnString = plane.addPassenger(pass, prefSeat);

			if (returnString.equals("")) {
				System.out.println(
						"There are no seats are available according to your preference. Try a different option");
			} else if (returnString.equals("There are no seats available at this time.")) {
				System.out.println("Sorry, the plane is full");
			} else {
				System.out.println("Passenger is seated at: " + returnString + "\n");
				System.out.println("Sucessfully booked your seat!" + "\n");
			}
		} while (returnString.equals(""));

	}

	/**
	 * a method that works with the process
	 * of adding a group in the plane
	 * @param plane
	 * @param reading
	 */
	private static void addGroup(Plane plane, Scanner reading) {
		// TODO Auto-generated method stub
		String groupName;
		String groupMem;
		String prefClass;

		do {
			System.out.println("Enter name of group: ");
			groupName = reading.nextLine();
			if (groupName.contains(",")) {
				System.out.println("Enter group name without comma.");
			}
		} while (groupName.contains(","));
		System.out.println("Enter group member names (comma separated): ");
		groupMem = reading.nextLine();
		do {
			System.out.println("Choose your service class: [E]conomy or [F]irst: ");
			prefClass = reading.nextLine();
			if ((prefClass.charAt(0) != 'E') && (prefClass.charAt(0) != 'F')) {
				System.out.println("Please enter a valid option (F or E)");
			}
		} while ((prefClass.charAt(0) != 'E') && (prefClass.charAt(0) != 'F'));

		int totalNames = 1; // at least one group member
		for (int i = 0; i < groupMem.length(); i++) {
			if (groupMem.charAt(i)== ',') {
				totalNames++;
			}
		}
		String[] groupNameArray = new String[totalNames];
		groupNameArray = groupMem.split(",");

		ArrayList<Passenger> groupAdding = new ArrayList<Passenger>();

		for (int i = 0; i < totalNames; i++) {
			groupNameArray[i] = groupNameArray[i].trim();
			groupAdding.add(new Passenger(groupNameArray[i], prefClass, groupName));
		}
		String returnString = plane.addGroup(groupAdding);
		if (returnString != null) {
			System.out.println(returnString);
			System.out.println("Successfully booked seats" + "\n");
		} else {
			System.out.println("This service class is full. \n");
		}

	}

	/**
	 * a method that takes a command line input
	 * @param plane
	 * @param fileName
	 */
	private static void readFile(Plane plane, String fileName) {
		// TODO Auto-generated method stub
		File file = new File(fileName);

		if (file.exists()) {
			String group;
			String readFile;
			int seatNum;
			String[] lineInfo = new String[4];

			try {
				Scanner read = new Scanner(file);
				read.nextLine();
				while (read.hasNextLine()) {
					readFile = read.nextLine();
					lineInfo = readFile.split(",");
					seatNum = Integer.parseInt(lineInfo[0].substring(0, 1));
					if (lineInfo.length == plane.fileParam - 1) {
						group = null;
					} else {
						group = lineInfo[plane.fileParam - 1];
					}
					plane.addDetails(new Passenger(lineInfo[2], lineInfo[0], group), seatNum);
				}

			} catch (FileNotFoundException notFound) {
				System.out.println("File not found");
			}
		}else {
			
			File newFile = new File(fileName);
			try {
				if (newFile.createNewFile()) {
					System.out.println("File created: " + newFile.getName());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
