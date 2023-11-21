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
	public Employee createNewEmployee(String name, String passCode) throws SQLException {
		PreparedStatement statement = getPreparedStatement(
				"INSERT INTO EmployeeTable(name, passCode) VALUES(?, ?)"
				);
		statement.setString(1, name);
		statement.setString(2, passCode);
		statement.execute();
		
		// Get the generated auto-incrementing primary key
		// Which will be the ID of the employee
		ResultSet result = statement.getGeneratedKeys();
		if(result.next()) {
			// the auto generated primary-key will be the only column in this result set
			int id = result.getInt(1);
			return new Employee(id, name, passCode);
		}
		return null;
	}
	
	@Override
	public boolean deleteEmployee(long employeeId) throws SQLException {
		PreparedStatement statement = getPreparedStatement(
				"DELETE FROM EmployeeTable WHERE id=?"
				);
		statement.setLong(1, employeeId);
		return statement.execute();
	}
	
	protected final PreparedStatement getPreparedStatement(String sql) throws SQLException {
		  return conn.prepareStatement(sql);
	  }

}
