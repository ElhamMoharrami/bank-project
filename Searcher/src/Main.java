import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String files = args[0];
            Search.search(files);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("please enter a valid path to files directory");
        }
    }
}
