package Services;

import Models.UserCard;

import java.sql.Connection;
import java.sql.SQLException;

public class TransferService implements IService, IWithdrawable {
    private final BankService bankService = new BankService();
    private final Connection connection;
    private double comission;

    public TransferService(Connection connection) {
        this.connection = connection;
    }

    public boolean transferToCard(double amount, UserCard cardFrom, UserCard cardTo, String bankOfATM) throws SQLException {
        if (!(cardFrom.bankOfCard.equals(bankOfATM) && cardTo.bankOfCard.equals(bankOfATM))) {
            calculateComission(amount);
        }
        if (canPay(amount, cardFrom)) {
            double amountToUpdate = bankService.getAmountById(cardFrom.getCardId(), connection) - amount - comission;
            bankService.updateAmountById(cardFrom.getCardId(), amountToUpdate, connection);
            amountToUpdate = bankService.getAmountById(cardTo.getCardId(), connection) + amount;
            bankService.updateAmountById(cardTo.getCardId(), amountToUpdate, connection);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void calculateComission(double amount) {
        comission = 0.05 * amount;
    }

    @Override
    public boolean canPay(double amount, UserCard card) throws SQLException {
        return bankService.getAmountById(card.getCardId(), connection) >= amount + comission;
    }
}
