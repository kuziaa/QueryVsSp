import dbConnection.SQLDatabaseConnection;
import executor.QueryExecutor;
import executor.QueryExecutorMachine;
import queryReader.QueryReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryVsSP {

    private final String queryFile = "";
    private final String spFile = "";
    private final String paramFile = "";

    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
        QueryExecutorMachine queryExecutorMachine = new QueryExecutorMachine("old_query.sql",
                "new_SP.sql", "params.csv");

        queryExecutorMachine.runCompare();
        queryExecutorMachine.extractReport();
    }
}
