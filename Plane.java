import java.util.ArrayList;

/**
 * a class that represents the plane's main functions and attributes (economy
 * class and first class)
 * 
 * @author nimra
 *
 */

public class Plane {
	private static ArrayList<PlaneSeat> firstClassSeats;
	private static ArrayList<PlaneSeat> economyClassSeats;

	private static final int econRows = 20;
	private static final int econColumns = 6;
	private static final int econStarting = 10;
	private static final int econSize = (econRows * econColumns);
	private int econSeatsLeft = econSize;

	private static final int firstClassRows = 2;
	private static final int firstClassColumns = 4;
	private static final int firstClassSize = (firstClassRows * firstClassColumns);
	private static int firstClassSeatsLeft = firstClassSize;

	// passengers information from file
	public static int fileParam = 4;

	/**
	 * constructor to occupy seats
	 */

	public Plane() {
		firstClassSeats = occupyFirstClass();
		economyClassSeats = occupyEconomyClass();
	}

	/**
	 * private helper that fills seats in first class
	 * 
	 * @return firstClassSeats- as an arrayList
	 */

	private ArrayList<PlaneSeat> occupyFirstClass() {
		firstClassSeats = new ArrayList<PlaneSeat>();
		String passengerSeat = "";

		for (int i = 1; i <= firstClassRows; i++) {
			for (int k = 1; k <= firstClassColumns; k++) {
				passengerSeat += 1;
				if (k == 1)
					passengerSeat += "A";
				if (k == 2)
					passengerSeat += "B";
				if (k == 3)
					passengerSeat += "C";
				if (k == 4)
					passengerSeat += "D";
				firstClassSeats.add(new PlaneSeat(passengerSeat, "F"));
				passengerSeat = "";
			}
		}
		return firstClassSeats;
	}

	/**
	 * private helper that fills seats in economy class
	 * 
	 * @return economyClassSeats- as an arrayList
	 */

	private ArrayList<PlaneSeat> occupyEconomyClass() {
		economyClassSeats = new ArrayList<PlaneSeat>();
		String passengerSeat = "";
		for (int i = econStarting; i <= econRows + econStarting; i++) {
			for (int k = 1; k <= econColumns; k++) {
				passengerSeat += i;
				if (k == 1)
					passengerSeat += "A";
				else if (k == 2)
					passengerSeat += "B";
				else if (k == 3)
					passengerSeat += "C";
				else if (k == 4)
					passengerSeat += "D";
				else if (k == 5)
					passengerSeat += "E";
				else if (k == 6)
					passengerSeat += "F";

				economyClassSeats.add(new PlaneSeat(passengerSeat, "E"));
				passengerSeat = "";
			}
		}
		return economyClassSeats;
	}

	/**
	 * adds info of passengers from a given file
	 * 
	 * @param p
	 * @param seat
	 */

	public void addDetails(Passenger p, int seat) {
		if (p.getUserPrefClass().equalsIgnoreCase("F")) {
			firstClassSeats.get(seat).setPassenger(p);
			firstClassSeatsLeft--;
		} else if (p.getUserPrefClass().equalsIgnoreCase("E")) {
			economyClassSeats.get(seat).setPassenger(p);
			econSeatsLeft--;
		}
	}

	/**
	 * a getter that get's the airplan's manifest
	 * 
	 * @param prefClass- the user's desired class
	 * @return manifestResult - to return manifest
	 */

	public String getManifest(String prefClass) {
		String manifestResult = "";
		int m = 0;

		if (prefClass.equalsIgnoreCase("First")) {
			manifestResult += "First\n";

			for (m = 0; m < firstClassSize; m++) {
				if (firstClassSeats.get(m).getPassenger() != null) {
					manifestResult += firstClassSeats.get(m).getSeatNum() + ": ";
					manifestResult += firstClassSeats.get(m).getPassenger().getPassName() + "\n";
				}
			}
		} else if (prefClass.equalsIgnoreCase("Economy")) {
			manifestResult += "Economy\n";
			for (m = 0; m < econSize; m++) {
				if (economyClassSeats.get(m).getPassenger() != null) {
					manifestResult += economyClassSeats.get(m).getSeatNum() + ": ";
					manifestResult += economyClassSeats.get(m).getPassenger().getPassName() + "\n";
				}
			}
		}
		return manifestResult;
	}

	/**
	 * a getter to get all the vacant seats in the plane
	 * 
	 * @param prefClass- user's desired class to view the vacant seats for
	 * @return returnString - returns availability for the certain class
	 */

