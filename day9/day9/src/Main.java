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
        System.out.println(solvePart2(lines));
    }

    private static int solvePart1(List<String> lines) {
        List<Integer> listOfNextValues = getNextValuesList(lines, true);
        return listOfNextValues.stream().reduce(0, Integer::sum);
    }

    private static int solvePart2(List<String> lines) {
        List<Integer> listOfNextValues = getNextValuesList(lines, false);
        return listOfNextValues.stream().reduce(0, Integer::sum);
    }

    private static List<Integer> getNextValuesList(List<String> lines, boolean part1) {
        return lines.stream().map(line -> getNextValue(line, part1)).toList();
    }

    private static Integer getNextValue(String line, boolean part1) {
        List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::valueOf).toList();
        Map<Integer, List<Integer>> map = new HashMap<>();

        createMap(map, numbers);

        fillMap(map, part1);

        return part1 ? map.get(0).getLast() : map.get(0).getFirst();
    }

    private static void createMap(Map<Integer, List<Integer>> map, List<Integer> numbers) {
        int currentSequence = 0;
        List<Integer> currentList = numbers;
        map.put(currentSequence, currentList);

        while (!new HashSet<>(currentList).equals(Set.of(0))) {
            List<Integer> nextList = new ArrayList<>();

            for (int i = 0; i < currentList.size() - 1; i++) {
                nextList.add(currentList.get(i + 1) - currentList.get(i));
            }
            currentList = nextList;
            currentSequence++;
            map.put(currentSequence, currentList);
        }
    }

    private static void fillMap(Map<Integer, List<Integer>> map, boolean part1) {
        for (int i = map.keySet().size() - 1; i > 0; i--) {
            List<Integer> list = map.get(i);
            List<Integer> prevList = new ArrayList<>(map.get(i - 1));
            int val;
            int prevVal;

            if (part1) {
                val = list.getLast();
                prevVal = prevList.getLast();
                prevList.add(prevVal + val);
            } else {
                val = list.getFirst();
                prevVal = prevList.getFirst();
                prevList.add(0, prevVal - val);
            }

            map.put(i - 1, prevList);
        }
    }
}