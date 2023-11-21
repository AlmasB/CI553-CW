package admin;

import middle.admin.EmployeeManager;

public class Employee {
	
	private final long id;
	private String name;
	private String passCode;
	
	public Employee(long id) {
		this.id = id;
	}
	
	public Employee(long id, String name, String passCode) {
		this.id = id;
		this.passCode = passCode;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassCode() {
		return passCode;
	}
	
	/**
	 * Sets the employee's passcode to the provided passcode
	 * Only sets the code if it is valid
	 * @param passCode to set
	 * @return true if the code is valid and was setS
	 */
	public boolean setPassCode(String passCode) {
		if(EmployeeManager.isPassCodeValid(passCode)) {
			this.passCode = passCode;
			return true;
		}
		return false;
	}
}
