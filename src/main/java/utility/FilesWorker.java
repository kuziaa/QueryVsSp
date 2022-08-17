package utility;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FilesWorker {

    public static final String COMMA_DELIMITER = ",";

    private FilesWorker() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static List<List<String>> csvToList(String fileName) {
        List<List<String>> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getFileFromResources(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineAsArray = line.split(COMMA_DELIMITER);
                fileContent.add(Arrays.asList(lineAsArray));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContent;
    }

    public static void listToCSV(String fileName, List<List<String>> fileContent) {
        String fileContentAsString = listToString(fileContent);
        fileName = "src/main/resources/" + fileName;
        try {
            File file = new File(fileName);
            file.createNewFile();
            Files.writeString(file.toPath(), fileContentAsString, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fileToString(String fileName) {
        File file = getFileFromResources(fileName);
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String listToString(List<List<String>> fileContent) {
        String fileContentAsString = "";
        for (List<String> line: fileContent) {
            fileContentAsString += line.stream()
                    .collect(Collectors.joining(COMMA_DELIMITER));
            fileContentAsString += "\n";
        }
        return fileContentAsString;
    }

    private static File getFileFromResources(String fileName) {

        URL resource = FilesWorker.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
