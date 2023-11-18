package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;

/**
  * Implements management of an mySQL database on Linux.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
class LinuxAccess extends DBAccess
{
  public void loadDriver() throws Exception
  {
    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
  }

  public String urlOfDatabase()
  {
    return "jdbc:mysql://localhost/catshop";
    // return "jdbc:mysql://localhost:3306/reservation_table";
    // return "jdbc:mysql://ps750_CI553:@ps750.brighton.domains/ps750_CI553";
  }
  // String myDriver = "org.gjt.mm.mysql.Driver";
  // String myUrl = "jdbc:mysql://localhost/test";
  // // Class.forName(myDriver);
  // Connection conn = DriverManager.getConnection(myUrl, "root", "");

}
