package executor;

import dbConnection.SQLDatabaseConnection;
import utility.FilesWorker;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutorMachine {

    String queryFileName;
    String spFileName;
    String paramFileName;
    String query;
    String sp;
    List<List<String>> parameters;
    List<List<String>> resultAsList = new ArrayList<>();
    List<String> resultTitle = new ArrayList<>();

    String clearCacheQuery = "DBCC DROPCLEANBUFFERS;\n" +
            "DBCC FREEPROCCACHE;\n" +
            "select 0";

    public QueryExecutorMachine(String queryFileName, String spFileName, String paramFileName) {
        this.queryFileName = queryFileName;
        this.spFileName = spFileName;
        this.paramFileName = paramFileName;
        init();

    }

    private void init() {
        query = FilesWorker.fileToString(queryFileName);
        sp = FilesWorker.fileToString(spFileName);
        parameters = FilesWorker.csvToList(paramFileName);
        resultTitle.addAll(parameters.get(0));
        parameters.remove(0);
        resultTitle.add("Query Time");
        resultTitle.add("SP Time");
        resultTitle.add("Query Raws");
        resultTitle.add("SP Raws");
        resultTitle.add("Results Are The Same");
    }

    public void runCompare() {
        for (List<String> parametersLine : parameters) {
            Connection connection = SQLDatabaseConnection.getConnection(parametersLine.get(0));

            QueryExecutor queryExecutor = new QueryExecutor(query, connection);
            QueryExecutor spExecutor = new QueryExecutor(sp, connection);
            QueryExecutor clearCacheExecutor = new QueryExecutor(clearCacheQuery, connection);

            queryExecutor.setParam(1, parametersLine.get(1), "dateTime");
            queryExecutor.setParam(2, parametersLine.get(2), "dateTime");
            queryExecutor.setParam(3, parametersLine.get(3), "date");
            queryExecutor.setParam(4, parametersLine.get(1), "dateTime");
            queryExecutor.setParam(5, parametersLine.get(2), "dateTime");
//            queryExecutor.setParam(6, parametersLine.get(4), "int");
//            queryExecutor.setParam(7, parametersLine.get(5), "int");
            queryExecutor.executeQuery();
            clearCacheExecutor.executeQuery();

            spExecutor.setParam(1, parametersLine.get(1), "dateTime");
            spExecutor.setParam(2, parametersLine.get(2), "dateTime");
            spExecutor.setParam(3, parametersLine.get(3), "date");
            spExecutor.setParam(4, parametersLine.get(4), "int");
            spExecutor.setParam(5, parametersLine.get(5), "int");
            spExecutor.executeQuery();
            clearCacheExecutor.executeQuery();

            List<String> resultLine = new ArrayList<>();
            resultLine.addAll(parametersLine);
            resultLine.add("" + queryExecutor.getExecutingTime());
            resultLine.add("" + spExecutor.getExecutingTime());
            resultLine.add("" + queryExecutor.getResult().size());
            resultLine.add("" + spExecutor.getResult().size());

            resultLine.add("" + queryExecutor.compareResults(spExecutor.getResult()));
            resultAsList.add(resultLine);
        }
    }

    public void extractReport() {
        for (List<String> resultLine: resultAsList) {
            System.out.println(resultLine);
        }
    }
}
