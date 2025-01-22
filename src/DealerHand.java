public class DealerHand extends Hand {
    private Card hiddenCard;

    public void setHiddenCard(Card card) {
        this.hiddenCard = card;
        sum += card.getValue();
        if (card.isAce()) {
            aceCount++;
        }
        reduceAceValue();
    }

    public Card getHiddenCard() {
        return hiddenCard;
    }

    @Override
    public void reset() {
        super.reset();
        hiddenCard = null;
    }

    public boolean shouldDraw(int playerSum) {
        return sum < 17 && sum <= playerSum;
    }
}