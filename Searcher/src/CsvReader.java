import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<String> readFile(String fileLoc) {
        List<String> list = new ArrayList<>();
        Path path = Paths.get(fileLoc);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                list.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println("something went wrong,could not read the file");
        }
        return list;
    }
}
