package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import admin.Employee;
import middle.admin.EmployeeManager;

public class DBEmployeeManager implements EmployeeManager {
	
	private Connection conn; // connection to database
	
	public DBEmployeeManager() throws Exception {
		DBAccess driver = new DBAccessFactory().getNewDBAccess();
		driver.loadDriver();
		
		conn = DriverManager.getConnection(driver.urlOfDatabase(), driver.username(), driver.password());
		conn.setAutoCommit(true);
	}

	@Override
	public Employee getEmployee(long employeeId) throws SQLException {
		PreparedStatement statement = getPreparedStatement(
				"SELECT * FROM EmployeeTable WHERE id=?"
				);
		statement.setLong(1, employeeId);
		ResultSet result = statement.executeQuery(); // should only return 1
		if(!result.next())
			return null; // no employee details with this id were found
		
		String name = result.getString("name");
		String passCode = result.getString("passCode");
		return new Employee(employeeId, name, passCode);
	}
	
	@Override
	public Employee createNewEmployee(String name, String passCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void deleteEmployee(long employeeId) {
		// TODO Auto-generated method stub
		
	}
	
	protected final PreparedStatement getPreparedStatement(String sql) throws SQLException {
		  return conn.prepareStatement(sql);
	  }

}
