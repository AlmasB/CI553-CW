package dbAccess;



//import java.sql.Connection;
//import java.sql.DriverManager;

/**
  * Implements management of an mySQL database on Linux.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */

//O2aZ6542FAJs
class LinuxAccess extends DBAccess
{
  @SuppressWarnings("deprecation")
public void loadDriver() throws Exception{
	 
		    // Load the MySQL driver
		    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();	    
  	}

  

  public String urlOfDatabase(){
    return "jdbc:mysql://jaw120:Hencev8b@jaw120.brighton.domains/jaw120";

  }
}





