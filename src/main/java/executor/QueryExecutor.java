package executor;

import dbConnection.SQLDatabaseConnection;
import queryReader.QueryReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutor {

    String query;
    String db;
    String fileName;
    List<String> parameters;
    ResultSet rs;
    long executingTime;

    public QueryExecutor(String query, List<String> parameters) {
        this.query = query;
        this.db = parameters.get(0);
        this.parameters = parameters;
    }

    public void executeQuery() {
        Connection con = SQLDatabaseConnection.getConnection(db);

        try (PreparedStatement pstm = con.prepareStatement(query) {

            for (int i = 1; i < parameters.size(); i++) {
                pstm.setString(i, parameters.get(i));
            }

            long startTime = System.currentTimeMillis();
            rs = pstm.executeQuery();
            executingTime = System.currentTimeMillis() - startTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getExecutingTime() {
        return executingTime;
    }

    public void cleanChache() {

    }




}
