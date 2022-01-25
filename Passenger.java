
public class Passenger {
	private String passName;
	private String userPrefClass;
	private String groupMem;
	
	/**
	 * constructor that creates a passenger
	 * @param userName - passenger name
	 * @param userServiceClass - First class or economy
	 * @param groupMem - Group or Individual Passenger
	 */
	public Passenger(String passName, String userPrefClass, String groupMem) {
		this.passName = passName;
		this.userPrefClass = userPrefClass;
		this.groupMem = groupMem;
	}
	
	/**
	 * getter for getting passenger's name
	 * @return passName - name of Passenger
	 */
	
	public String getPassName() {
		return passName;
	}
	
	/**
	 * getter to get passenger's desired class
	 * @return userPrefClass - the desired class
	 */
	
	public String getUserPrefClass() {
		return userPrefClass;
	}
	/**
	 * getter to get group
	 * @return groupMem - passenger's group
	 */
	public String getGroupMem() {
		return groupMem;
	}
}
