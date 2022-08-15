package queryReader;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParametersReader {

    public final String COMMA_DELIMITER = ",";

    String fileName;
    List<String> titles = new ArrayList<>();
    List<List<String>> parameters = new ArrayList<>();

    public ParametersReader(String fileName) {
        this.fileName = fileName;
        parseCSVtoList();
    }

    private void parseCSVtoList() {

        try (BufferedReader br = new BufferedReader(new FileReader(getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                if (titles.isEmpty()) {
                    titles.addAll(Arrays.asList(values));
                    continue;
                }
                parameters.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFile() {

        URL resource = getClass().getClassLoader().getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }

        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<List<String>> getParameters() {
        return parameters;
    }

    
}