	public String getAvailableSeats(String prefClass) {
		boolean seatAvail = false;
		String returnString = "";
		int m = 0;

		if (prefClass.equalsIgnoreCase("First")) {
			returnString = "First: ";

			for (int row = 1; row <= firstClassRows; row++) {
				seatAvail = false;

				for (int column = 1; column <= firstClassColumns; column++) {
					if (firstClassSeats.get(m).getPassenger() == null) {
						if (seatAvail == false) {
							returnString += ("\n" + row + ": ");
							seatAvail = true;
						} else {
							returnString += ",";
						}
						returnString += firstClassSeats.get(m).getSeatNum().charAt(1);
					}
					m++;
				}
			}
		} else if (prefClass.equalsIgnoreCase("Economy")) {
			returnString += "Economy: ";
			m = 0;

			for (int row = econStarting; row < econRows + econStarting; row++) {
				seatAvail = false;

				for (int column = 1; column <= econColumns; column++) {
					if (economyClassSeats.get(m).getPassenger() == null) {
						if (seatAvail == false) {
							returnString += ("\n" + row + ": ");
							seatAvail = true;
						} else {
							returnString += ",";
						}
						returnString += economyClassSeats.get(m).getSeatNum().charAt(2);
					}
					m++;
				}
			}
			returnString += "\n";
		}
		return returnString;
	}

	/**
	 * adds a passenger that the user enters
	 * 
	 * @param p-        passenger that needs to be added
	 * @param prefSeat- user's desired seat
	 * @return a seat for the passenger or no available seats message
	 */

	public String addPassenger(Passenger p, String prefSeat) {
		int m;

		if (p.getUserPrefClass().equalsIgnoreCase("Economy")) {
			if (econSeatsLeft == 0) {
				return "There are no seats available at this time.";
			}
			for (m = 0; m < econSize; m++) {
				if (economyClassSeats.get(m).getPassenger() == null
						&& economyClassSeats.get(m).getSeatType().equals(prefSeat)) {
					econSeatsLeft--;
					economyClassSeats.get(m).setPassenger(p);

					return "Your seat: " + economyClassSeats.get(m).getSeatNum();

				}
			}
			return "Your preferred seat is not available";

		} else if (p.getUserPrefClass().equalsIgnoreCase("First")) {
			if (firstClassSeatsLeft == 0) {
				return "There are no seats available at this time.";
			}
			for (m = 0; m < firstClassSize; m++) {
				if (firstClassSeats.get(m).getPassenger() == null
						&& firstClassSeats.get(m).getSeatType().equalsIgnoreCase(prefSeat)) {
					firstClassSeatsLeft--;
					firstClassSeats.get(m).setPassenger(p);

					return "Your seat: " + firstClassSeats.get(m).getSeatNum();

				}
			}
			return "Sorry your preferred seat is not available.";
		}
		return "Please enter a valid class (first or economy).";
	}

	/**
	 * adds group that user enters
	 * 
	 * @param group= an arrayList that manages passengers of a group to be seated
	 *               together
	 * @return string that gives the passenger's name and seat
	 */

	public String addGroup(ArrayList<Passenger> group) {
		int x, y;
		int z = 0;
		int maximumEmpty = 0;
		int adjEmpty = 0;

		ArrayList<Integer> memberList = new ArrayList<Integer>();

		String groupSeating = "";

		if (group.get(0).getUserPrefClass().equals("Economy") && econSeatsLeft <= group.size()) {
			return "No seats available right now.";

		} else if (group.get(0).getUserPrefClass().equalsIgnoreCase("First") && firstClassSeatsLeft <= group.size()) {
			return "No seats available right now.";
		}
		if (group.get(0).getUserPrefClass().equalsIgnoreCase(("Economy"))) {
			for (x = group.size(); x > 0; x--) {
				for (y = 0; y < econSize; y++) {
					if ((y % econColumns == 0) || (y == econSize - 1)) {
						if (maximumEmpty < adjEmpty) {
							maximumEmpty = adjEmpty;

							for (z = maximumEmpty; z >= 1; z--)
								memberList.add(Math.abs(z - y));

						}
						if (maximumEmpty >= x) {
							while (!group.isEmpty() && !memberList.isEmpty()) {
								economyClassSeats.get(memberList.get(0)).setPassenger(group.get(0));
								groupSeating += (group.get(0).getPassName() + " is at the seat: "
										+ economyClassSeats.get(memberList.get(0)).getSeatNum() + "\n");
								memberList.remove(0); // removing first passenger
								group.remove(0); // ""
							}
						}
						maximumEmpty = 0; adjEmpty = 0;
						memberList.clear();
					}
					if (economyClassSeats.get(y).getPassenger() != null) {
						if (maximumEmpty < adjEmpty) {
							memberList.clear();
							maximumEmpty = adjEmpty;
							for (z = 1; z <= maximumEmpty; z++)
								memberList.add(y - z);
						}
						adjEmpty = 0;
					} else {
						adjEmpty++;
					}
				}
			}

		} else if (group.get(0).getUserPrefClass().equalsIgnoreCase("First")) {
			for (x = group.size(); x > 0; x--) {
				for (y = 0; y < firstClassSize; y++) {
					if ((y == firstClassSize) || ((y % firstClassColumns) == 0)) {
						if (maximumEmpty < adjEmpty) {
							maximumEmpty = adjEmpty;

							for (z = maximumEmpty; z >= 1; z--)
								memberList.add(Math.abs(z - y));
						}
						if (maximumEmpty >= x) {
							while (!group.isEmpty() && !memberList.isEmpty()) {
								firstClassSeats.get(memberList.get(0)).setPassenger(group.get(0));
								groupSeating += (group.get(0).getPassName() + "is at the seat: "
										+ firstClassSeats.get(memberList.get(0)).getSeatNum() + "\n");
								group.remove(0);
								memberList.remove(0);

							}
						}
						memberList.clear();
						maximumEmpty = 0;
						adjEmpty = 0;
					}
					if (firstClassSeats.get(y).getPassenger() != null) {
						if (maximumEmpty < adjEmpty) {
							memberList.clear();
							maximumEmpty = adjEmpty;

							for (z = 1; z <= maximumEmpty; z++) {
								memberList.add(y - z);
							}
						}
						adjEmpty = 0;
					} else {
						adjEmpty++;
					}
				}
			}
		}
		return groupSeating; // where the group has sat
	}

