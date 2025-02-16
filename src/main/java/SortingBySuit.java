import java.util.Comparator;
import java.util.List;

public class SortingBySuit implements Comparator<Card> {
    private static final List<String> SUIT_ORDER = List.of("♥", "♣", "♦", "♠");

    @Override
    public int compare(Card card1, Card card2) {
        int suitIndex1 = SUIT_ORDER.indexOf(card1.getSuit());
        int suitIndex2 = SUIT_ORDER.indexOf(card2.getSuit());

        if (suitIndex1 == suitIndex2) {
            return Integer.compare(card1.getValue(), card2.getValue());
        } else {
            return Integer.compare(suitIndex1, suitIndex2);
        }
    }
}
