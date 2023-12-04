import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int RED_LIMIT = 12;
    private static final int GREEN_LIMIT = 13;
    private static final int BLUE_LIMIT = 14;

    public static int getId(String line) {
        return Integer.parseInt(line.split(":")[0].split(" ")[1]);
    }

    public static String[] getSets(String line) {
        String[] allSets = line.split(":")[1].split(";");
        for (String set : allSets) {
            set = set.trim();
        }
        return line.split(":")[1].split(";");
    }

    public static boolean validateSet(String set) {
        set = set.trim();
        String[] arr = set.split(",");

        for (String handful : arr) {
            handful = handful.trim();
            String[] colorArr = handful.split(" ");

            int numberOfColor = Integer.parseInt(colorArr[0]);
            int limitOfColor = switch (colorArr[1]) {
                case "red" -> RED_LIMIT;
                case "green" -> GREEN_LIMIT;
                case "blue" -> BLUE_LIMIT;
                default -> 0;
            };

            if (numberOfColor > limitOfColor) {
                return false;
            }
        }

        return true;
    }

    public static int getPowerOfGame(String[] sets) {
        int minimumRed = 0;
        int minimumGreen = 0;
        int minimumBlue = 0;

        for (String set : sets) {
            set = set.trim();
            String[] arr = set.split(",");

            for (String handful : arr) {
                handful = handful.trim();
                String[] colorArr = handful.split(" ");

                int cubeNumberOfColor = Integer.parseInt(colorArr[0]);

                switch (colorArr[1]) {
                    case "red":
                        if (cubeNumberOfColor > minimumRed) minimumRed = cubeNumberOfColor;
                        break;
                    case "green":
                        if (cubeNumberOfColor > minimumGreen) minimumGreen = cubeNumberOfColor;
                        break;
                    case "blue":
                        if (cubeNumberOfColor > minimumBlue) minimumBlue = cubeNumberOfColor;
                        break;
                }
            }
        }

        //System.out.println(Arrays.toString(sets));
        //System.out.println("red: " + minimumRed + ", green: " + minimumGreen + ", blue: " + minimumBlue);
        return minimumRed * minimumGreen * minimumBlue;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day2\\day2\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day2\\day2\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        int sumOfValidIds = 0;
        int sumOfPowers = 0;

        for (String line : lines) {
            String[] sets = getSets(line);
            int power = getPowerOfGame(sets);
            sumOfPowers += power;

            boolean allSetValid = true;
            int id = getId(line);
            for (String set : sets) {
                if (!validateSet(set)) {
                    allSetValid = false;
                }
            }

            if (allSetValid) sumOfValidIds += id;
        }

        //System.out.println(sumOfValidIds);
        System.out.println(sumOfPowers);
    }
}