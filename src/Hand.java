import java.util.ArrayList;

public abstract class Hand implements ICardContainer {
    protected ArrayList<Card> cards;
    protected int sum;
    protected int aceCount;

    public Hand() {
        cards = new ArrayList<>();
        reset();
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
        sum += card.getValue();
        if (card.isAce()) {
            aceCount++;
        }
        reduceAceValue();
    }

    @Override
    public int getSum() {
        return sum;
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public void reset() {
        cards.clear();
        sum = 0;
        aceCount = 0;
    }

    @Override
    public int reduceAceValue() {
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }
}