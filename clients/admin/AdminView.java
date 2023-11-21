package clients.admin;

import javax.swing.RootPaneContainer;

import middle.MiddleFactory;
import middle.admin.EmployeeManager;

public class AdminView {
	
	private EmployeeManager employeeManager;
	
	public AdminView(RootPaneContainer root, MiddleFactory mf, int x, int y) {
		try {
			this.employeeManager = mf.makeEmployeeManager();
			System.out.println(employeeManager.getEmployee(1).getPassCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
