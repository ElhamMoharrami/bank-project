import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvWriter<T> {
    private final String fileName;
    private final List<T> list;

    public CsvWriter(String fileName, List<T> list) {
        this.fileName = fileName;
        this.list = list;
    }

    public void writeToFile() {
        Path filePath = FileSystems.getDefault().getPath(fileName);
        try (BufferedWriter bufferedList = Files.newBufferedWriter(filePath)) {
            for (T element : list) {
                bufferedList.write(element.toString() + "\n");
            }
            System.out.println(fileName+" generated successfully");
        } catch (IOException e) {
            System.out.println("Something went wrong,could not create file.");
        }
    }
}

