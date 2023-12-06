import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\advent_of_code\\day5\\day5\\src\\input.txt";
        //String filePath = "D:\\advent_of_code\\day5\\day5\\src\\example.txt";
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }
    private static long part1(List<String> lines) {
        long[] seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" +")).mapToLong(Long::parseLong).toArray();
        List<Mapping> maps = generateMaps(lines);

        return getLowestLocation(seeds, maps);
    }

    private static long part2(List<String> lines) {
        long[] seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" +")).mapToLong(Long::parseLong).toArray();
        List<SeedEntry> seedEntries = SeedEntry.fill(seeds);
        List<Mapping> maps = generateMaps(lines);

        return revFind(seedEntries, maps);
    }
    private static List<Mapping> generateMaps(List<String> inputList) {
        List<Mapping> list = new ArrayList<>();
        list.add(new Mapping());
        int mapIndex = 0;
        for (int i = 3; i < inputList.size(); i++) {
            String current = inputList.get(i);
            if (current.isEmpty()) {
                i++;
                mapIndex++;
                list.add(new Mapping());
            } else {
                long dest = Long.parseLong(current.split(" ")[0]);
                long source = Long.parseLong(current.split(" ")[1]);
                long range = Long.parseLong(current.split(" ")[2]);
                Mapping currentMap = list.get(mapIndex);
                currentMap.addLine(new Line(source, dest, range));
            }
        }
        return list;
    }

    private static long getLowestLocation(long[] seeds, List<Mapping> maps) {
        long lowestLoc = Long.MAX_VALUE;
        for (long seed : seeds) {
            long next = seed;
            for (Mapping map : maps) {
                next = map.findMapping(next);
            }
            if (next < lowestLoc) lowestLoc = next;
        }
        return lowestLoc;
    }

    private static long revFind(List<SeedEntry> seedEntries, List<Mapping> maps) {
        long max = maps.get(maps.size() - 1).getMax();
        for (int i = 0; i < max; i++) {
            long next = i;
            for (int j = maps.size() - 1; j >= 0; j--) {
                next = maps.get(j).findRevMapping(next);
            }
            for (SeedEntry seedEntry : seedEntries) {
                if (seedEntry.partOfLine(next)) return i;
            }
        }
        return -1;
    }

    static class SeedEntry {
        long source;
        long range;

        public SeedEntry(long source, long range) {
            this.source = source;
            this.range = range;
        }

        boolean partOfLine(long a) {
            return a >= source && a < source + range;
        }

        static List<SeedEntry> fill(long[] seeds) {
            List<SeedEntry> list = new ArrayList<>();
            for (int i = 0; i + 1 < seeds.length; i+=2) {
                list.add(new SeedEntry(seeds[i], seeds[i + 1]));
            }
            return list;
        }

        @Override
        public String toString() {
            return String.format("[%-%d]", source, range);
        }
    }
    static class Line {
        long source;
        long dest;
        long range;

        public Line(long source, long dest, long range) {
            this.source = source;
            this.dest = dest;
            this.range = range;
        }

        boolean partOfLine(long a) {
            return a >= source && a < source + range;
        }

        boolean revPartOfLine(long a) {
            return a >= dest && a < dest + range;
        }

        long mapsTo(long a) {
            long diff = a - source;
            return dest + diff;
        }

        long mapsFrom(long a) {
            long diff = a - dest;
            return source + diff;
        }

        @Override
        public String toString() {
            return String.format("[%d-%d-%d]", source, dest, range);
        }
    }

    static class Mapping {
        List<Line> lines = new ArrayList<>();

        void addLine(Line a) {
            lines.add(a);
        }

        long findMapping(long a) {
            for (Line line : lines) {
                if (!line.partOfLine(a)) continue;
                return line.mapsTo(a);
            }
            return a;
        }

        long findRevMapping(long a) {
            for (Line line : lines) {
                if (!line.revPartOfLine(a)) continue;
                return line.mapsFrom(a);
            }
            return a;
        }

        long getMax() {
            Line temp = lines.stream().max(Comparator.comparing(x -> x.dest + x.range)).get();
            return temp.dest + temp.range;
        }

        @Override
        public String toString() {
            return lines.toString();
        }
    }
}

