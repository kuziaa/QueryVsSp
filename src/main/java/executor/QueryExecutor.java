package executor;

import dbConnection.SQLDatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutor {

    private final String query;
    private final String db;
    private final List<String> parameters;
    private ResultSet rs;
    private List<List<String>> result = new ArrayList<>();
    private long executingTime;

    public QueryExecutor(String query, List<String> parameters) {
        this.query = query;
        this.db = parameters.get(0);
        this.parameters = parameters;
    }

    public void executeQuery() {
        executeQuery(this.query);
    }

    public void executeQuery(String query) {
        Connection con = SQLDatabaseConnection.getConnection(db);

        try (PreparedStatement pstm = con.prepareStatement(query)) {

            for (int i = 1; i < parameters.size(); i++) {
                pstm.setString(i, parameters.get(i));
            }

            long startTime = System.currentTimeMillis();
            rs =pstm.executeQuery();
            executingTime = System.currentTimeMillis() - startTime;

            resultSetToList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void resultSetToList() throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (rs.next()) {
            List<String> lineOfData = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                lineOfData.add(rs.getString(i));
            }
            result.add(lineOfData);
        }
    }

    public long getExecutingTime() {
        return executingTime;
    }

    public List<List<String>> getResult() {
        return result;
    }

    public boolean compareResults(List<List<String>> otherResult) {
        for (int i = 0; i < result.size(); i++) {
            List<String> lineOfResult1 = result.get(i);
            List<String> lineOfResult2 = otherResult.get(i);
            for (int j = 0; j < lineOfResult1.size(); j++) {
                if (!lineOfResult1.get(j).equals(lineOfResult2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