	/**
	 * method to get the flight's info from data collected to be stored into a file
	 * 
	 * @return string with all flight info
	 */

	public String getFlightInfo() {
		String returnString = "";
		for (int i = 0; i < firstClassSize; i++) {
			if (firstClassSeats.get(i).getPassenger() != null) {
				returnString += (firstClassSeats.get(i).getSeatNum() + ", ");
				if (firstClassSeats.get(i).getPassenger().getGroupMem() == null) {
					returnString += ("I");
				} else {
					returnString += ("G, ");
					returnString += (firstClassSeats.get(i).getPassenger().getGroupMem());
				}
				returnString += (", " + firstClassSeats.get(i).getPassenger().getPassName());
				returnString += "\n";
			}
		}
		for (int i = 0; i < econSize; i++) {
			if (economyClassSeats.get(i).getPassenger() != null) {
				returnString += (economyClassSeats.get(i).getSeatNum() + ", ");
				if (economyClassSeats.get(i).getPassenger().getGroupMem() == null) {
					returnString += ("I");
				} else {
					returnString += ("G, ");
					returnString += (economyClassSeats.get(i).getPassenger().getGroupMem());
				}
				returnString += (", " + economyClassSeats.get(i).getPassenger().getPassName());
				returnString += "\n";
			}
		}
		return returnString;
	}

	/**
	 * method that removes an individual passenger from the aircraft
	 * 
	 * @param passName- passenger's name
	 * @return string that gives a successful cancellation message or an error
	 *         message that it could not be cancelled
	 */

	public String cancelReserved(String passName) {
		String returnString = "";
		boolean isReserved = false;

		for (int x = 0; x < firstClassSize; x++) {
			if (firstClassSeats.get(x).getPassenger() != null) {
				if (passName.equalsIgnoreCase(firstClassSeats.get(x).getPassenger().getPassName())) {
					firstClassSeatsLeft++;
					firstClassSeats.get(x).setPassenger(null);
					isReserved = true;
				}
			}
		}
		for (int x = 0; x < econSize; x++) {
			if (economyClassSeats.get(x).getPassenger() != null) {
				if (passName.equalsIgnoreCase(economyClassSeats.get(x).getPassenger().getPassName())) {
					econSeatsLeft++;
					economyClassSeats.get(x).setPassenger(null);
					isReserved = true;
				}
			}
		}
		if (isReserved) {
			returnString = "Your reservation has successfully been cancelled.";
		} else {
			returnString = "No reservation found";
		}
		return returnString;
	}

	/**
	 * method that cancels a group's reservation depending on their name
	 * 
	 * @param name - group name
	 * @return string that return a successful cancellation message or an error
	 *         message that it could not be cancelled
	 */
	public String cancelGroupReservation(String name) {
		boolean isReserved = false;
		String returnString = "";

		for (int i = 0; i < econSize; i++) {
			if (economyClassSeats.get(i).getPassenger() != null) {
				if (name.equalsIgnoreCase(economyClassSeats.get(i).getPassenger().getGroupMem())) {
					economyClassSeats.get(i).setPassenger(null);
					econSeatsLeft++;
					isReserved = true;

				}
			}
		}
		for (int i = 0; i < firstClassSize; i++) {
			if (firstClassSeats.get(i).getPassenger() != null) {
				if (name.equalsIgnoreCase(firstClassSeats.get(i).getPassenger().getGroupMem())) {
					firstClassSeats.get(i).setPassenger(null);
					firstClassSeatsLeft++;
					isReserved = true;
				}
			}
		}
		if (isReserved) {
			returnString = "Your reservation has successfully been cancelled.";
		} else {
			returnString = "No reservation found";
		}
		return returnString;
	}

}
