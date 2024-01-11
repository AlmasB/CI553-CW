package dbAccess;

import java.sql.*;
public class KeywordSearch{
    public void keywordSearch(String keyword) throws SQLException, ClassNotFoundException {
        // Database connection
        String URL = "jdbc:mysql://lb1587_CatShop:Twothousandandfour007@lb1587.brighton.domains/lb1587_CatShop";
        String User = "lb1587_CatShop";
        String Password = "Twothousandandfour007";

        Class.forName("com.mysql.cj.jdbc.Driver"); // database driver
        Connection connection = DriverManager.getConnection(URL, User, Password); // connect
        Statement statement = connection.createStatement();

try {
    ResultSet resultSet = statement.executeQuery("SELECT * FROM ProductTable WHERE description LIKE " + "'%" + keyword + "%' OR productNo LIKE " + "'%" + keyword + "%';"); // sql query!
    // output to console
    while (resultSet.next()) {
        System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
    }
    connection.close();
} catch (SQLException e){
    System.out.println(e);
}

    }
}


