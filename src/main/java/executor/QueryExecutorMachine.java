package executor;

import queryReader.ParametersReader;
import queryReader.QueryReader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutorMachine {

    String queryFile;
    String spFile;
    String paramFile;
    QueryReader queryReader;
    QueryReader spReader;
    ParametersReader parametersReader;
    List<List<String>> resultAsList;
    List<String> resultTitle;

    String clearCache = "DBCC DROPCLEANBUFFERS;\n" +
            "DBCC FREEPROCCACHE;\n" +
            "select 0";

    public QueryExecutorMachine(String queryFile, String spFile, String paramFile) {
        this.queryFile = queryFile;
        this.spFile = spFile;
        this.paramFile = paramFile;
        resultTitle = new ArrayList<>();
        init();

    }

    private void init() {
        queryReader = new QueryReader(queryFile);
        spReader = new QueryReader(spFile);
        parametersReader = new ParametersReader(paramFile);
        resultAsList = new ArrayList<>();
        resultTitle = new ArrayList<>();
        resultTitle.addAll(parametersReader.getTitles());
        resultTitle.add("Query Time");
        resultTitle.add("SP Time");
        resultTitle.add("Query Raws");
        resultTitle.add("SP Raws");
        resultTitle.add("Results Are The Same");
    }

    public void runCompare() {
        for (List<String> parametersLine : parametersReader.getParameters()) {

            QueryExecutor queryExecutor = new QueryExecutor(queryReader.getQuery(), parametersLine);
            QueryExecutor spExecutor = new QueryExecutor(spReader.getQuery(), parametersLine);
            QueryExecutor clearCacheExecutor = new QueryExecutor(clearCache, List.of(parametersLine.get(0)));

            queryExecutor.executeQuery();
            clearCacheExecutor.executeQuery();
            spExecutor.executeQuery();
            clearCacheExecutor.executeQuery();

            List<String> resultLine = new ArrayList<>();
            resultLine.addAll(parametersLine);
            resultLine.add("" + queryExecutor.getExecutingTime());
            resultLine.add("" + spExecutor.getExecutingTime());
            resultLine.add("" + queryExecutor.getResult().size());
            resultLine.add("" + spExecutor.getResult().size());

//            resultAsList.add(parametersLine);
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
