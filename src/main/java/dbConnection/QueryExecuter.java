package dbConnection;

import java.sql.Connection;

public class QueryExecuter {
    Connection connection;

    QueryExecuter(Connection connection) {
        this.connection = connection;
    }


}
