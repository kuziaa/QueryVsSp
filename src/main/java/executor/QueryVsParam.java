package executor;

import queryReader.ParametersReader;
import queryReader.QueryReader;

import java.util.List;

public class QueryVsParam {

    String queryFile;
    String spFile;
    String paramFile;
    QueryReader queryReader;
    QueryReader spReader;
    ParametersReader parametersReader;
    List<List<String>> resultAsList;

    public QueryVsParam(String queryFile, String spFile, String paramFile) {
        this.queryFile = queryFile;
        this.spFile = spFile;
        this.paramFile = paramFile;
        init();

    }

    private void init() {
        queryReader = new QueryReader(queryFile);
        spReader = new QueryReader(spFile);
        parametersReader = new ParametersReader(paramFile);
        List<String> result;
    }

    public void runCompare() {
        for (List<String> parametersLine : parametersReader.getParameters()) {
            QueryExecutor queryExecutor = new QueryExecutor(queryReader.getQuery(), parametersLine);
            QueryExecutor spExecutor = new QueryExecutor(spReader.getQuery(), parametersLine);
            queryExecutor.executeQuery();
            queryExecutor.cleanChache();
            spExecutor.executeQuery();
            spExecutor.cleanChache();

        }

    }

    public void extractReport() {

    }
}
