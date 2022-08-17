package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLDatabaseConnection {

    private static Connection connection;

    public static Connection getConnection(String db) {
        String connectionUrl =
                "jdbc:sqlserver://MSQ-NBW-6894\\G3SQL01:1433;"
                        + "database=" + db + ";"
                        + "user=sa;"
                        + "password=IDeaS123;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        try {
            return DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
