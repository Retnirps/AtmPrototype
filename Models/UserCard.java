package Models;

public class UserCard {
    private final int cardId;
    public final String bankOfCard;

    public int getCardId() {
        return cardId;
    }

    public UserCard(int cardId, String bankOfCard) {
        this.cardId = cardId;
        this.bankOfCard = bankOfCard;
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "bankOfCard='" + bankOfCard + '\'' +
                '}';
    }
}
