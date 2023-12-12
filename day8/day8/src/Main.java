import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static String instruction;

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day8\\day8\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day8\\day8\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        System.out.println("part 1: " + solvePart1(lines));
    }

    private static int solvePart1(List<String> lines) {
        Map<String, String[]> nodes = createNodes(lines);

        return getRequiredSteps(nodes);
    }

    private static int getRequiredSteps(Map<String, String[]> nodes) {
        int stepCount = 0;
        String currentElement = "AAA";

        while (!currentElement.equals("ZZZ")) {
            for (String str : instruction.split("")) {
                String[] currentNode = nodes.get(currentElement);
                currentElement = switch (str) {
                    case "L" -> currentNode[0];
                    case "R" -> currentNode[1];
                    default -> currentElement;
                };
                stepCount++;
                if (currentElement.equals("ZZZ")) break;
            }
        }

        return stepCount;
    }

    private static Map<String, String[]> createNodes(List<String> lines) {
        Map<String, String[]> nodes = new HashMap<>();
        instruction = lines.get(0);

        for (int i = 2; i < lines.size(); i++) {
            String node = lines.get(i).split(" = ")[0];
            String[] arr = lines.get(i).split(" = ")[1].replaceAll("\\p{P}", "").split(" ");
            nodes.put(node, arr);
        }

        return nodes;
    }
}