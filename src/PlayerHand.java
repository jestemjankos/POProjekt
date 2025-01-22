public class PlayerHand extends Hand {
    public boolean isBust() {
        return getSum() > 21;
    }

    public boolean hasBlackjack() {
        return getSum() == 21;
    }
}