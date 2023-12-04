import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static HashMap<Integer, Integer> copiesOfCards = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day4\\day4\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day4\\day4\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        //System.out.println("total points: " + getTotalPoints(lines));
        System.out.println("total cards: " + getTotalCards(lines));
    }

    private static int getTotalCards(List<String> lines) {
        for (String card : lines) {
            String[] arr = card.split(":")[0].trim().split(" ");
            Integer cardNumber = Integer.valueOf(arr[arr.length - 1]);
            copiesOfCards.put(cardNumber, 1);
        }

        for (String card : lines) {
            String[] arr = card.split(":")[0].trim().split(" ");
            Integer cardNumber = Integer.valueOf(arr[arr.length - 1]);
            String cardWithoutName = card.split(":")[1].trim();

            List<Integer> winningNumbers = getNumbersFromString(cardWithoutName.split("\\|")[0]);
            List<Integer> yourNumbers = getNumbersFromString(cardWithoutName.split("\\|")[1]);
            List<Integer> matches = createMatchingList(winningNumbers, yourNumbers);

            getBonusCards(cardNumber, matches.size());
        }
        return countAllCopies();
    }

    private static int countAllCopies() {
        int sum = 0;
        for (Integer key : copiesOfCards.keySet()) {
            sum += copiesOfCards.get(key);
        }
        return sum;
    }

    private static void getBonusCards(Integer cardNumber, int matches) {
        int copiesOfWinningCard = copiesOfCards.get(cardNumber);
        for (int idOfCardWon = cardNumber + 1; idOfCardWon < cardNumber + matches + 1; idOfCardWon++) {
            //card to add new copies to
            for (int copiesToAdd = 0; copiesToAdd < copiesOfWinningCard; copiesToAdd++) {
                //number of copies to add
                int numberOfCopies = copiesOfCards.get(idOfCardWon);
                if (copiesOfCards.containsKey(idOfCardWon)) {
                    copiesOfCards.put(idOfCardWon, numberOfCopies + 1);
                }
            }
        }
    }

    private static int getTotalPoints(List<String> lines) {
        int sum = 0;

        for (String card : lines) {
            String cardWithoutName = card.split(":")[1].trim();
            String winningNumbersString = cardWithoutName.split("\\|")[0];
            String yourNumbersString = cardWithoutName.split("\\|")[1];

            List<Integer> winningNumbers = getNumbersFromString(winningNumbersString);
            List<Integer> yourNumbers = getNumbersFromString(yourNumbersString);
            List<Integer> matches = createMatchingList(winningNumbers, yourNumbers);

            sum += getPointsOfCard(matches);
        }
        return sum;
    }

    private static List<Integer> createMatchingList(List<Integer> winningNumbers, List<Integer> yourNumbers) {
        List<Integer> list = new ArrayList<>();

        for (Integer i : yourNumbers) {
            if(winningNumbers.contains(i)) list.add(i);
        }
        return list;
    }

    private static int getPointsOfCard(List<Integer> list) {
        int res = 0;

        if (!list.isEmpty()) {
            for (int num : list) {
                res = res == 0 ? (res += 1) : (res * 2);
            }
        }

        return res;
    }

    private static List<Integer> getNumbersFromString(String string) {
        Matcher matcher = Pattern.compile("\\d+").matcher(string);

        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Integer.valueOf(matcher.group()));
        }
        return numbers;
    }
}