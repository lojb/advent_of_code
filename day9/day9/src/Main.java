import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day9\\day9\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day9\\day9\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        System.out.println(solvePart1(lines));
    }

    private static int solvePart1(List<String> lines) {
        List<Integer> listOfNextValues = getNextValuesList(lines);
        return listOfNextValues.stream().reduce(0, Integer::sum);
    }

    private static List<Integer> getNextValuesList(List<String> lines) {
        return lines.stream().map(Main::getNextValue).toList();
    }

    private static Integer getNextValue(String line) {
        List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::valueOf).toList();
        Map<Integer, List<Integer>> map = new HashMap<>();

        int currentSequence = 0;
        List<Integer> currentList = numbers;
        map.put(currentSequence, currentList);

        while (!new HashSet<>(currentList).equals(Set.of(0))) {
            List<Integer> nextList = new ArrayList<>();

            for (int i = 0; i < currentList.size() - 1;i++) {
                nextList.add(currentList.get(i + 1) - currentList.get(i));
            }
            currentList = nextList;
            currentSequence++;
            map.put(currentSequence, currentList);
        }

        for (int i = map.keySet().size() - 1; i > 0; i--) {
            List<Integer> list = map.get(i);
            List<Integer> prevList = new ArrayList<>(map.get(i - 1));
            int val = list.getLast();
            int prevVal = prevList.getLast();

            prevList.add(prevVal + val);
            map.put(i - 1, prevList);
        }

        return map.get(0).getLast();
    }
}