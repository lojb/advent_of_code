import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day8\\day8\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day8\\day8\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
    }
}