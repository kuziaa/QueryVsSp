package executor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutor {

    private final String query;
    private ResultSet rs;
    private List<List<String>> result = new ArrayList<>();
    private long executingTime;
    private Connection connection;
    private PreparedStatement preparedStatement;


    public QueryExecutor(String query, Connection connection) {
        this.query = query;
        this.connection = connection;
        setupPreparedStatement();
    }

    private void setupPreparedStatement() {
        try {
            preparedStatement = connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setParam(int paramNumber, String param, String type) {
        try {
            switch (type) {
                case "int":
                    preparedStatement.setInt(paramNumber, Integer.parseInt(param));
                    break;
                case "double":
                    preparedStatement.setDouble(paramNumber, Double.parseDouble(param));
                case "str":
                    preparedStatement.setString(paramNumber, param);
                    break;
                case "date":
                    preparedStatement.setDate(paramNumber, java.sql.Date.valueOf(param));
                    break;
                case "dateTime":
                    preparedStatement.setTimestamp(paramNumber, java.sql.Timestamp.valueOf(param));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeQuery() {
        executeQuery(this.query);
    }

    public void executeQuery(String query) {

        try {
            long startTime = System.currentTimeMillis();
            rs = preparedStatement.executeQuery();
            executingTime = System.currentTimeMillis() - startTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
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
        System.out.println("num of col current: " + result.size());
        System.out.println("num of col other: " + otherResult.size());

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
