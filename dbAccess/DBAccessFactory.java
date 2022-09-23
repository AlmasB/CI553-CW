/**
 * @author  Mike Smith University of Brighton
 * @version 3.0
 */

package dbAccess;

import debug.DEBUG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
  * Manages the starting up of the database.
  * The database may be Access, mySQL etc.
  */

// Pattern: Abstract Factory
//          Fix to be 

public class DBAccessFactory
{
  private static String theAction   = "";
  private static String theDataBase = "";
  private static String theOS       = "";

  public static void setAction( String name )
  {
    theAction = name;
  }

  private static String setEnvironment()
  {
    theDataBase  = fileToString( "DataBase.txt" ) + theAction;
    String os    = System.getProperties().getProperty( "os.name" );
    String arch  = System.getProperties().getProperty( "os.arch" );
    String osVer = System.getProperties().getProperty( "os.version" );
    theOS = String.format("%s %s %s",  os, osVer, arch );
    System.out.println( theOS );
    return theOS;
  }
  
  /**
   * Return an object to implement system level access to the database.
   * @return An object to provide system level access to the database
   */
  public DBAccess getNewDBAccess()
  {
    setEnvironment();
    DEBUG.traceA("Using [%s] as database type\n", theDataBase );
    switch ( theDataBase )
    {
       case "Derby" :
         return new DerbyAccess();       // Derby
    
       case "DerbyCreate" :
         return new DerbyCreateAccess(); // Derby & create database
    
       case "Access" :
       case "AccessCreate" :
         return new WindowsAccess();     // Access Windows
    
       case "mySQL" :
       case "mySQLCreate" :
         return new LinuxAccess();       // MySQL Linux
         
       default:
         DEBUG.error("DataBase [%s] not known\n", theDataBase );
         System.exit(0);
    }
    return new DBAccess();               // Unknown
  }
  
  /**
   * return as a string the contents of a file
   * stripping out newline and carriage returns from contents
   * @param file File name
   * @return contents of a file as a string
   */
  private static String fileToString( String file )
  {
    byte[] vec = fileToBytes( file );
    return new String( vec ).replaceAll("\n","").replaceAll("\r","");
  }

  /**
   * Return contents of file as a byte vector
   * @param file File name
   * @return contents as byte array
   */
  private static byte[] fileToBytes( String file )
  {
    byte[] vec = new byte[0];
    try
    {
      final int len = (int) length( file );
      if ( len < 1000 )
      {
        vec = new byte[ len ];
        FileInputStream istream = new FileInputStream( file );
          final int read = istream.read(vec, 0, len);
          istream.close();
        return vec;
      } else {
        DEBUG.error("File %s length %d bytes too long",
                     file, len );
      }
    }
    catch ( FileNotFoundException  err )
    {
      DEBUG.error("File does not exist: fileToBytes [%s]\n", file );
      System.exit(0);
    }
    catch ( IOException err )
    {
      DEBUG.error("IO error: fileToBytes [%s]\n", file );
      System.exit(0);
    }
    return vec;
  }

  /**
   * return number of characters in file
   * @param path File name
   * @return Number of characters in file
   */
  private static long length( String path )
  {
    try
    {
      File in = new File( path );
      return in.length();
    }
    catch (SecurityException err )
    {
      DEBUG.error("Security error: length of file [%s]\n", path);
      System.exit(0);
    }
    return -1;
  }

}

