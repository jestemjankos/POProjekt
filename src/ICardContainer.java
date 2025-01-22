import java.util.ArrayList;

public interface ICardContainer {
    void addCard(Card card);
    int getSum();
    void reset();
    ArrayList<Card> getCards();
    int reduceAceValue();
}