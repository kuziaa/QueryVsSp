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
//        QueryExecutorMachine queryExecutorMachine = new QueryExecutorMachine("old_query_declare.sql",
//                "new_SP_declare.sql", "params.csv");
//
//        queryExecutorMachine.runCompare();
//        queryExecutorMachine.extractReport();

        QueryReader queryReader = new QueryReader("old_query_2.sql");
        List<String> params = new ArrayList<>();
        params.add("992150");
        params.add("2022-07-11");
        params.add("1");
        params.add("2022-06-12 18:00:00");
        params.add("2022-07-11 18:00:00");
        params.add("2");
        params.add("2");
        params.add("2022-06-12 18:00:00");
        params.add("2022-07-11 18:00:00");
        QueryExecutor queryExecutor = new QueryExecutor(queryReader.getQuery(), params);
        queryExecutor.executeQuery();
        System.out.println(queryExecutor.getExecutingTime());
//        658



    }
}
