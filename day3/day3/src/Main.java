import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    static Set<String> symbols = new HashSet<>();
    static char gearSymbol = '*';

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day3\\day3\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day3\\day3\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        System.out.println("sum of all lines: " + getSumOfAllLines(lines));
        System.out.println("sum of all gear ratios: " + getSumOfAllGearRatios(lines));
    }

    private static int getSumOfAllGearRatios(List<String> lines) {
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            String lineBefore = getLineBefore(lines, i);
            String lineAfter = getLineAfter(lines, i);

            sum += getGearsOfLine(currentLine, lineBefore, lineAfter);
        }
        return sum;
    }

    private static int getGearsOfLine(String currentLine, String lineBefore, String lineAfter) {
        StringBuilder numberString = new StringBuilder();

        int sum = 0;
        int indexOfGear = 0;

        for (int i = 0; i < currentLine.length(); i++) {
            if (currentLine.charAt(i) == '*') {
                indexOfGear = i;
                getSumOfGearRatio(indexOfGear, currentLine, lineBefore, lineAfter);
            }
        }

        return sum;
    }

    private static int getSumOfGearRatio(int indexOfGear, String currentLine, String lineBefore, String lineAfter) {
        int res = 0;
        int gearRatio = 1;
        for (int i = indexOfGear - 1; i <= indexOfGear + 1; i++) {
            if (i >= 0 && i <= currentLine.length()) {
                if (checkLineForGear(lineBefore, i)) {
                    gearRatio += 1;
                }
                if (checkLineForGear(currentLine, i)) {
                    gearRatio += 1;
                }
                if (checkLineForGear(lineAfter, i)) {
                    gearRatio += 1;
                }
            }
        }
        return gearRatio >= 2 ? res : 0;
    }

    private static boolean checkLineForGear(String line, int i) {
        StringBuilder numberString = new StringBuilder();
        for (int j = i - 1; j < i + 1; j++) {

        }
        return Character.isDigit(line.charAt(i));
    }

    public static int getSumOfAllLines(List<String> lines) {
        setSymbols(lines);
        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            String lineBefore = getLineBefore(lines, i);
            String lineAfter = getLineAfter(lines, i);

            sum += getSumOfLine(currentLine, lineBefore, lineAfter);
        }

        return sum;
    }

    public static String getLineBefore(List<String> allLines, int i) {
        String lineBefore = null;
        if (i - 1 >= 0) lineBefore = allLines.get(i - 1);
        return lineBefore;
    }

    public static String getLineAfter(List<String> allLines, int i) {
        String lineAfter = null;
        if (i + 1 < allLines.size()) lineAfter = allLines.get(i + 1);
        return lineAfter;
    }


    public static void setSymbols(List<String> lines) {
        lines = lines.stream().map(line -> line.replaceAll("\\d", "").replaceAll("\\.", "")).toList();

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                symbols.add(String.valueOf(line.charAt(i)));
            }
        }
    }

    public static int getSumOfLine(String line, String lineBefore, String lineAfter) {
        StringBuilder numberString = new StringBuilder();

        int sum = 0;
        int startPosition = 0;
        int endPosition = 0;

        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                if (numberString.isEmpty()) startPosition = i;
                numberString.append(line.charAt(i));
            }

            if (i + 1 == line.length()) {
                endPosition = i;
            }

            if (!numberString.isEmpty() && !Character.isDigit(line.charAt(i))) {
                endPosition = i - 1;
            }

            if (!numberString.isEmpty() && (i + 1 == line.length() || !Character.isDigit(line.charAt(i)))) {

                endPosition = i - 1;
                int currentNumber = Integer.parseInt(numberString.toString());

                if (validateNumber(line, lineBefore, lineAfter, startPosition, endPosition)) {
                    sum += currentNumber;
                }

                numberString = new StringBuilder();
            }
        }

        return sum;
    }

    private static boolean validateNumber(String currentLine, String lineBefore, String lineAfter, int startPosition, int endPosition) {
        boolean res = false;

        for (int i = startPosition - 1; i <= endPosition + 1; i++) {
            if (i >= 0 && i <= currentLine.length()) {
                if (checkLine(lineBefore, i)) res = true;
                if (checkLine(currentLine, i)) res = true;
                if (checkLine(lineAfter, i)) res = true;
            }
        }
        return res;
    }

    private static boolean checkLine(String line, int i) {
        boolean res = false;

        if (line != null && symbols.contains(String.valueOf(line.charAt(i)))) {
            res = true;
        }
        return res;
    }
}