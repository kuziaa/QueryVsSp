package queryReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class QueryReader {

    String fileName;
    String query;

    public QueryReader(String fileName) {

        this.fileName = fileName;
    }

    public String getQuery() {

        URL resource = getResource();

        try {
            query = Files.readString(Path.of(resource.toURI()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    private URL getResource() {
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found");
        }
        return resource;
    }
}
