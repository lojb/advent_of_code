import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day6\\day6\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day6\\day6\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        System.out.println(solvePart1(lines));
        System.out.println(solvePart2(lines));
    }

    private static int solvePart1(List<String> lines) {
        long[] timesOfRacesMs = Arrays.stream(lines.get(0).split(":")[1].trim().split(" +")).mapToLong(Long::parseLong).toArray();
        long[] recordDistanceMm = Arrays.stream(lines.get(1).split(":")[1].trim().split(" +")).mapToLong(Long::parseLong).toArray();

        return getWinningStrategies(timesOfRacesMs, recordDistanceMm);
    }

    private static int solvePart2(List<String> lines) {
        long timeOfRace = Long.parseLong(lines.get(0).split(":")[1].trim().replace(" ", ""));
        long recordDistance = Long.parseLong(lines.get(1).split(":")[1].trim().replace(" ", ""));
        return winningWaysOfRace(timeOfRace, recordDistance);
    }

    private static int getWinningStrategies(long[] timesOfRacesMs, long[] recordDistanceMm) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < timesOfRacesMs.length; i++) {
            list.add(winningWaysOfRace(timesOfRacesMs[i], recordDistanceMm[i]));
        }

        return list.stream().reduce(1, (a, b) -> a * b);
    }

    private static int winningWaysOfRace(long time, long record) {
        int winningWays = 0;
        for (int buttonPushTime = 0; buttonPushTime <= time; buttonPushTime++) {
            long travelTime = time - buttonPushTime;
            int speed = buttonPushTime;
            if (travelTime * speed > record) winningWays++;
        }
        return winningWays;
    }
}