/**
 * class that represents the seats in the plane
 * @author nimra
 *
 */
public class PlaneSeat {
	private String seatType;
	private String seatNum;
	private Passenger passenger;
	
	/**
	 * constructor that builds seats in plane
	 * @param num-seat number
	 * @param prefClass- class where seat is
	 */
	public PlaneSeat (String num, String prefClass) {
		passenger = null;
		this.seatNum = num;
		
		if(prefClass.equalsIgnoreCase("E")) {
			if(num.contains("C") || num.contains("D")) {
				this.seatType = "A";
			}else if (num.contains("F") || num.contains("A")) {
				this.seatType = "W";
			}else {
				this.seatType = "C";
			}
		}else if (prefClass.equalsIgnoreCase("F")) {
			if(num.contains("A") || num.contains("D")) {
				this.seatType = "W";
			}else {
				this.seatType = "A";
			}
			
		}
	}
	/**
	 * getter to get seat number
	 * @return seatNum - seat number
	 */

	public String getSeatNum() {
		return seatNum;
	}
	
	/**
	 * Gets seat type
	 * @return window, aisle, or center
	 */
	public String getSeatType() {
		return seatType;
	}
	
	/**
	 * getter to get the passenger thats given a seat
	 * @return- passenger
	 */
	public Passenger getPassenger() {
		return passenger;
	}
	
	/**
	 * a setter to set the passenger to a specific seat
	 * @param taken- where the passenger is seat
	 */
	public void setPassenger(Passenger taken) {
		passenger = taken;
	}


}


