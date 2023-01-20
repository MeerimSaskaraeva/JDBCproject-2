package java8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection(){
        try {
            //System.out.println("Successfully connected to database... ");
           return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/dao",
                    "postgres",
                    "2202"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
