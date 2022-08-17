import executor.QueryExecutorMachine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

public class QueryVsSP {

    public static void main(String[] args) {
        QueryExecutorMachine queryExecutorMachine = new QueryExecutorMachine("old_query.sql",
                "new_SP.sql", "params (only one).csv");

        queryExecutorMachine.runCompare();
        queryExecutorMachine.extractReport();

    }
}
