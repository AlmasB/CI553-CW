package dbAccess;
import java.sql.*;

/**
  * Implements management of an mySQL database on Linux.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
class LinuxAccess extends DBAccess
{
  public void loadDriver() throws Exception
  {
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

  }

  public String urlOfDatabase()
  {
    return "jdbc:mysql://lb1587_CatShop:Twothousandandfour007@lb1587.brighton.domains/lb1587_CatShop";
  }


}


