import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Map<String, Integer> createWordToNumberMap(){
        Map<String, Integer> mp = new HashMap<>();
        mp.put("one", 1);
        mp.put("two", 2);
        mp.put("three", 3);
        mp.put("four", 4);
        mp.put("five", 5);
        mp.put("six", 6);
        mp.put("seven", 7);
        mp.put("eight", 8);
        mp.put("nine", 9);
        mp.put("oneight", 18);
        mp.put("twone", 21);
        mp.put("eightwo", 82);
        return mp;
    }

    public static int extractNumber(String input) {
        Map<String, Integer> wordToNumber = createWordToNumberMap();

        StringBuilder digits = new StringBuilder();

        Pattern pattern = Pattern.compile("(?:\\d+|oneight|twone|eightwo|one|two|three|four|five|six|seven|eight|nine)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group().toLowerCase();
            if (Character.isDigit(match.charAt(0))) {
                digits.append(match);
            } else {
                digits.append(wordToNumber.get(match));
            }
        }


        String number = "";
        number += digits.charAt(0);
        number += digits.charAt(digits.length() - 1);

        return Integer.parseInt(number);
    }

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day1\\day1task1\\src\\input.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        int ans = 0;
        for (String line : lines) {
            // System.out.println(line);
            // System.out.println(extractNumber(line));
            ans += extractNumber(line);
        }
        System.out.println(ans);
    }
}

