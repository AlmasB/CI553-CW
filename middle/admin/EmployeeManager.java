package middle.admin;

import admin.Employee;

public interface EmployeeManager {
	
	//TODO: Clean up throws exceptions
	
	/**
	 * Gets the employee from their id
	 * @param employeeId the id to get
	 * @return the employee object
	 * @throws  
	 */
	Employee getEmployee(long employeeId) throws Exception;
	
	/**
	 * Creates a new employee and inserts it into the database
	 * @param name The name, can be null
	 * @param passCode The pass-code. If null, will default to '0000'.
	 * @return The employee object after inserting into the table
	 */
	Employee createNewEmployee(String name, String passCode);
	
	/**
	 * Deletes the employee
	 * @param employeeId The id to delete
	 */
	void deleteEmployee(long employeeId);
	
	/**
	 * Checks whether the passcode is valid (only contains digits 0-9)
	 * and of length 4-16
	 * @param code the code to check
	 * @return true if the passcode is valid and can be used
	 */
	public static boolean isPassCodeValid(String code) {
		if(code.length() < 4 || code.length() > 16) return false;
		
		for(int i = 0; i < code.length(); i++) {
			if(!Character.isDigit(code.charAt(i))) return false;
		}
		
		return true;
	}

}
