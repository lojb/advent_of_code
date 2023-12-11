import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day7\\day7\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day7\\day7\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        System.out.println(solvePart1(lines));
        System.out.println(solvePart2(lines));
    }

    private static int solvePart1(List<String> lines) {
        List<Hand> allHands = createListOfHands(lines, true);
        allHands = orderByHandType(allHands);
        return getSumOfWins(allHands);
    }
    private static int solvePart2(List<String> lines) {
        List<Hand> allHands = createListOfHands(lines, false);
        allHands = orderByHandType(allHands);
        return getSumOfWins(allHands);
    }

    private static List<Hand> createListOfHands(List<String> lines, boolean part1) {
        List<Hand> handsList = new ArrayList<>();
        for (String line : lines) {
            handsList.add(new Hand(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]), part1));
        }
        return handsList;
    }

    private static List<Hand> orderByHandType(List<Hand> allHands) {
        Collections.sort(allHands);
        return allHands;
    }

    private static int getSumOfWins(List<Hand> allHands) {
        int sum = 0;
        for (int i = 0; i < allHands.size(); i++) {
            //System.out.println(allHands.get(i).getBid());
            //System.out.println(i + 1);
            sum += allHands.get(i).getBid() * (i + 1);
        }
        return sum;
    }
}