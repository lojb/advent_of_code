import java.util.*;

public class Hand implements Comparable{
    int bid;
    int handStrength;
    int[] freqs = new int[13];
    int[] cards = new int[5];
    String[] ranks= new String[]{"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};

    public Hand(String line, int bid, boolean part1) {
        if (!part1) {
            ranks = new String[]{"A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J"};
        }
        this.bid = bid;
        int numJokers = 0;
        String[] s = line.split("");
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                if (s[i].equals(ranks[j])) {
                    if (!s[i].equals("J") || part1) {
                        freqs[j]++;
                    }
                    if (s[i].equals("J") && !part1) {
                        numJokers++;
                    }
                    cards[i] = j;
                }
            }
        }
        Arrays.sort(freqs);
        freqs[freqs.length - 1] += numJokers;
        handStrength = 2 * freqs[freqs.length - 1];
        if (freqs[freqs.length - 2] == 2) {
            handStrength += 1;
        }
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(Object o) {
        Hand other = (Hand) o;
        if (handStrength != other.handStrength) {
            return handStrength - other.handStrength;
        } else {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] != other.cards[i]) {
                    return other.cards[i] - cards[i];
                }
            }
            return 0;
        }
    }
}
